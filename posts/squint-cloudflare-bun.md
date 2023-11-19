Title: Writing a Cloudflare worker with squint and bun
Date: 2023-11-19
Tags: clojure

In this post I'll describe how to write a Cloudflare worker with squint and bun.

## Introduction

As I tried to build an [Advent of Code
playground](https://squint-cljs.github.io/squint/?boilerplate=https%3A%2F%2Fgist.githubusercontent.com%2Fborkdude%2Fcf94b492d948f7f418aa81ba54f428ff%2Fraw%2Fe613dbceac5b04c2b71b032a75f13881bccd72c5%2Faoc_ui.cljs&src=OzsgSGVscGVyIGZ1bmN0aW9uczoKOzsgKGZldGNoLWlucHV0IHllYXIgZGF5KSAtIGdldCBBT0MgaW5wdXQKOzsgKGFwcGVuZCBzdHIpIC0gYXBwZW5kIHN0ciB0byBET00KOzsgKHNweSB4KSAtIGxvZyB4IHRvIGNvbnNvbGUgYW5kIHJldHVybiB4CgooZGVmIGlucHV0ICgtPj4gKGpzLWF3YWl0IChmZXRjaC1pbnB1dCAyMDIyIDEpKQogICAgICAgICAgICAgI19zcHkKICAgICAgICAgICAgIHN0ci9zcGxpdC1saW5lcwogICAgICAgICAgICAgKG1hcHYgcGFyc2UtbG9uZykpKQoKKGRlZm4gcGFydC0xCiAgW10KICAoLT4%2BIGlucHV0CiAgICAocGFydGl0aW9uLWJ5IG5pbD8pCiAgICAodGFrZS1udGggMikKICAgIChtYXAgIyhhcHBseSArICUpKQogICAgKGFwcGx5IG1heCkKICAgIGFwcGVuZCkpCgooZGVmbiBwYXJ0LTIKICBbXQogICgtPj4gaW5wdXQKICAgIChwYXJ0aXRpb24tYnkgbmlsPykKICAgICh0YWtlLW50aCAyKQogICAgKG1hcCAjKGFwcGx5ICsgJSkpCiAgICAoc29ydC1ieSAtKQogICAgKHRha2UgMykKICAgIChhcHBseSArKQogICAgYXBwZW5kKSkKCih0aW1lIChwYXJ0LTEpKQoodGltZSAocGFydC0yKSk%3D)
for squint, I wanted to support downloading puzzle input from
[adventofcode.com](https://adventofcode.com). Doing this directly from the
playground resulted in CORS issues. Mario Trost in the #adventofcode channel on
Clojurians Slack suggested that this could be solved using a Cloudflare worker
and indeed it could. Thanks Mario!

A [Cloudflare worker](https://workers.cloudflare.com/) is tiny "serverless"
  application that scales automatically. The first 100k requests a day are
  free. A worker can be written in JavaScript, among other languages. Workers
  written in JavaScript can use the [Fetch
  API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API) which
  comprises of `fetch`, `Request`, `Response` and more standardized stuff,
  available as global objects in browsers, Node.js, deno, bun and other
  browser-inspired JavaScript runtimes. These APIs are basically all you need to
  build a worker.

[Squint](https://github.com/squint-cljs/squint) is a ClojureScript dialect
designed for easier JS interop and smaller bundle output. As such, it seems like
a good fit got Cloudflare workers. The smaller the code, the better the (cold)
startup time.

[Bun](https://bun.sh/) is a fast all-in-one JavaScript runtime / bundler /
  toolkit. Bun seems a good tool to use for developing Cloudflare workers since
  with little boilerplate you can get something running quickly. Bun supports
  hot reloading of worker code too.

## Hello world

To produce a "hello world" worker only the following is necessary:

`squint.edn`:
``` clojure
{:paths ["src"]
 :extension "js"
 :output-dir "out"}
```

The Squint CLJS files are going into `src` and compiled `.js` files will be written to `out`.

The only source file:

`src/index.cljs`:
``` clojure
(ns index)

(defn ^:async handler [{:keys [method] :as req} _env _ctx]
  (js/Response. "hello world"))

(def default
  {:fetch handler})
```

Beware that your handler isn't going to be called `fetch` since this will
conflict with `js/fetch`, something that tripped me up. So I just called it
`handler`.  Calling a var `default` in squint will make it the default export,
which is something Cloudflare workers use.

Now run `bun install squint-cljs` and then `bun squint watch`.  In parallel, run
`bun --hot out/index.js`. This will spin up a server. You can make change to the
code and new requests will see the changes. This is very convenient for local
development and testing.

To bundle everything to a single file, run `bun build --minify --outdir=dist
out/index.js`. Then you can run `bun dist/index.js` to prove to yourself that
the standalone JS works.

The standalone JavaScript file is somewhere around 2 kilobytes. Seriously: 2kb,
that's it :)!

To deploy to Cloudflare, I'm using
[wrangler](https://developers.cloudflare.com/workers/wrangler/).  It needs a
small config file, called `wrangler.toml` which describes the name of the
application and the location of the main JS file:

``` clojure
name = "hello-world"
main = "dist/index.js"
compatibility_date = "2023-09-04"
```

After writing the config file, you can run `bun wrangler deploy`. You will be
asked to log in to the Cloudflare dashboard, etc. Eventually your application
will be running at `https://hellow-world.<your-user>.workers.dev`.

## Proxy

The final worker code can be seen below. The handler looks at an incoming
request, and decides whether it's a `GET` or `OPTION` request. In the handling
of the `GET` request, the URL params `day`, `year` and `aoc-token` are pulled
out. While developing I noticed that the `URLSearchParams` object implements a
`Map`-like ad-hoc interface, but squint's `get` function wasn't aware of this,
so initially I couldn't use destructuring like this:

``` clojure
(let [{:keys [foo]} (-> (js/URL. "https://foo.com?foo=1") :searchParams)] foo)
```

to get `foo` out of this object. Similarly for the `Headers` object from
`Response`. Both have a `get` method. I decided to make the squint `get`
function work with any JS datastructure that has a `get` method. I'm not
entirely sure if that is a good idea though since arbitrary methods called `get`
may perform side effects... Let me know in the comments!  But after doing so,
destructuring worked on the search params.

Then a request is made to `https://adventofcode.com` to retrieve the input. When
doing this from a non-browser, you don't get into any CORS issues. Note that we
can nicely use `js-await` for waiting for promise results. A copy of the
`Response` object was made and `"Access-Control-Allow-Origin" "*` was added to
the headers to satisfy all the CORS ... stuff.

I also noticed the browser playground was doing an `OPTION` request so I also
need to handle those, returning the most permissive headers to satisfy the CORS
gods.

`src/index.cljs`
``` clojure
(ns index)

(defn ^:async handler [{:keys [method] :as req} _env _ctx]
  (if (= :GET method)
    (let [params (-> req :url (js/URL.) :searchParams)
          {:keys [aoc-token day year]} params]
      (if (and aoc-token day year)
        (let [resp (js-await (js/fetch (str "https://adventofcode.com/" year "/day/" day "/input")
                                       {:headers {:cookie (str "session=" aoc-token)}}))
              body (js-await (.text resp))
              resp (js/Response. body {:headers {"Access-Control-Allow-Origin" "*"}})]
          resp)
        (js/Response. "Set 'aoc-token, 'day' and 'year' as a URL query parameter" {:status 400
                                                                                   :headers {"Access-Control-Allow-Origin" "*"}})))
    ;; response for :OPTION
    (js/Response. nil {:status 200
                       :headers {"Access-Control-Allow-Origin" "*"
                                 "Access-Control-Allow-Methods" "GET,HEAD,POST,OPTIONS"
                                 "Access-Control-Allow-Headers" "*"}})))

(def default
  {:fetch handler})
```

The final production JS is still around 2-3kb.  After another `bun wrangler
deploy` this browser playground for Advent of Code started working:

[Advent of Code
playground](https://squint-cljs.github.io/squint/?boilerplate=https%3A%2F%2Fgist.githubusercontent.com%2Fborkdude%2Fcf94b492d948f7f418aa81ba54f428ff%2Fraw%2Fe613dbceac5b04c2b71b032a75f13881bccd72c5%2Faoc_ui.cljs&src=OzsgSGVscGVyIGZ1bmN0aW9uczoKOzsgKGZldGNoLWlucHV0IHllYXIgZGF5KSAtIGdldCBBT0MgaW5wdXQKOzsgKGFwcGVuZCBzdHIpIC0gYXBwZW5kIHN0ciB0byBET00KOzsgKHNweSB4KSAtIGxvZyB4IHRvIGNvbnNvbGUgYW5kIHJldHVybiB4CgooZGVmIGlucHV0ICgtPj4gKGpzLWF3YWl0IChmZXRjaC1pbnB1dCAyMDIyIDEpKQogICAgICAgICAgICAgI19zcHkKICAgICAgICAgICAgIHN0ci9zcGxpdC1saW5lcwogICAgICAgICAgICAgKG1hcHYgcGFyc2UtbG9uZykpKQoKKGRlZm4gcGFydC0xCiAgW10KICAoLT4%2BIGlucHV0CiAgICAocGFydGl0aW9uLWJ5IG5pbD8pCiAgICAodGFrZS1udGggMikKICAgIChtYXAgIyhhcHBseSArICUpKQogICAgKGFwcGx5IG1heCkKICAgIGFwcGVuZCkpCgooZGVmbiBwYXJ0LTIKICBbXQogICgtPj4gaW5wdXQKICAgIChwYXJ0aXRpb24tYnkgbmlsPykKICAgICh0YWtlLW50aCAyKQogICAgKG1hcCAjKGFwcGx5ICsgJSkpCiAgICAoc29ydC1ieSAtKQogICAgKHRha2UgMykKICAgIChhcHBseSArKQogICAgYXBwZW5kKSkKCih0aW1lIChwYXJ0LTEpKQoodGltZSAocGFydC0yKSk%3D)

You need to insert your Advent of Code session token in the input box, run
`Compile` and after that it's stored in local storage, for other puzzles you
might want to solve this year!  Give it a spin and let me know what you think.

The source code for the worker is available [here](https://github.com/borkdude/aoc-proxy).
