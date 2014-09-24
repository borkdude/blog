---
layout: post
title: "Figwheel, Om and Ring: how to combine them?"
date: 2014-09-24 20:14:50 +0200
comments: true
published: true
categories: [clojurescript, figwheel, om]
---

tl;dr: In this article you will find a configuration of figwheel with
Om and Ring/Jetty as an external server.

For a few weeks I have been using Clojurescript and
[Om](https://github.com/swannodette/om) for front end development. Om
is a Clojurescript library based on the famous
[React](http://facebook.github.io/react/).

Today I have been playing around with
[Figwheel](https://github.com/bhauman/lein-figwheel), a
[leiningen](http://leiningen.org/) plugin that uses
[lein-cljsbuild](https://github.com/emezeske/lein-cljsbuild) to
compile Clojurescript and pushes the resulting Javascript to the
browser, which is then reloaded. Together with React this is a
powerful combination. As you change code in your editor and press
save, the changes can be instantly reflected in the page you were
viewing... if you configure things properly.

The project I'm currently working on consists of several Clojurescript
files. Often an Om component gets its own file. For my workflow it
would make sense to be able to edit a component, for example a click
handler on a button, then save the code and then being able to try the
new click handler immediately, without replaying actions to reach the
state in which it makes sense to press the button. With Figwheel this
workflow is possible and I wish I had found this out earlier.

Let's make a project to illustrate this workflow. I made a leiningen
template called [wrom](https://github.com/borkdude/wrom) that I will
use to create a new project.

```bash
$ lein new wrom example
$ find .
.
./.gitignore
./project.clj
./resources
./resources/public
./resources/public/index.html
./resources/public/style.css
./src
./src/example
./src/example/client.cljs
./src/example/server.clj
```

As you can see an example project has been made. It includes a server
and client part. All sources files reside in one directory
`src`. Let's add figwheel to our project. To do this add
`[figwheel "0.1.4-SNAPSHOT"]` to `:dependencies` and
`[lein-figwheel "0.1.4-SNAPSHOT"]` to `plugins`. Your `project.clj`
should now look like this:

```clojure
(defproject example "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2322"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [org.webjars/react "0.11.1"]
                 [om "0.7.1"]
                 [cljs-http "0.1.14"]
                 [ring/ring-core "1.3.1"]
                 [ring/ring-jetty-adapter "1.3.1"]
                 [figwheel "0.1.4-SNAPSHOT"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]
            [lein-figwheel "0.1.4-SNAPSHOT"]
            [lein-ring "0.8.10"]]

  :source-paths ["src"]

  :cljsbuild {:builds [{:id "example"
                        :source-paths ["src"]
                        :compiler
                        {:output-to "resources/public/example.js"
                         :output-dir "resources/public/out"
                         :optimizations :none
                         :source-map true}}]}

  :ring {:handler example.server/app
         :nrepl {:start? true :port 4500}
         :port 8090}

  :global-vars {*print-length* 20})
```

Next, let's edit `client.cljs`. In the namespace declaration under
`:require` add `[figwheel.client :as fw]`. Now let's hook up figwheel
so it can send changes in our project the a browser. Because we use
Ring with the Jetty adapter as an external server, we have to tell
figwheel explicitly where it's websocket is, since it can't just
connect to the same origin, because that is where Ring is listening.

```clojure
(fw/watch-and-reload
 :websocket-url   "ws://localhost:3449/figwheel-ws"
 :jsload-callback
 (fn []
   (println "reloaded")))
```

Now let's start Ring and Figwheel independently. Start both commands inside the `example` directory. In one terminal type `lein figwheel`. It's best to wait for compilation to complete, else the page hasn't much to show us:

```
lein figwheel
Compiling ClojureScript.
Figwheel: Starting server at http://localhost:3449
Figwheel: Serving files from '(dev-resources|resources)/public'
Compiling "resources/public/example.js" from ["src"]...
Successfully compiled "resources/public/example.js" in 20.297 seconds.
notifying browser that file changed:  /example.js
notifying browser that file changed:  /out/goog/deps.js
notifying browser that file changed:  /out/example/client.js
```

In another terminal type `lein ring server`. If you're lucky a browser
will open automatically to `localhost:8090/index.html` and you will
see the text `Hello world from server!`. This text actually really
came from the Ring handler in `server.clj`. If you open a developer
console, you will hopefully see that figwheel has connected to the
browser. (Btw, I used Google Chrome during the writing of this
article.)

```
Figwheel: trying to open cljs reload socket
Figwheel: socket connection established
```

Now let's see if figwheel will pick up a code change in
client.cljs. Let's change the `render` function into this:

```clojure
(render [_]
       (dom/div
        nil
        (dom/h1 nil (:text app))
        (dom/p nil "Hello from Michiel!")))
```

If everything worked out correctly, in the browser you will almost
immediately see that your change is reloaded and rerendered by Om.

# Problem one: component will be remounted upon code reload

One problem of making changes in the file where the call to `om/root`
resides is that the entire component will be unmounted and replaced by
a new instance. To test my claim, let's add line 6 and 19-21 from the snippet below:

```clojure
(om/root
 (fn [app owner]
   (reify
     om/IWillMount
     (will-mount [_]
       (println "I will mount")
       (go (let [response (<! (http/get
                               (str "/welcome-message")))]
             (if (= (:status response)
                    200)
               (om/update! app :text (:body response))
               (om/update! app :text "Server error")))))
     om/IRender
     (render [_]
       (dom/div
        nil
        (dom/h1 nil (:text app))
        (dom/p nil "Hello from Michiel!")))
     om/IWillUnmount
     (will-unmount [_]
       (println "I will unmount"))))
 app-state
 {:target (. js/document (getElementById "app"))})
```

Now make a change in the source code. Just add a space somewhere and
save the file. In the browser's console you will see that React
mounted the component. Now change the source again and press
save. You'll see that React unmounted the component and mounted a new
instance of the component. You really have to pay attention to this,
because if you created resources or `go` loops in `IWillMount` and you
didn't clean them up in `IWillUnMount`, things can get really messy
when you have lots of code reloads. The `go` loops from previous
'ghost' instances will just keep running and your program can get
unpredictable. So, the solution to this problem is just to keep track
of your resources and take the appropriate actions in the
`will-unmount`.

# Problem two: all local state is lost upon reload

TODO: geldt dit ook voor app-state?

# Problem two: a reload of a different does not automatically cause a
  re-render


























```clojure
(+ 1 2 3)
(throw (Exception. "so curious"))
```
