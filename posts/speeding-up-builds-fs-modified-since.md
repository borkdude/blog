## Babashka tasks

When designing [babashka tasks](https://book.babashka.org/#tasks) I was aiming
to create something like `make`, `just`, `npm run`: run tasks with almost no
startup time overhead, possibly with dependencies between then, but with a
language I like to work with: Clojure. One thing I deliberately left out for now
was a mechanism that talks about targets and whether to rebuild those targets or
not. Another inspiration for babashka tasks was
[Mach](https://github.com/juxt/mach) by [JUXT](https://www.juxt.pro/) which does
have such a mechanism, which leans heavily on one function:
`(mach.core/modified-since [file dir])`.

## Babashka.fs

Instead of deciding on a built-in mechanism for rebuilding targets, I included
the `modified-since` function in [babashka.fs](https://github.com/babashka/fs),
which is a library for common file system operations like copying, deleting or
moving files, creating symlinks, etc. The `babashka.fs` library is included in
[babashka](https://babashka.org/) but can also be used as a library within
Clojure JVM programs. Now let's see what this function does.

## `fs/modified-since`

Let's first take a look at the [docstring](https://babashka.org/fs/babashka.fs.html#var-modified-since):

> `(modified-since anchor file-set)`
>
> Returns seq of regular files (non-directories, non-symlinks) from file-set
  that were modified since the anchor path.  The anchor path can be a regular file
  or directory, in which case the recursive max last modified time stamp is used
  as the timestamp to compare with.  The file-set may be a regular file, directory
  or collection of files (e.g. returned by glob). Directories are searched
  recursively.

In other words:

- The `anchor` argument can be a file or directory.
- The `file-set` argument can be a file or directory.
- The returned value is a seq of regular files from `file-set` that were
  _modified since_ the maximum of all the modified timestamps in `anchor`.

Let's see it in action.

```
$ touch a
$ touch b
$ bb -e '(fs/modified-since "a" "b")'
(#object[sun.nio.fs.UnixPath 0x108eff48 "b"])
```

We see that the file `b` was modified since `a`. This is because we touched
`b` after we touched `a`.

Now let's touch `a` again:

```
$ touch a
$ bb -e '(fs/modified-since "a" "b")'
()
```

In other words, if `a` was a build target and `b` was a source file, then we
didn't have to rebuild `a` again since it was already up to date.

What if `a` doesn't exist? In real scenarios this would probably mean that we
have to build `a` for the first time or that it was deleted, possibly because of
`clean` task. In that case `b` is returned as well:

```
$ touch a
$ bb -e '(fs/modified-since "a" "b")'
(#object[sun.nio.fs.UnixPath 0x108eff48 "b"])
```

When the `file-set` is a directory, you get all of the files that were modified since `anchor`:

```
$ mkdir -p src
$ touch src/a src/b
$ bb -e '(fs/modified-since "a" "src")'
(#object[sun.nio.fs.UnixPath 0x69351174 "src/a"] #object[sun.nio.fs.UnixPath 0x40a401b4 "src/b"])
$ touch a src/a
$ bb -e '(fs/modified-since "a" "src")'
(#object[sun.nio.fs.UnixPath 0x778e11d9 "src/a"])
```

## Real life examples

Today I used this function to speed up the rendering of this blog (which
reminded me to blog about this function):

``` clojure
(doseq [{:keys [file title date legacy]} posts]
  (let [cache-file (fs/file ".work" (html-file file))
        markdown-file (fs/file "posts" file)
        stale? (seq (fs/modified-since cache-file markdown-file))
        body (if stale?
               (let [body (markdown->html markdown-file)]
                 (spit cache-file body)
                 body)
               (slurp cache-file))
...
```

Here I used `fs/modified-since` simply as a predicate to check if the cached
HTML output was newer than the markdown input file: if the result is non-empty,
we have to rebuild the markdown to HTML and then store it in a `.work`
directory. The rendering of this blog went down from rougly 1 second to 200ms
after building it for the first time from scratch. There are more places where I
could do a similar optimization but for now it's fast enough.

I've already used this function several times in `bb.edn` tasks file to speed up
Clojure
builds. [Here](https://github.com/clj-easy/graal-build-time/blob/130819a1d953f5864b8ef3d273997dfa014860c2/bb.edn#L30)
is an example of not rebuilding a jar file when it's already up to date, without
starting a JVM:


``` clojure
  jar {:doc "Build jar"
       :depends [compile-sources]
       :task (if (seq (fs/modified-since bs/jar-file [bs/class-dir]))
               (clojure "-T:build jar")
               (println "Jar is already up to date" bs/jar-file))}
```

The technique I used there is to make a `build_shared.clj` file which is loaded
both by [tools.build](https://github.com/clojure/tools.build) and babashka.
Before launching a JVM, babashka can check if there's anything to rebuild. If
the sources for the jar aren't modified since the jar itself, then we can skip
launching a JVM entirely. But also within the `build.clj` which runs inside of a
JVM the same technique can be used.

Here is one more example of how I used `fs/modified-since` to skip a `lein uberjar` invocation.

```
build-server {:doc "Produces lsp server standalone jar"
              :depends [java1.8 update-project-clj -uberjar]
              :task (when (seq (fs/modified-since -uberjar ["server/project.clj"
                                                            "server/src"]))
                      (shell {:dir "server"}
                        "lein with-profiles -user do clean, uberjar"))}
```

The usage of this function isn't tied to any specific build system or scenario:
it's just a comparison of some files with another bunch of files, which makes it
generally applicable. This form of optimization does come with a trade-off:
e.g. when you forget to take into account changes to `deps.edn` or so, you might
get a false positive 'nothing to do here'. It might be a good idea to put
`.cpcache` in the fileset in a `deps.edn` project as a sign that the classpath
changed. As a part of [TDEPS-83](https://clojure.atlassian.net/browse/TDEPS-83),
which will land in the [clojure CLI](https://clojure.org/guides/deps_and_cli)
soon (and soon after that in `bb` itself via
[deps.clj](https://github.com/borkdude/deps.clj)), even transitive `:local/root`
changes will be picked up in this directory.

Hope you enjoyed this little write-up about `fs/modified-since`. Thanks again to
JUXT Mach where I got the inspiration from.

Happy rebuilding!
