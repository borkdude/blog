Title: Automatic help and completions in babashka CLI
Date: 2026-06-18
Tags: clojure, babashka, cli
Description: Automatic help and completions in babashka CLI

[Babashka CLI](https://github.com/babashka/cli) is a library to write command
line tools. It is available in babashka by default. This library was born out of
some frustration with `clojure -X`'s functionality where people have to write
raw EDN on the command line, which to me isn't a good user experience
(especially not for people using Powershell where quoting rules are different
than in bash and zsh). Babashka wants to give Clojure users a good scripting
experience, no matter what OS or shell you are using.

While babashka CLI had all the ingredients for parsing and formatting options
(for help) and for multi-command (or subcommand) style (e.g. `git remote show
origin`) invocations, you still had to write your own `--help` functionality.
Also babashka CLI didn't offer anything for getting shell completions. These two
gaps existed as open Github issues for about three years now. What held me back
in implementing these features was: A) I found help output for multi-command
CLIs always a bit too opinionated. Every CLI I knew was doing it
differently. Which one should I pick for bb.cli? and B) to implement shell
completions I actually had to know something about shells I did not use
personally.  After looking at a couple of other libs like Howard M. Lewis Ship's
[cli-tools](https://github.com/hlship/cli-tools), and Lambdaisland
[CLI](https://github.com/lambdaisland/cli) and a couple more non-Clojure
libraries, I decided I should just pick a help output that looks reasonable and
offer an API to do your own thing if you want to do that. For implementing the
completion support I re-used the branch that [Sohalt](https://github.com/sohalt)
and I worked on in 2024. Additionally I used Claude Code to get this work over
the hump. Studying how Powershell or nushell completions work _in detail_ just
isn't that interesting to me and I was happy to defer most of the shell-specific
nitty-gritty.
One extra bonus feature that was already in Babashka CLI for a
while is the nested command notation instead of the "table". While this alreaxy
existed in CLI for a while, it's now exposed for users.

Let's dig into an example to learn more about the new features!

## Writing our own git

Yeah, we're going to write our own git, but don't worry, we'll not write our own VCS! We'll leave that up to [Zach Oakes](https://www.youtube.com/@xeuxeuxeuxeu). Just the CLI interface this time and we'll let ourselves off the hook with `println` to fake the implementation.
So here's a bit of code for you to look at. There's a bunch of functions like `clone`, `log`, `checkout` etc. that just print some info to stdout. The `tree` describes the command structure. And the `dispatch` call a the end dispatches the commmand line arguments over the tree.

```clojure
#!/usr/bin/env bb
(require '[babashka.cli :as cli]
         '[clojure.string :as str])

;; stand-ins; a real tool would shell out to git
(def ^:private branches ["main" "develop" "feature/login" "release/2.0"])
(def ^:private remotes  ["origin" "upstream" "fork"])

(defn clone [{:keys [opts]}]
  (println "Cloning" (:url opts)
           (when (:depth opts) (str "(depth " (:depth opts) ")"))))

(defn log [{:keys [opts]}]
  (println "Showing" (or (:max-count opts) "all") (name (:format opts)) "log entries"))

(defn checkout [{:keys [opts]}]
  (println (if (:create opts) "Creating and switching to" "Switching to") (:branch opts)))

(defn remote-add [{:keys [opts]}]
  (println "Added remote" (:name opts) "->" (:url opts)))

(defn remote-remove [{:keys [opts]}]
  (println "Removed remote" (:name opts)))

(defn remote-list [_]
  (run! println remotes))

(def tree
  {:spec {:verbose {:coerce :boolean :desc "Be verbose" :alias :v}}
   :cmd
   {"clone"
    {:fn clone :doc "Clone a repository into a new directory"
     :spec {:url   {:desc "Repository to clone from" :require true}
            :depth {:desc "Create a shallow clone with N commits" :coerce :long}}
     :args->opts [:url]}
    "log"
    {:fn log :doc "Show commit logs"
     :spec {:format    {:desc "Output format" :coerce :keyword
                        :validate #{:oneline :short :full}
                        :default :short}
            :max-count {:desc "Limit the number of commits" :coerce :long :alias :n}}}
    "checkout"
    {:fn checkout :doc "Switch branches"
     :spec {:branch {:desc "Branch to switch to" :coerce :string
                     :complete-fn (fn [{:keys [to-complete]}]
                                    (filter #(str/starts-with? % to-complete) branches))
                     :require true}
            :create {:desc "Create the branch before switching" :coerce :boolean :alias :b}}
     :args->opts [:branch]}
    "remote"
    {:doc "Manage the set of tracked repositories"
     :cmd-order ["add" "remove" "list"]
     :cmd
     {"add"
      {:fn remote-add :doc "Add a remote"
       :spec {:name {:desc "Remote name" :require true}
              :url  {:desc "Remote URL" :require true}}
       :args->opts [:name :url]}
      "remove"
      {:fn remote-remove :doc "Remove a remote"
       :spec {:name {:desc "Remote name" :coerce :string
                     :complete-fn (fn [{:keys [to-complete]}]
                                    (filter #(str/starts-with? % to-complete) remotes))
                     :require true}}
       :args->opts [:name]}
      "list" {:fn remote-list :doc "List the existing remotes"}}}}
   :epilog "Docs: https://example.com/mygit"})

(defn -main [& args]
  (cli/dispatch tree args {:prog "mygit" :help true}))

(apply -main *command-line-args*)
```

The `:prog` value is used in help output and represents the `_program_`
name. The `:help true` setting activates automatic help support. The automatic
help support re-uses the already existing `:desc` (for options) /`:doc` (for
commands) documentation values. When `:validate` is a set of keywords,
auto-completion will pick up on this to autocomplete an option.

Save this code as `mygit.clj` and make it executable.

```bash
chmod +x mygit.clj
```

Note that at the time of writing, babashka CLI version x.y.z isn't part of the newly released bb yet. This is coming soon, but there's more work to be done in babashka, to make babashka tasks even more awesome, which is going to be using part of the new CLI functionality. Stay tuned. For now you can add this snippet to the top of your code to make a bb script pick up on the newest CLI version:

```
(require '[babashka.deps :as deps])
(deps/add-deps '{:deps {org.babashka/cli {:mvn/version "x.y.z"}}})
(require '[babashka.cli] :reload)
```


Now we can invoke this script with `./mygit.clj`. The usage line below will display `"mygit`"`, because of the
`:prog` setting, it's display name, independent of how the script is invoked.

So let's invoke it in a couple of different ways:

```
$ ./mygit.clj clone https://example.com/repo.git --depth 1
Cloning https://example.com/repo.git (depth 1)

$ ./mygit.clj checkout -b feature/login
Creating and switching to feature/login

$ ./mygit.clj log -n 5 --format oneline
Showing 5 oneline log entries

$ ./mygit.clj remote add origin https://example.com/repo.git
Added remote origin -> https://example.com/repo.git
```

## Automatic help

The `:help true` option to `dispatch` enriches the command tree with `--help` / `-h` options at every level, including the top level of the tree. It will also include a terse error message when invalid command line options are provided. So this is the opinionated help support that you can use as a good default, but don't have to use if you want to do your own thing. When `--help`/`-h` is invoked explicitly, the exit code will be `0` and help output is printed to stdoud. On invalid input, output is printed to `stderr` and the exit code will be `1`.

This is what top level help output looks like: `./mygit.clj --help`:

```
Usage: mygit [options] <command>

Commands:
  clone    Clone a repository into a new directory
  log      Show commit logs
  checkout Switch branches
  remote   Manage the set of tracked repositories

Options:
  -v, --verbose  Be verbose
  -h, --help     Show this help

Run "mygit <command> --help" for more information on a command.

Docs: https://example.com/mygit
```

The per line description of a command comes from the `:doc` key, and the per line description of an option comes from the `:desc` key. Trailing prose can be provided via the `:epilog` key, which here is `"Docs: https://example.com/mygit"`.

Every individual command also supports `--help` in a similar way:

```
Usage: mygit checkout [options] <branch>

Switch branches

Options:
      --branch  Branch to switch to (required)
  -b, --create  Create the branch before switching
  -h, --help    Show this help

Run "mygit --help" for global options.
```

Babashka CLI supports the `:args->opts` option to coalesce arguments into options. This is why we see `<branch>` printed as a supported argument. The `(required)` suffix comes from `:require true` in an option's spec and the short `-b` comes from the `:alias` setting.

### Multi-word commands

In our git implementation (unlike the real one), the `remote` command does not invoke a function on its own. It just provides a `:doc` value, describing what the group of child commands are for.

Run `./mygit.clj remote --help` lists the group's children:

```
Usage: mygit remote [options] <command>

Manage the set of tracked repositories

Commands:
  add    Add a remote
  remove Remove a remote
  list   List the existing remotes

Options:
  -h, --help  Show this help

Run "mygit remote <command> --help" for more information on a command.

Run "mygit --help" for global options.
```

Invoking `./mygit.clj remote add --help` show the help of `remote add`, with both positional arguments in the usage line:

```
Usage: mygit remote add [options] <name> <url>

Add a remote

Options:
      --name  Remote name (required)
      --url   Remote URL (required)
  -h, --help  Show this help

Run "mygit --help" for global options.
```

A mistyped or missing command gives a terse error and exits with exit code 1:

```
$ ./mygit.clj statys
Unknown command: statys

Commands:
  clone    Clone a repository into a new directory
  log      Show commit logs
  checkout Switch branches
  remote   Manage the set of tracked repositories

Run "mygit --help" for more information.
```

## Shell completions

In Babashka CLI shell completions are produced dynamically by letting the shell call back into the CLI.
As of today, Babashka CLI supports `bash`, `zsh`, `fish`, `powershell` and `nushell`.

We're just going to show here how to get completions for `zsh` but the process is very similar for other shells.

The `./mygit.clj org.babashka.cli/completions snippet --shell zsh` invocation spits out a `zsh` snippet to stdout specific to this CLI. The `org.babashka.cli/completions` is inserted by babashka CLI.

To enable completions in zsh (after `compinit`), run:

```bash
source <(./mygit.clj org.babashka.cli/completions snippet --shell zsh)
```

This enables completions for commands and options, showing descriptinos on the side. Already used options are not suggested again, unless they are expected to be used multiple times.

```
$ ./mygit.clj remote <TAB>
add     -- Add a remote
remove  -- Remove a remote
list    -- List the existing remotes
```

The `:validate` set on `log --format` doubles as its completion source without adding extra config:

```
$ ./mygit.clj log --format <TAB>
full  oneline  short
```

Dynamic values can be supplied with `:complete-fn`. In our git example, branch
names and remotes are completed by `:complete-fn`.

```
$ ./mygit.clj remote remove <TAB>
origin  upstream  fork
```

```
$ ./mygit.clj checkout <TAB>
main  develop  feature/login  release/2.0
```

To see what the completer returns without a shell, you can call the completions command directly:

```
$ ./mygit.clj org.babashka.cli/completions complete --shell zsh -- remote ''
add	Add a remote
remove	Remove a remote
list	List the existing remotes
--help	Show this help
-h	Show this help
```

## Wrapping up

After holding off and thinking about these issues for a couple of years, I finally bit the bullet and added help and completion support to Babashka CLI. Hope you'll enjoy it!

More exciting related stuff is coming soon. The new Babashka CLI will be integrated into
babashka of course, but also babashka [tasks](https://book.babashka.org/#tasks)
will be pimped with automatic help and cmopletions. I'm not yet done with that
work though.

Meanwhile I've been porting over
[squint](https://github.com/squint-cljs/squint/pull/835) and
[neil](https://github.com/babashka/neil/pull/263) over to the automatic help
already.

Special thanks to

- [Nextjournal](https://github.com/nextjournal) whose commercial app I'm taking as a case study for this work
- [Clojurists Together](https://www.clojuriststogether.org/)
- [Sponsors on Github](http://github.com/sponsors/borkdude)

