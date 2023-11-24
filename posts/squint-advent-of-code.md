Title: Playing Advent of Code with Squint
Date: 2023-11-24
Tags: clojure, squint

In the [previous
post](https://blog.michielborkent.nl/squint-cloudflare-bun.html) I described how
I built a Cloudflare worker with
[squint](https://github.com/squint-cljs/squint).  This worker is part of my
attempt to build a playground for squint in which you can play [Advent of
Code](https://adventofcode.com/). Advent of Code is a series of programming
puzzles published each day through December 1-25. By exercising puzzles
solutions on squint, I'm able to detect missing or incompatible features
compared to ClojureScript.

## Playground

The Advent of Code playground can be loaded [here](https://squint-cljs.github.io/squint/examples/aoc/index.html).

This link redirects you to the most recent version and I'll be updating the
redirected-to link if necessary.

## Puzzle input

To play Advent of Code, you need to download your puzzle input:

``` clojure
(def input (str/trim (js-await (fetch-input 2017 1))))
```

The `fetch-input` function is pre-defined in this
[boilerplate](https://gist.github.com/borkdude/cf94b492d948f7f418aa81ba54f428ff)
gist and is loaded via the `boilerplate` query parameter.  Advent of Code input
is personalized and based on your Advent of Code account. This is why you are
asked to fill in your Advent of Code token in the top of the UI. You can obtain
this token by visiting [Advent of Code](https://adventofcode.com/),
registering + logging in and then find the `session` cookie in the developer
console. Copy paste this value into the UI and hit `Save!`. After doing so, the
session token is saved in local storage for next puzzles you might want to
solve. The session token is only saved in your browser. The Cloudflare worker
doesn't store anything and only serves as a proxy.

## REPL-mode

The playground has two ways of compiling Squint Clojurescript to JS: the normal
ES6 mode and REPL-mode. In REPL-mode you're able to incrementally evaluate
squint snippets. This works by compiling vars in keys of global objects, much
like CLJS does it. E.g.:

``` clojure
(defn foo [])
```

is compiled to

``` clojure
var squint_core = await import('squint-cljs/core.js');
globalThis.user = globalThis.user || {};
globalThis.user.foo = (function () {
return null;
});
var foo = globalThis.user.foo;
```

in REPL-mode. This output certainly isn't optimal for JS bundlers like `esbuild`
so it's only intended for development.

By hitting "Compile" the whole editor is compiled + evaluated. The compiled
JavaScript is visible in the right output pane.  When in REPL-mode, hitting
Cmd-Enter (or Windows-Enter) with the cursor after a form will compile only that
expression.  Use the `comment` form to evaluate sub-expressions while working
towards a complete solution.

While working on your puzzle, the state of the editor is saved to local storage,
so if you accidentally close the browser, the input re-appear next time you
visit the playground.

Let me know if you're enjoying this and feel free to post issues in the
`#squint` channel on Clojurians Slack!
