{:title "Using the Clojure REPL with Java or Scala", :date "2016-07-26", :categories #{"scala" "clojure" "repl"}, :legacy true}

Clojure is a tool that enables interactive development and runtime
inspection. Even when we work in other programming languages,
Clojure can still be useful. Especially when that other language lives
on the JVM.

Let's take Scala for example. Scala has a REPL. The REPL can be used
to test-drive software in development. But it doesn't really let you
inspect a running program when you didn't start it with `sbt
console`. So let's use Clojure for that. We will walk through a simple
Scala program that allows runtime inspection of an otherwise unknown
state.

We'll need an sbt project for this example. Make a directory and put a
`build.sbt` in it. The only dependency in this example is Clojure.

```bash
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.clojure" % "clojure" % "1.8.0"
)
```

In `src/main/scala/example.scala` we add the following imports:

``` scala
import clojure.java.api.Clojure
import clojure.java.api.Clojure.{`var` => cvar}
```

We'll be using
[Clojure's Java API](http://clojure.github.io/clojure/javadoc/).  In
Scala `var` is a reserved keyword, so I'm renaming it to `cvar`, since
I don't like the backticks in my code.

Next, let's create an object that will contain some random value:

``` scala
object BusinessLogic {
  val x = Math.random() // I wonder what this value is at runtime... 
}
```

Also, let's create an `App` so we can run our program with `sbt run`:

``` scala
object Main extends App {
}
```

If we would execute `sbt run`, we would never know the value of `x` in
`BusinessLogic`. We could add a `println`, but what if `x` was a `var`
and it's value would change over time? Clojure lets us inspect this
value at any given point in time. We'll start a
[socket server](http://clojure.org/reference/repl_and_main#_launching_a_socket_server)
that is available since Clojure 1.8.0.

``` scala
object Main extends App {
  val require = cvar("clojure.core","require")
  require.invoke(Clojure.read("clojure.core.server"))
  val startServer = cvar("clojure.core.server","start-server")
  val options = Clojure.read("""{:port 4555 :accept clojure.core.server/repl 
    :name repl :server-daemon false}""")
  startServer.invoke(options)
}
```

This may seem a little intimidating, so I'll explain it line by line.
On line 2 we get a reference to Clojure's `require` so we can... yes,
require namespaces.  On line 3 we read a string so we get the symbol
that `require` needs to load the `clojure.core.server` namespace.  On
line 4 we get a reference to the `start-server` `var`. On line 5 we
define a bunch of settings. Their meaning can be found
[here](http://clojure.org/reference/repl_and_main#_launching_a_socket_server).


In Clojure this would read as:

``` clojure
(require 'clojure.core.server)
(clojure.core.server/start-server 
  {:port 4555
   :accept clojure.core.server/repl
   :name 'repl
   :server-daemon false})
```

but since we're coming from Scala and have to use Clojure's Java API,
it looks a bit more involved.

On the last line we invoke `start-server` with said options. When we
execute `sbt run` again, the process will block,
because `:server-daemon` was set to `false`:

``` scala
~/dev/scala/cljrepl $ sbt run
[info] Loading global plugins from /Users/Borkdude/.sbt/0.13/plugins
[info] Set current project to cljrepl (in build file:/Users/Borkdude/Dropbox/dev/scala/cljrepl/)
[info] Compiling 1 Scala source to /Users/Borkdude/Dropbox/dev/scala/cljrepl/target/scala-2.11/classes...
[info] Running Main
```

This gives us the chance to connect to the socket repl with good ol' telnet:

``` bash
~ $ rlwrap telnet localhost 4555
Trying 127.0.0.1...
Connected to localhost.
Escape character is '^]'.
user=> (import 'BusinessLogic)
BusinessLogic
user=> (BusinessLogic/x)
0.722431948099764 ;; <-- aah! 
user=> :repl/quit
Connection closed by foreign host.
```

See what we did there?

Clojure's socket REPL also supports initialization via a JVM property. 
To try this, add these lines to `build.sbt`:

``` scala
fork := true
javaOptions := Seq("-Dclojure.server.repl={:port 4555 :accept clojure.core.server/repl :server-daemon false}")
```

The `App` can now be reduced to something like:

``` scala
object Main extends App {
  val require = cvar("clojure.core","require")
}
```

This doesn't do much except taking care that Clojure is initialized
(for more info, read the last paragraph in
[this Stackoverflow answer](http://stackoverflow.com/questions/2181774/calling-clojure-from-java/23555959#23555959)).

This Scala example translates fairly straightforward to Java. Now don't tell your
boss you're using a different programming language. After all, Clojure
is just a Java library that gives you superpowers :-).

PS: it may be wise to turn this off in production because of the
security risk; on the other hand, a Clojure REPL has saved my day more
than once in the past!
