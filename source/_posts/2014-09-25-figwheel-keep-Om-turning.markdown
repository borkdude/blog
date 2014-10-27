---
layout: post
title: Figwheel keep Om turning!
date: 2014-09-25 21:00:50 +0200
comments: true
published: true
categories: [clojure, clojurescript, figwheel, om]
---
How to combine figwheel, Om and Ring in one application
## tl;dr
In this article you will find a configuration of figwheel with
Om and a Ring server in one application.

The following issues will be addressed:

 * Om root component will be remounted upon code reload
 * App-state is lost when code is reloaded
 * Code changes in other cljs files do not cause a re-render

## Let's get Om with it

For a few weeks I have been using Clojurescript and
[Om](https://github.com/swannodette/om) for front end development. Om
is a Clojurescript library based on the famous
[React](http://facebook.github.io/react/).
[Figwheel](https://github.com/bhauman/lein-figwheel) is a tool that
can speed up Clojurescript and Om development by reloading code in the
browser without refreshing an entire page.

Figwheel comes in the form of a [leiningen](http://leiningen.org/)
plugin that uses
[lein-cljsbuild](https://github.com/emezeske/lein-cljsbuild) to
compile Clojurescript and pushes the resulting Javascript to the
browser, which is then reloaded. Together with React this is a
powerful combination. As you change code in your editor and press
save, the changes can be instantly reflected in the page you were
viewing... if you configure things properly.

The project I'm currently working on consists of several Clojurescript
files. Often an Om component resides in a file of its own. For my
workflow it would make sense to be able to edit a component in one
file, save the code and see the change in the browser immediately.  So
here is what I've done.

Let's make a project based on the
[wrom](https://github.com/borkdude/wrom) template.

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

The application comprises a server and client part. All sources files
reside in one directory `src`. Let's add figwheel to our project. Add
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

Let's edit `client.cljs`. In the namespace declaration under
`:require` add `[figwheel.client :as fw]`. Now we'll hook up figwheel
so it can send changes in our project to a browser. Because we use
Ring with the Jetty adapter as an external server, we have to tell
figwheel explicitly where it's websocket is, since it can't just
connect to the same origin.

```clojure
(fw/watch-and-reload
 :websocket-url   "ws://localhost:3449/figwheel-ws"
 :jsload-callback
 (fn []
   (println "reloaded")))
```

Start Ring and Figwheel independently, both from inside the `example`
directory. In one terminal type `lein figwheel` (or optionally supply
the name of the build: `lein figwheel example`). It's best to wait for
the clojurescript compilation to complete before starting Ring.

```
$ lein figwheel
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
see the text `Hello world from server!`. This text really came from
the Ring handler in `server.clj`. If you open a developer console, you
will see that figwheel has connected to the browser:

{% img center /images/2014-09/figwheel/figwheel-connecting.png %}

Now let's see if figwheel will pick up a code change in
client.cljs. Let's change the `render` function to:

```clojure
(render [_]
       (dom/div
        nil
        (dom/h1 nil (:text app))
        (dom/p nil "Hello from Michiel!")))
```

If everything worked, in the browser you will almost immediately see
that your change is reloaded and re-rendered by Om.

## Om root component will be remounted upon code reload

One problem of making changes in the file where the call to `om/root`
resides is that the entire component tree will be unmounted and
replaced by a new instance. To verify my claim, let's add line 6 and
19-21 from the snippet below:

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

Now make a change anywhere in the source code and save the file. In
the browser's console you will see that Om mounted the
component. Now change the source again and press save. You'll see that
Om unmounted the component and mounted a new instance of the
component. You really have to pay attention to this, because if you
created resources or `go` loops in `will-mount` and you didn't clean
them up in `will-unmount`, things can get really messy when you have
lots of code reloads. The `go` loops from unmounted instances will
just keep running and your program can get unpredictable. So, the
solution to this problem is to keep track of your resources and take
the appropriate actions in the `will-unmount`.

## App-state is lost when code is reloaded
Let's change our app state to

```clojure
(def app-state (atom {:text "" :button "unclicked"}))
```

and the render function to

```clojure
(render [_]
       (dom/div
        nil
        (dom/p nil (pr-str app))
        (dom/button #js {:onClick #(om/update! app
                                              :button "clicked")}
                    "Click to change state")))
```

In the render function we show a printed version of the cursor. Now
let's click the button. The `app-state` updated and the component is
reflecting this. Now let's change the code of `client.cljs` and
save. What do we see? Our app-state is back to the initial state. This
is because `app-state` is being redefined by the code reload. To avoid
this, change `def` to `defonce`:

```clojure
(defonce app-state (atom {:text "" :button "unclicked"}))
```

Now the app-state will survive a code reload. Sometimes it's that easy
to write re-loadable code.

## Code changes in other cljs files do not cause a re-render

Let's make a second file called `child.cljs` like this:

```clojure
(ns example.child
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defn child [cursor owner]
  (om/component
   (dom/div nil
            (dom/p nil "I'm a child.")
            (dom/p nil (str "I have local state: "
                            (pr-str (om/get-state owner))))
            (dom/button #js {:onClick
                             #(om/update-state! owner
                                                :clicks inc)}
                        "Click me to update my local state"))))
```

Let's `require` and `om/build` this child component in our root component, so it will
appear on the page. I omitted irrelevant parts.

```clojure
(ns example.client
  ...
  (:require [om.core :as om :include-macros true]
            ...
            [example.child :refer (child)]))

...

(om/root
 (fn [app owner]
   (reify
     ...
     om/IRender
     (render [_]
       (om/build child app))
     ...  )))
 app-state
 {:target (. js/document (getElementById "app"))})
...
```

I'm not entirely sure if figwheel handles changes in namespace
declarations well, so just refresh the page. You will see the button
from the child component on the screen. Click a few times:

{% img center /images/2014-09/figwheel/child-component1.png %}

Now let's change some code of the child component. For example, change
the text in the button to "Click me to update me!". When saved, you
won't see the change reflected in your browser. figwheel has reloaded
child.cljs but the problem is that Om doesn't 'know' about this. Let's
tell Om. Let's summon the power of `core.async`.

Change the `:require` entry for `core.async` to
`[cljs.core.async :refer [<! chan put!]]` so we can create channels
and put something in them.

In client.cljs add a channel. Place it below the definition of `app-state`:

```
(defonce re-render-ch (chan))
```

In `will-mount` we'll now spawn a `go` loop that keeps reading from
`re-render-ch`:

```clojure
(will-mount [_]
  (println "I will mount")
  (go (loop []
    (when (<! re-render-ch)
      (om/refresh! owner)
      (recur))))
```

All we have to do now is put a (truthy) message into the channel and
the root component will re-render itself. This can be done in the
callback provided to `fw/watch-and-reload`:

```clojure
(fw/watch-and-reload
 :websocket-url "ws://localhost:3449/figwheel-ws"
 :jsload-callback
 (fn []
   (println "reloaded")
   (put! re-render-ch true)))
```

Refresh the page so everything is in place. Now change the text of the
button again in child.cljs and you'll see the component being
instantly re-rendered in the browser. Observe however that all local
state in the child component is lost. Because the code of the child
component changed, Om has to remount the component. Again, be
conscious of resources and go loops hat are creating during the mount
phase and clean them up if necessary.

Let's change to child to show and update the cursor instead of local
state.

```clojure
(defn child [cursor owner]
  (om/component
   (dom/div nil
            (dom/p nil "I'm a child.")
            (dom/p nil (str "My cursor:"
                            (pr-str cursor)))
            (dom/button #js {:onClick
                             #(om/transact!
                               cursor
                               :clicks inc)}
                        "Click me!"))))
```

Click a few times and you'll see something like this:

{% img center /images/2014-09/figwheel/child-component2.png %}

Now change the text of the button in child.cljs again and save. You'll
see that the state of the cursor is preserved, as expected.

## Update October 11th 2014

The use of a channel to refresh a component was a little invasive and to be honest, I didn't like it much. Arne Brasseur pointed out in the comments that refreshing of Om components can be implemented simpler, because `om/root` is idempotent. This means it may be called multiple times and all will be well.

I changed the code in `main.cljs` according to the suggestion of Arne:

```clojure
(ns example.client
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.async :refer [<! chan put!]]
            [cljs-http.client :as http]
            [figwheel.client :as fw]
            [example.child :refer (child)]))

(enable-console-print!)

(defonce app-state (atom {:text ""}))

(defn main []
  (om/root
   (fn [app owner]
     (reify
       om/IRender
       (render [_]
         (dom/div
          nil
          (om/build child app)))
       om/IWillUnmount
       (will-unmount [_]
         (println "I will unmount"))))
   app-state
   {:target (. js/document (getElementById "app"))}))

(fw/watch-and-reload
 :websocket-url "ws://localhost:3449/figwheel-ws"
 :jsload-callback
 (fn []
   (println "reloaded")
   (main)))

(defonce initial-call-to-main (main))
```

The differences:

- no invasive channel and call to `om/refresh!`
- the call to `om/root` is now wrapped inside a function `main`
- `main` is called on initial page load and will be called by figwheel upon code reload (no matter which ClojureScript file, which the point of putting the call to `main` here)

Arne Brasseur is the author of the excellent [Chestnut](https://github.com/plexus/chestnut) template, that embeds Figwheel as one of its dev tools. I suggest you try it out if you haven't. David Nolen, the author of Om, just published a [demo video](https://www.youtube.com/watch?v=gI3fJKmvgq4) of Chestnut.

The completed (and updated) code of this blog post can be viewed [here](https://github.com/borkdude/figwheel-keep-om-turning).

If you liked my post or want to suggest an improvement, please leave a
comment. Thanks for reading!
