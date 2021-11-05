[Babashka](https://github.com/babashka/babashka) is a fast starting native
scripting tool for Clojure. Like in bash, it's common to call other programs,
also known as "shelling out".

## clojure.java.io

This is where `clojure.java.shell` got its name
from and this namespace is also available in babashka. Invoking its `sh`
function is probably the most well known way of shelling out in Clojure:

``` clojure
(require '[clojure.java.shell :refer [sh]])
(def res (sh "ls" "-la"))
```

The behavior of `sh` is that it will block and return stdout and stderr as
strings and the exit code a number:

``` clojure
(keys res) ;;=> (:exit :out :err)
(:exit res) ;;=> 0

(require '[clojure.string :as str])
(last (str/split-lines (:out res)))
;; => "drwxr-xr-x     7 borkdude  staff          224 Nov  4 11:02 zsh-config"
```

## TODO

- use clojure-mode's syntax highlighting
- published date should be derived from .md file's date if not given explicitly
- if preview, then insert some text saying that it's a preview, perhaps also a robot.txt saying that it should not index the preview
