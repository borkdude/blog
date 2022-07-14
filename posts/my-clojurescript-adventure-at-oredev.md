{:title "My ClojureScript adventure at Øredev", :date "2014-11-16", :categories #{"clojure" "om" "reagent" "react" "clojurescript"}, :legacy true}

## tl;dr

November 6th 2014 I was given the chance to speak about ClojureScript and React at the Swedish developer conference [Øredev](http://oredev.org/2014/speakers/michiel-borkent). You will find the videos and slides of my talks below. The talk about ClojureScript and React seems to be more popular and it has better sound quality, so you may want to skip straight to that one.

<img align="center" src="/assets/oredev-2014/michiel_clojurescript_ftw.jpg">

## How I got there

On October 7th I received an e-mail from the organization of Øredev. One of their speakers, [Anna Pawlicka](https://twitter.com/annapawlicka), who was going to speak about ClojureScript had cancelled (for urgent private reasons). She had passed them some names of people they could ask to replace her. Apparently I was on this list and received an e-mail. After some consideration I accepted this invitation. It would be my first appearance on a bigger (> 1000 participants) developer conference. So in the month leading up to this conference I was pretty nervous and excited. Luckily I could practice my preliminary talks at two local meetups and had lots of people providing me with useful feedback.

## The talks

The conference expected two talks of both 40 minutes. 40 minutes is a short time, so I had to select my slides and the level of detail very carefully.

### ClojureScript for the web

My first talk was about ClojureScript for web applications in general. I explained the most important features of Clojure(Script). Unfortunately the sound volume of the video is a bit low.

The description of the talk for the conference was:

Over the last few years we have seen the rise of browser
applications. Instead of rendering all UI server side, JavaScript
driven client applications are now being widely adopted. While
JavaScript is a flexible and powerful language, it has its
shortcomings. This is where languages that compile to
JavaScript step in. ClojureScript is one of them and offers its own
powerful features to the front end developer. In this talk you will
get an overview of what ClojureScript development looks like and how
it may simplify your application.

Video:

<iframe src="//player.vimeo.com/video/111214648" width="600" height="360" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe> <p><a href="http://vimeo.com/111214648">CLOJURESCRIPT FOR THE WEB</a> from <a href="http://vimeo.com/user4280938">&Oslash;redev Conference</a> on <a href="https://vimeo.com">Vimeo</a>.</p>

The slides can be downloaded [here](http://michielborkent.nl/oredev14/ClojureScript_for_the_web.pdf). Related code is [here](https://github.com/borkdude/oredev2014).

### ClojureScript interfaces to React

The second talk was about using ClojureScript in combination with the
React library. I showed two approaches: Om and Reagent. I have spent
more time and detail on Reagent, because this library is easier to
explain in a 40 minute talk.

The description of the talk for the conference:

React is a JavaScript library for creating declarative UIs. It was
created by Facebook to simplify writing applications consisting of
many components. React allows you to describe how the UI should look and renders
it automatically via one way data binding. It achieves good
performance by using a virtual DOM that prevents unnecessary and
expensive DOM manipulations. Even better performance can be achieved
by leveraging the immutable data structures of ClojureScript. This is
an approach taken by Om and Reagent. In this talk you will get an
impression of using ClojureScript together with React in practice.

Video:

<iframe src="//player.vimeo.com/video/111289716" width="600" height="360" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe> <p><a href="http://vimeo.com/111289716">CLOJURESCRIPT INTERFACES TO REACT</a> from <a href="http://vimeo.com/user4280938">&Oslash;redev Conference</a> on <a href="https://vimeo.com">Vimeo</a>.</p>

The slides can be downloaded [here](http://michielborkent.nl/oredev14/ClojureScript_interfaces_to_React.pdf). Related code is [here](https://github.com/borkdude/oredev2014).

## The conference

Øredev is very well organized. I didn't have to worry about a thing:
plane tickets, hotel, vegan food, quality coffee, ice breaking social
events: all arranged by them. If you ever have the chance to speak at
Øredev: I can highly recommend it!

<img align="center" src="/assets/oredev-2014/badge.jpg" width="50%">

<img align="center" src="/assets/oredev-2014/vegan-food-oredev.jpg" width="50%">

I wasn't the only person doing Clojure-related
talks. [Ryan Neufield](https://twitter.com/rkneufeld), the main author
of the Clojure Cookbook and Pedestal contributor was there talking
about Datomic and Pedestal. Rob Ashton shared his lessons learned
while creating a database with Clojure. Neal Ford talked on functional
thinking in Java, Scala and Clojure. Here's a picture of Ryan
presenting about Pedestal:

<img align="center" src="/assets/oredev-2014/ryan-pedestal.jpg" width="50%">

## Thanks

During these 40 minute talks I didn't have the time to thank people
who helped me during my month of preparation in one way or
another. Here is a list of people and companies I would like to thank
for their help or support:

- Anders Janmyr
- [Anna Pawlicka](http://www.twitter.com/annapawlicka)
- [Barbara Borkent](http://www.twitter.com/lalage_)
- [David Nolen](http://www.twitter.com/swannodette)
- Denis Fuenzalida
- Emily Holweck
- [Finalist](http://www.finalist.nl) (company)
- [Jayway](http://www.jayway.com) (company)
- [Martin van Amersfoorth](http://www.twitter.com/mamersfo)
- Matthijs Steen
- Ustun Ozgur
- [Vijay Kiran](http://www.twitter.com/vijaykiran)

## Resources

Lastly, for what it's worth, here is a raw list of resources that I found
interesting to study while I was preparing my talks. Have fun with those!

- [Clojurescript Up and Running](http://shop.oreilly.com/product/0636920025139.do)
- http://www.infoq.com/news/2014/01/om-react
- http://www.lexicallyscoped.com/2013/12/25/slice-of-reactjs-and-cljs.html
- http://adamsolove.com/js/clojure/2014/01/06/om-experience-report.html
- http://www.joshlehman.me/rewriting-the-react-tutorial-in-om/
- https://t.co/bzQcj0OsPW - PureScript
- http://2013.jsconf.eu/speakers/pete-hunt-react-rethinking-best-practices.html
- https://www.youtube.com/watch?v=SiFwRtCnxv4 - Nolen on immutability
- https://www.youtube.com/watch?v=-DX3vJiqxm4 - Secrets of the Virtual DOM
- http://www.funnyant.com/reactjs-what-is-it/
- https://twitter.com/swannodette/status/407750614727524352 - historic tweet of David Nolen porting React code to Cljs
- http://spootnik.org/entries/2014/10/26_from-angularjs-to-om-a-walk-through.html - from Angular to Om
- http://www.infoq.com/presentations/ClojureScript-Javelin
- https://github.com/enaqx/awesome-react
- http://stackoverflow.com/questions/21109361/why-is-reacts-concept-of-virtual-dom-said-to-be-more-performant-than-dirty-mode
- https://github.com/swannodette/om/blob/master/src/om/core.cljs#L250
- https://www.youtube.com/watch?v=noiGVQoyYHw#t=72 - Clojure: 10 big ideas
- https://keminglabs.com/blog/cljs-app-designs/ - A sampler of ClojureScript application designs
- http://teropa.info/blog/2013/10/18/single-page-webapps-in-clojurescript-with-pedestal.html - SPA in Pedestal
- https://groups.google.com/d/topic/pedestal-users/jODwmJUIUcg/discussion - Why Pedestal App is (probably) discontinued
- http://blog.cognitect.com/blog/2014/10/24/analysis-of-the-state-of-clojure-and-clojurescript-survey-2014 - Clojure 2014 survey
