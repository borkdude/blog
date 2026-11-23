Title: Thanks for giving!
Date: 2026-11-25
Tags: sponsors
Description: Thanks for giving!

<!--
Guide:
- Prose outside comments comes from the 2024 and 2025 posts and the 2026 OSS updates. Edit freely.
- Highlights: pick two or three items from the milestones list that sponsors felt most. Cut the rest or add one sentence on why it mattered to you.
- Plans: look back at the 2025 list. Say which ideas landed (CIDER middleware, completions) and which carry over. Keep each bullet short, as in 2025.
- Copy the plans into the Clojurists Together application or link to it once the vote page is up.
- Replace the picture placeholder near the end.
-->

Dear sponsors,

As we approach Thanksgiving once again, I'm reminded that sustained open source software development, supported by long term sponsors, is not something to take for granted.

I'm genuinely grateful for your ongoing support through GitHub Sponsors. Your contributions make a real difference: my Clojure projects wouldn't be nearly as polished, maintained, or ambitious without your help.

If you'd like to look back on what happened in open source this past year, you can find an overview here: https://blog.michielborkent.nl/tags/oss-updates.html. The core projects remain clj-kondo, babashka, SCI, scittle, and squint/cherry.

## Highlights of 2026

<!-- One or two sentences on how 2026 felt overall. -->

Three years after the initial installment, [Babashka Conf 2026](https://babashka.org/conf/) happened on May 8th at the OBA Oosterdok library in Amsterdam, with David Nolen, primary maintainer of ClojureScript, as our keynote speaker. You can watch all the videos [here](https://www.youtube.com/watch?v=c5RTAtodh3M&list=PLaN-rC-CjQqCClhXmwzE7XhGWbrbB-A7u).

<img src="assets/babashka-conf-2026.jpg" width="70%" align="center" alt="Babashka Conf 2026 speakers and organizers">

Another highlight was my visit to the [Clojure Conj](https://2026.clojure-conj.org) together with Rahul Dé, where we organized a Babashka workshop. It was great seeing lots of familiar and new faces over there. Rahul and I will give this workshop at [EuroClojure 2027](https://2027.euroclojure.org/) as well.

<img src="assets/conj-2026-workshop.jpg" width="70%" align="center" alt="Babashka workshop at Clojure/conj 2026, seen from the back of the room">

To give people at the workshop the best experience possible, lots of improvements went into babashka this year:

- Automatic command line help and completions in babashka tasks via [babashka.cli](https://github.com/babashka/cli)
- Resolving dependencies without a JVM (by vendoring tools.deps and rewriting the maven part)
- [Babashka FFI](https://github.com/babashka/ffi): a foreign function library to call C-ABI functions from libraries
- Better nREPL support: support popular nREPL middleware like CIDER's test middleware, the CIDER inspector, etc
- TUI support via JLine

<!-- Highlights outside babashka: clj-kondo, squint, cherry, scittle, reagami? Your Func Prog Sweden talk about Reagami: https://www.youtube.com/watch?v=X0PowSdliXs -->

## Plans for 2027

I've also applied for Clojurists Together again for 2027. If you're a CT sponsor, a vote in the next long-term funding round would be appreciated.

Here are the main ideas I want to explore in 2027:

<!--
- Which 2025 ideas carry over? Not yet done as far as the OSS posts show:
  - Clj-kondo: run macros directly from source code
  - Clj-kondo: run exported configs/hooks directly from classpath
  - Clj-kondo: first-class support for ClojureDart and Jank
  - Clj-kondo: performance improvements for bigger projects
  - Squint/Cherry: browser nREPL support, source maps
  - Scittle2 (working name)
  - Babashka: distinguished parallel task output
  - Clojure CLI: tools working group
- What is new for 2027? Babashka FFI and TUI follow-ups, EuroClojure 2027 workshop, Babashka Conf 2027?
-->

I can't make any promises on hard deadlines, but I definitely intend to work toward realizing the above goals.

<!-- Anything outside software development? Babashka Conf 2027, EuroClojure 2027 workshop. -->

As always, feel free to reach out anytime, whether here, on Clojurians Slack, or by email at michielborkent@gmail.com. I love hearing about what you are doing with my projects. Also if you are struggling with something, let me know. Your feedback and use cases continue to shape the direction of my work.

Here's to another strong year of Clojure OSS!

Thank you for making this journey possible.

With appreciation,

Michiel Borkent / @borkdude

<!-- TODO: replace with the 2026 thank-you picture, e.g. assets/thanks-2026.png -->
<img src="assets/thanks-2026.png" width="50%">

PS: if you aren't sponsoring, but are interested, here are the main ways to do
so:

<details>
<summary>Sponsor info</summary>

- [Github Sponsors](https://github.com/sponsors/borkdude)
- The [Babashka](https://opencollective.com/babashka) or [Clj-kondo](https://opencollective.com/clj-kondo) OpenCollective
- [Ko-fi](https://ko-fi.com/borkdude)
- [Patreon](https://www.patreon.com/borkdude)
- [Clojurists Together](https://www.clojuriststogether.org/)
</details>
