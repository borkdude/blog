## Nbb

[Nbb](https://github.com/babashka/nbb) is a scripting environment, in the
tradition of babashka, but for Node.js. Like babashka, nbb doesn't have a
compile cycle, but it just interprets Clojure code using
 [SCI](https://github.com/babashka/sci). It has a few useful CLJS libraries
included, e.g. for JS interop. It can load _any_ JS library from NPM. Check out
the repo [here](https://github.com/babashka/nbb). There are several
[examples](https://github.com/babashka/nbb/tree/main/examples) available as
well.

## Nbb on AWS Lambda

While it was already possible to use nbb on
AWS Lambda, it needed some boilerplate code. Valtterri Harmainen captured this
boilerplate in his
[nbb-lambda-adapter](https://github.com/vharmain/nbb-lambda-adapter).

This week AWS announced that [AWS Lambda now supports ES Modules and Top-Level
Await for Node.js
14](https://aws.amazon.com/about-aws/whats-new/2022/01/aws-lambda-es-modules-top-level-await-node-js-14/).
With that in place, the boilerplate code isn't necessary anymore.

## Creating an nbb lambda

All you need to do to get nbb running on AWS Lambda is the following:

package.json:
```
{"dependencies": {"nbb": "0.1.0"}}
```

index.mjs:
``` javascript
import { loadFile } from 'nbb';

const { handler } = await loadFile('./example.cljs');

export { handler }
```

example.cljs:
``` clojure
(ns example)

(defn handler [event _ctx]
  (js/console.log event)
  (js/Promise.resolve #js{:hello "world"}))

#js {:handler handler}
```

Make sure to run `npm install`.

Zip the directory: `zip -r app.zip .`

Go to the AWS Console. `Choose Lambda` -> `Author from Scratch` -> `Runtime Node.s 14.x + arm64`.
The default 128 MB should be sufficient for fast response
times after cold start, but for fast cold starts, higher memory (which comes
with higher CPU) is better.

Then choose `Upload from` and choose the zip file.

You can test the lambda function by creating a test event and invoking it.

To be able to invoke the function via HTTP, you'll first have to `Publish` it.

The API Gateway response has to be a little different so adjust your handler code like this...

example.cljs:
```clojure
(ns example)

(defn handler [event _ctx]
      (js/console.log event)
      (js/Promise.resolve
        (clj->js {:statusCode 200
                  :body       (js/JSON.stringify #js{:hello "world"})})))

#js {:handler handler}
```

After uploading the updated code, under `Configuration > Trigger` you can add an API Gateway trigger. Create
one and choose `HTTP API` and `Security Open` (make sure you change this when it
becomes a private production lambda rather than just for the sake of trying nbb
on lambda!).

After that you should end up with a public URL like
`https://9fov8nrv4f.execute-api.eu-central-1.amazonaws.com/default/...` which
you can then call from `curl` or via a browser. The response times I got after
the cold start were around 100ms.

As a nice bonus, you can edit the CLJS code directly in the console:

<blockquote class="twitter-tweet"><p lang="en" dir="ltr">After cold start on a 128MB ARM lambda:<br><br>$ time curl <a href="https://t.co/NGsyrtbKZp">https://t.co/NGsyrtbKZp</a><br>{&quot;hello&quot;:&quot;world&quot;}curl 0.02s user 0.01s system 23% cpu 0.105 total<br><br>And you get to edit the CLJS code in the console :) <a href="https://t.co/4ql2R04R0N">pic.twitter.com/4ql2R04R0N</a></p>&mdash; (λ. borkdude) (@borkdude) <a href="https://twitter.com/borkdude/status/1479786184557617160?ref_src=twsrc%5Etfw">January 8, 2022</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>

Also check out this [nbb serverless
example](https://github.com/vharmain/nbb-serverless-example) that Valtteri
Harmainen made.
