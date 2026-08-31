Title: Babashka 1.13.220 gets FFI
Date: 2026-08-31
Tags: clojure, babashka, ffi, tasks
Description: Babashka 1.13.220: call C libraries directly with babashka.ffi

Today babashka 1.13.220 is released, with a new `babashka.ffi` namespace for calling C libraries directly from Babashka scripts.
The [babashka.ffi library](https://github.com/babashka/ffi) is also available as a standalone library for JVM Clojure, so you can use it in your Clojure projects as well. Note that the API is still experimental, although no changes are currently planned. It just needs more exposure and your feedback :). Here's a small demo.

## Calling C

This example loads `libz` from your system and requests the version.

```clojure
(require '[babashka.ffi :as ffi :refer [defcfn]])

(def zlib (ffi/load-system-library "z"))
(def zlib-version (ffi/cfn zlib "zlibVersion" [] :string))

(zlib-version)
;;=> "1.3.1"
```

This example loads an OS-specific library for doing math:

```clojure
(ffi/load-library
 {:mac "libm.dylib"
  :linux "libm.so.6"
  :windows "ucrtbase.dll"})

(defcfn cos "cos" [:double] :double)
(defcfn pow "pow" [:double :double] :double)

(cos 0.0)      ;;=> 1.0
(pow 2.0 10.0) ;;=> 1024.0
```

To get a feeling for how to use it in larger, non-trivial projects, read the library [guide](https://github.com/babashka/ffi/blob/main/doc/guide.md).
Some of the API decisions like `defcfn` are clearly inspired by [coffi](https://github.com/IGJoshua/coffi), so I want to thank Joshua Suskalo for leading the way with his excellent library. But `babashka.ffi` is not simply a copy of coffi. It does a few things differently. You can provide an explicit library (or a function or delay that resolves to one) to `defcfn` for example. Also it has a `place` concept (inspired by Specter's paths) that efficiently lets you read from and write to structs and unions. Like coffi, babashka.ffi builds on `java.lang.foreign` and makes you manage memory explicitly through arenas. One benefit of this is that you'll get exceptions rather than segfaults that tear down your REPL and you can use `with-open` to release allocated memory.

## Install

To use `babashka.ffi` and libraries that build on it, you have to use a dynamically linked version of babashka. On Mac and Windows this was always the default. On Linux, the static binary was preferred historically since it did not depend on your system's libc and zlib.
In this release we flip this default to a mostly-static binary: all the shared C libraries that babashka needs are statically linked, and glibc is the only dynamically linked part. The aarch64 binary, although it carries `-static` in its name, was already built this way. Babashka on Linux is built in a container that pins the glibc version to the lowest one possible so it should work on all mainstream LTS versions of Linux today. If you still prefer the fully static binary, you can use the install script with the `--static` flag.
If you use a package manager or a GitHub Action to install babashka, it may not yet be up to date with this new policy. If that is the case, feel free to open an issue at the babashka GitHub repo and I'll reach out to get this fixed. Meanwhile you can install babashka using the installer script on GitHub to a temporary directory to get a second installation of babashka with FFI enabled:

```
$ curl -sLO https://raw.githubusercontent.com/babashka/babashka/master/install
$ bash install --dir /tmp/bb-test
$ /tmp/bb-test/bb -e "(require '[babashka.ffi :as ffi]) (ffi/load-system-library \"z\")"
```

The installer script probes your system for the supported glibc version and falls back to the fully static version when necessary.

## Demos

To validate the design of babashka.ffi, I built a few shiny [demos](https://github.com/babashka/ffi/tree/main/examples):

- [pacman.clj](https://github.com/babashka/ffi/blob/main/examples/pacman.clj):
  pac-man with the classic ghost personalities (requires raylib)
- [doom.clj](https://github.com/babashka/ffi/blob/main/examples/doom.clj):
  a raycaster with textures and sprites (requires raylib)
- [helitorus.clj](https://github.com/babashka/ffi/blob/main/examples/helitorus.clj):
  a helix around a torus (requires raylib)
- [gtk4.clj](https://github.com/babashka/ffi/blob/main/examples/gtk4.clj):
  a native GTK 4 window rendering from an atom
- [portaudio.clj](https://github.com/babashka/ffi/blob/main/examples/portaudio.clj):
  an arpeggio through a realtime audio callback
- [python.clj](https://github.com/babashka/ffi/blob/main/examples/python.clj):
  embedded CPython calling back into Clojure

<img src="assets/1.13.220-pacman.png" style="max-width:420px;width:100%" alt="pac-man running in babashka through babashka.ffi and raylib">

A one-liner to try these demos:

```
$ bb -e '(load-string (slurp "https://raw.githubusercontent.com/babashka/ffi/main/examples/pacman.clj"))'
```

## FFI-based libraries

To validate the design of babashka.ffi even more, a couple of new libraries were born. These libraries mostly resemble existing pods but now use FFI to fulfill similar use cases.

- [babashka.sqlite](https://github.com/babashka/babashka.sqlite)
- [babashka.duckdb](https://github.com/babashka/babashka.duckdb)
- [babashka.postgres](https://github.com/babashka/babashka.postgres)
- [filewatcher](https://github.com/babashka/filewatcher)

One cool thing you could not do with a pod before is defining a Clojure function in SQLite:

```clojure
(require '[babashka.sqlite :as sq])

(sq/with-conn [db nil]
  (sq/create-function! db "initials"
    (fn [s] (apply str (map first (clojure.string/split s #" ")))))
  (sq/query db ["select initials(?) i" "gerald jay sussman"]))
;;=> [{:i "gjs"}]
```

## Tasks: :exec-fn composition

This release also has some really nice task improvements: `:exec-fn` tasks now compose through `:depends`. A task can depend on another
CLI task, and the dependency's options parse, coerce and show up in `--help`
and shell completion:

```clojure
{:tasks
 {compile {:exec-fn build/compile-sources
           :cli {:spec {:release {:coerce :boolean}}}}
  jar     {:depends [compile]
           :exec-fn build/jar}}}
```

```
$ bb jar --help
...
Inherited options:
  --release
```

Also you can now directly provide `:exec-args` on a task:

```clojure
{:tasks
 {deploy {:exec-fn deploy/run
          :exec-args {:env "staging"}}}}
```

A `:cmd` tree can now be provided through a var, whose namespace is loaded on demand:

```clojure
{:tasks
 {cli {:cmd my.project.cli/commands}}}
```

## AI disclosure

While developing FFI and while validating the design through examples and writing libraries, I have made use of LLM assistance.

## Wrapping up

Hope you'll like these new features!

The full changelog can be found [here](https://github.com/babashka/babashka/blob/master/CHANGELOG.md).
