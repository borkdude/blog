Title: Thanksgiving OSS sponsor appreciation post!
Date: 2026-11-23
Tags: sponsors
Description: Thanksgiving OSS sponsor appreciation post!

Dear sponsors,

As we approach Thanksgiving again, it's time for me to reflect on the past year
and in particular on my activities in Clojure open source. I'm very grateful
that my main projects babashka, clj-kondo, SCI, squint
and all other related projects are thriving so far thanks to your ongoing support through GitHub Sponsors and Clojurists Together.
Your contributions make a real difference: my Clojure projects wouldn't be nearly as polished, maintained, or ambitious without your help.
I will **apply for Clojurists Together long-term funding for 2027**. If you're a CT sponsor, a vote in the next long-term funding round would be really appreciated.

## Highlights of 2026

2026 felt like a really productive year to me. On the 1st of January I started hacking on **async/await support in ClojureScript**. Since Squint supported this feature, I had a vision of how it could work in ClojureScript. It turned out to be manageable and I got 95% working within a day. I know that a lot of people can say this in 2026 with Claude helping them to code, but I assure you no LLMs were used (or hurt) in this process. Of course it took some back and forth with maintainer David Nolen, but the **patch was merged** March 6th of 2026. A new release came out on May 7th 2026. It felt great to contribute something significant back to ClojureScript that benefits all of its users in the ecosystem and not just squint users.

Three years ago the first Babashka Conf took place. This was mainly organized by volunteers other than myself and was hosted at Pitch. I did the keynote that year. This year I decided to organize **[Babashka Conf 2026](https://babashka.org/conf/)** myself, which took place on May 8th at the OBA Oosterdok library in Amsterdam. What prompted me to organize this was that the Dutch ClojureDays were resurrected after four years. Since many people would come over anyway, an extra conference the day before would align well with people's traveling plans. David Nolen, who came over all the way from New York, was our keynote speaker. You can **watch all the videos [here](https://www.youtube.com/watch?v=c5RTAtodh3M&list=PLaN-rC-CjQqCClhXmwzE7XhGWbrbB-A7u)**. The conference was only possible thanks to the kind companies and persons who decided to sponsor this event: Nubank, Exoscale, Flexiana, Bob and Itonomi. The day after, at Dutch ClojureDays, I presented the async/await support in ClojureScript, so it was quite a busy weekend.

<img src="assets/babashka-conf-2026.jpg" width="70%" align="center" alt="Babashka Conf 2026 speakers and organizers">

Another highlight this year was my visit to the [Clojure Conj](https://2026.clojure-conj.org) together with Rahul Dé, where we organized a **Babashka workshop**. It was great seeing lots of familiar and new faces over there. Rahul and I will give this workshop at **[EuroClojure 2027](https://2027.euroclojure.org/)** as well. Make sure to be there!

<img src="assets/conj-2026-workshop.jpg" width="70%" align="center" alt="Babashka workshop at Clojure/conj 2026, seen from the back of the room">

To give people at the workshop the best experience possible, lots of improvements went into babashka prior to the conference... conference-driven development!

- Automatic command line help and completions in babashka tasks via [babashka.cli](https://github.com/babashka/cli)
- Resolving dependencies without a JVM (by vendoring tools.deps and rewriting the maven part)
- [Babashka FFI](https://github.com/babashka/ffi): a foreign function library to call C-ABI functions from libraries
- Better nREPL support: popular nREPL middleware like CIDER's test middleware, the CIDER inspector, etc.
- TUI support via JLine

Rich Hickey told me in person that he considers **babashka an official Clojure dialect**, alongside ClojureScript, ClojureCLR, ClojureDart and Jank. That really means a lot to me. I'm allowed to use "Clojure" in the name if I want, but at this point, changing the name of babashka would probably lead to confusion? Let me know what you think.

<img src="assets/dialects.webp" width="733" style="max-width: 100%" alt="Alex Miller on Clojurians Slack: the official dialects from Rich's perspective are Clojure, ClojureScript, ClojureCLR, ClojureDart, Babashka, and Jank">

I could name a lot of other highlights: improvements to clj-kondo, SCI, squint,
and related projects of the borkiverse. But that would make this blog post too
long. You can find highlights in more detail in my OSS updates here:
https://blog.michielborkent.nl/tags/oss-updates.html. A few things that stand out to me are:

- support for source maps in squint
- nREPL support for squint
- JIT-compilation in SCI when running in JavaScript
- running macros directly from source in clj-kondo

## Plans for 2027

As I said, I will apply for Clojurists Together long-term funding for 2027. If you're a CT sponsor, a vote in the next long-term funding round would be really appreciated.

Here are the main items I want to focus on in 2027.

- Better support for Clojure dialects in clj-kondo, starting with ClojureDart and Jank
- Clj-kondo project configuration (rather than doing everything via command line arguments)
- Clj-kondo: run exported config/hooks directly from a classpath (depends on the previous point)
- Clj-kondo: improvements to the type system
- Babashka: remaining work on babashka tasks (e.g. colored output for parallel tasks)
- Babashka: include (parts of) bbin (package manager for bb scripts/projects) in bb itself
- Babashka: better support for self-contained binaries
- Babashka: compatibility with more Clojure libraries
- Babashka: more libraries that leverage [babashka.ffi](https://github.com/babashka/ffi)
- EuroClojure talk and workshop
- Another Babashka Conf...?
- Attend meetings of the Clojure CLI working group and provide feedback
- Scittle2: Scittle but based on squint or cherry
- Overall: performance improvements and bugfixes of all my projects

I can't make any promises on hard deadlines, but I definitely intend to work toward realizing the above goals.

## Wrapping up

As always, feel free to reach out anytime, whether here, on Clojurians Slack, or by email at michielborkent@gmail.com. I love hearing about what you are doing with my projects. Also if you are struggling with something, let me know. Your feedback and use cases continue to shape the direction of my work.

Here's to another strong year of Clojure OSS!

Thank you for making this journey possible.

With appreciation,

Michiel Borkent / @borkdude

PS: if you aren't sponsoring, but are interested, here are the main ways to do
so:

<details>
<summary>Sponsor info</summary>

- [GitHub Sponsors](https://github.com/sponsors/borkdude)
- The [Babashka](https://opencollective.com/babashka) or [Clj-kondo](https://opencollective.com/clj-kondo) OpenCollective
- [Ko-fi](https://ko-fi.com/borkdude)
- [Clojurists Together](https://www.clojuriststogether.org/)
</details>
