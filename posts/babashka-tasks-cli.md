Title: Babashka tasks with automatic help and completions
Date: 2026-07-27
Tags: clojure, babashka, cli, tasks
Description: Babashka tasks with automatic help and completions

[Babashka tasks](https://book.babashka.org/#tasks) is a project task manager
that is part of babashka since May 2021.  It's a practical way to manage a
Clojure or other software project. Just invoking `bb tasks` gives you an
overview of everything that is important to developers of this
project. E.g. launching a REPL, bumping and releasing new versions, etc.

Example `bb.edn`:

```clojure
{:tasks
 {dev   {:doc "Start the dev system"
         :task (clojure "-M:dev")}

  test  {:doc "Run the tests"
         :task (clojure "-M:test")}

  build {:doc "Build an uberjar"
         :depends [test]
         :task (clojure "-T:build uber")}}}
```

```console
$ bb tasks
The following tasks are available:

dev    Start the dev system
test   Run the tests
build  Build an uberjar
```

The `bb build` task runs `test` first because it `:depends` on it.

## Before: `exec` and `-x`

[Babashka CLI](https://github.com/babashka/cli) is a command line parsing
library that comes bundled with babashka.  Babashka tasks already had some
integration with it before, through the `exec` function. The `exec` function
auto-resolve a var symbol and that var can carry a `:org.babashka/cli`
specification.

`bb.edn`:
```clojure
{:paths ["bb"]
 :tasks {dev {:doc "Start the dev system"
              :task (exec 'tasks/dev)}}}
```

`bb/tasks.clj`:
```clojure
(ns tasks
  (:require [babashka.tasks :refer [clojure]]))

(defn dev
  {:org.babashka/cli {:spec {:port {:coerce :int :default 8080}}}}
  [opts]
  (clojure "-X:dev" opts))
```

```console
$ bb dev
dev system on port 8080

$ bb dev --port 3000
dev system on port 3000
```

Babashka's `-x` flag already invoked that function by its qualified name:

```console
$ bb -x tasks/dev
dev system on port 8080

$ bb -x tasks/dev --port 3000
dev system on port 3000
```

Since a couple of months, Babashka CLI provides [automatic help and
completions](https://blog.michielborkent.nl/babashka-cli-help-and-completions.html).
Babashka tasks now uses those when configured with either `:exec-fn` or `:cmd`.

## Opting in with `:exec-fn`

To opt-in to that new behavior, the `dev` task becomes:

`bb.edn`:
```clojure
{:paths ["bb"]
 :tasks {dev {:exec-fn tasks/dev}}}
```

`bb/tasks.clj`:
```clojure
(ns tasks
  (:require [babashka.tasks :refer [clojure]]))

(defn dev
  "Start the dev system"
  {:org.babashka/cli {:spec {:port {:coerce :int
                                    :default 8080
                                    :desc "HTTP port"}}}}
  [opts]
  (clojure "-X:dev" opts))
```

```console
$ bb dev --help
Usage: bb dev [options]

Start the dev system

Options:
      --port  HTTP port (default: 8080)
  -h, --help  Show this help
```

The function's docstring also becomes the task description in `bb tasks`:

```console
$ bb tasks
The following tasks are available:

dev  Start the dev system
```

## Task explosion

One thing I noticed in some projects is that during their lifecycle, tasks
tended to get copied when they need another option.

```console
$ bb tasks
The following tasks are available:

dev                              Start the dev system
dev:with-transactor              Start the dev system with a transactor
dev:with-transactor:with-nrepl   Start the dev system with a transactor and nREPL
```

I now think that this is a "task smell" and this should be only one task with command line options:

`bb/tasks.clj`:
```clojure
(defn dev
  "Start the dev system"
  {:org.babashka/cli {:spec {:port       {:coerce :int
                                          :default 8080
                                          :desc "HTTP port"}
                             :transactor {:coerce :boolean
                                          :desc "Start a transactor"}
                             :nrepl      {:coerce :boolean
                                          :desc "Start an nREPL server"}}}}
  [opts]
  (clojure "-X:dev" opts))
```

```console
$ bb dev --help
Usage: bb dev [options]

Start the dev system

Options:
      --port        HTTP port (default: 8080)
      --transactor  Start a transactor
      --nrepl       Start an nREPL server
  -h, --help        Show this help

$ bb dev --transactor
dev system on port 8080 transactor=true nrepl=false
```

## Shell completions

To enable completions, you can use `bb org.babashka.cli/completions snippet --shell <shell>` for your specific shell.

E.g. for zsh, we do this by adding this to `~/.zshrc` after `compinit`:

```
source <(bb org.babashka.cli/completions snippet --shell zsh)
```

For Bash, fish, PowerShell, and Nushell, see
[Completions](https://github.com/babashka/cli#completions) in the Babashka CLI
README.

After doing that, task name completion includes descriptions on auto-complete:

```
$ bb <TAB>
dev  -- Start the dev system
```

Task specific option completions includes descriptions:

```
$ bb dev <TAB>
--help  -h  -- Show this help
--port      -- HTTP port
```

You can call the completions command directly to inspect its output without a shell, e.g. for debugging:

```
$ bb org.babashka.cli/completions complete --shell zsh -- dev ''
--port	HTTP port
--help	Show this help
-h	Show this help
```

## Restricting values with `:enum`

Use `:enum` to restrict an option to a fixed set of values:

```clojure
(defn dev
  "Start the dev system"
  {:org.babashka/cli {:spec {:port {:coerce :int
                                    :default 8080
                                    :desc "HTTP port"}
                             :env  {:desc "Environment"
                                    :enum ["dev" "staging" "prod"]
                                    :default "dev"}}}}
  [opts]
  (clojure "-X:dev" opts))
```

The help output lists the allowed values:

```console
$ bb dev --help
Usage: bb dev [options]

Start the dev system

Options:
      --port  HTTP port (default: 8080)
      --env   Environment (one of: dev, staging, prod) (default: dev)
  -h, --help  Show this help
```

The value is also validated:

```console
$ bb dev --env qa
Error: Invalid value for option --env: qa. Expected one of: dev, staging, prod

Usage: bb dev [options]

Run "bb dev --help" for more information.
```

Shell completion also uses the `:enum` values:

```
$ bb dev --env <TAB>
dev  prod  staging
```

## Commands

Add commands to a task with `:cmd`:

`bb.edn`:
```clojure
{:paths ["bb"]
 :tasks {dev {:exec-fn tasks/dev}

         db  {:doc "Manage the database"
              :cmd {"migrate" {:exec-fn tasks/db-migrate}
                    "seed"    {:exec-fn tasks/db-seed}}}}}
```

Each leaf uses `:exec-fn` to point to a function:

`bb/tasks.clj`:
```clojure
(defn db-migrate
  "Run pending migrations"
  {:org.babashka/cli {:spec {:env {:desc "Environment"
                                   :enum envs
                                   :default "dev"}}}}
  [{:keys [env]}]
  (println "migrating" env))

(defn db-seed
  "Seed the database with fixtures"
  {:org.babashka/cli {:spec {:env {:desc "Environment"
                                   :enum envs
                                   :default "dev"}}}}
  [{:keys [env]}]
  (println "seeding" env))
```

A top-level `:exec-fn` can handle `bb db`. When no command is specified:

```console
$ bb db
No command given.
```

Automatic help lists the commands and provides separate help for each:

```console
$ bb db --help
Usage: bb db [options] <command>

Manage the database

Commands:
  migrate  Run pending migrations
  seed     Seed the database with fixtures

Options:
  -h, --help  Show this help

Run "bb db <command> --help" for more information on a command.
```

Shell completion includes commands and option values:

```
$ bb db <TAB>
migrate  -- Run pending migrations
seed     -- Seed the database with fixtures

$ bb db migrate --env <TAB>
dev  prod  staging
```

## CLI settings with `:cli`

Use `:cli` for Babashka CLI settings, such as a help epilog:

`bb.edn`:
```clojure
{:paths ["bb"]
 :tasks {dev {:exec-fn tasks/dev}

         db  {:doc "Manage the database"
              :cli {:epilog "Migration code is in resources/migrations."}
              :cmd {"migrate" {:exec-fn tasks/db-migrate}
                    "seed"    {:exec-fn tasks/db-seed}}}}}
```

```console
$ bb db --help
Usage: bb db [options] <command>

Manage the database

Commands:
  migrate  Run pending migrations
  seed     Seed the database with fixtures

Options:
  -h, --help  Show this help

Run "bb db <command> --help" for more information on a command.

Migration code is in resources/migrations.
```

For tasks that require code outside of `bb.edn`, `:cli` may contain a fully
qualified var symbol:

`bb.edn`:
```clojure
{:paths ["bb"]
 :tasks {dev {:exec-fn tasks/dev}

         db  {:doc "Manage the database"
              :cli tasks/db-cli
              :cmd {"migrate" {:exec-fn tasks/db-migrate}
                    "seed"    {:exec-fn tasks/db-seed}}}}}
```

`bb/tasks.clj`:
```clojure
(defn- report-error
  [{:keys [msg]}]
  (binding [*out* *err*]
    (println "db:" msg))
  (System/exit 1))

(def db-cli
  {:epilog "Migration code is in resources/migrations."
   :error-fn report-error})
```

```console
$ bb db migrate --env qa
db: Invalid value for option --env: qa. Expected one of: dev, staging, prod
```

Top-level `:cli` options in `:tasks` apply to every CLI task:

```clojure
{:paths ["bb"]
 :tasks {:cli tasks/db-cli
         ...}}
```

## Multi-line docs

A `:doc` value may be a vector of lines:

`bb.edn`:
```clojure
db {:doc ["Manage the database"
          "Migrations are applied in order and are idempotent."]
    :cmd {"migrate" {:exec-fn tasks/db-migrate}
          "seed"    {:exec-fn tasks/db-seed}}}
```

```console
$ bb db --help
Usage: bb db [options] <command>

Manage the database
Migrations are applied in order and are idempotent.

Commands:
  migrate  Run pending migrations
  seed     Seed the database with fixtures

Options:
  -h, --help  Show this help

Run "bb db <command> --help" for more information on a command.
```

The `bb tasks` overview prints only the first line:

```console
$ bb tasks
The following tasks are available:

dev  Start the dev system
db   Manage the database
```

## Availability

The task integration is available in babashka 1.13.219. The Babashka CLI
features it builds on are in 0.12.85:

```clojure
org.babashka/cli {:mvn/version "0.12.85"}
```

## Closing remarks

I hope you'll enjoy these new additions to bb tasks!
The new task keys should be considered experimental and may change in a future
version of babashka, depending on feedback from the community.
