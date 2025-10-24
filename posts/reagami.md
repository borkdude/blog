Title: Reagami: ultra-small (<4kb) reactive apps using Squint and hiccup
Date: 2025-10-24
Tags: clojure, squint, clojurescript, reagent

Recently I've been excited by [Eucalypt](https://github.com/chr15m/eucalypt), a
Reagent-clone in pure [Squint](https://github.com/squint-cljs/squint).  For
those who don't know Squint, it is a CLJS dialect that re-uses JS features as
much as possible. Maps and vectors compile directly to objects and arrays for
example. The standard library mimics that of Clojure(Script) and collections,
although they are mutable in JS, are treated immutably via shallow-copying.  You
can build small apps in Eucalypt starting at about 9kb gzip. When looking at the
code, I wondered if we could make an ultra-simple Reagent-like library with less
features for even smaller apps. If I understand Eucalypt correctly, it produces
DOM nodes from hiccup and then patches the already mounted DOM nodes using a
diffing algorithm. I tried to do this in under 100 lines, in a more basic
fashion.  I call this library [Reagami](https://github.com/borkdude/reagami), a
library you can use to fold your state into the DOM!

Let's explain how it works.

## Rendering Hiccup to DOM nodes

Below you see the `create-node` function, which takes hiccup, e.g. `[:div
"Hello"]` and transforms it into a DOM node.
I placed comments in the code that help you understand it, hopefully.

``` clojure
(defn- create-node
  ([hiccup] (create-node hiccup false))
  ([hiccup in-svg?]
   (cond
;; we compile primitives directly into TexNodes
     (or (nil? hiccup)
         (string? hiccup)
         (number? hiccup)
         (boolean? hiccup))
     (js/document.createTextNode (str hiccup))
;; vectors indicate a DOM node literal or a function call
     (vector? hiccup)
     (let [[tag & children] hiccup
;; here we pull apart id and class shortcuts like: `:div#my-id.my-class`
           [tag id class] (if (string? tag) (parse-tag tag) [tag])
           classes (when class (.split class "."))
;; hiccup vectors can have an optional map to set attributes of the DOM node
          [attrs children] (if (map? (first children))
                              [(first children) (rest children)]
                              [nil children])
;; SVG needs special handling, SVG nodes need to be created with `createElementNS`, see below
           in-svg? (or in-svg? (= :svg tag))
;; if we encounter a function as the first element, e.g. `[my-component]`, we call it
           node (if (fn? tag)
                  (let [res (apply tag (if attrs
                                         (cons attrs children)
                                         children))]
;; and then we feed the result back into create-node
                    (create-node res in-svg?))
;; so here we create the DOM element, either a normal or an SVG one
                  (let [node (if in-svg?
                               (js/document.createElementNS svg-ns tag)
                               (js/document.createElement tag))]
;; here we iterate over all the children in the hiccup vector
                    (doseq [child children]
;; in squint, vectors are also seqs, but here we want to make sure the result is list-like,
;; e.g. the result from `(for [i xs] [my-component])`
;; if that is the case, then we map create-node over those results
                      (let [child-nodes (if (and (seq? child)
                                                 (not (vector? child)))
                                          (mapv #(create-node % in-svg?) child)
;; else, we have only one node as the result of calling create-node on the child
                                          [(create-node child in-svg?)])]
;; let's add all the child-nodes to the DOM element created from the tag!
                        (doseq [child-node child-nodes]
                          (.appendChild node child-node))))
;; attribute handling: for each key and value in the attribute map:
                    (doseq [[k v] attrs]
                      (let [key-name k]
                        (cond
;; Let's create some CSS if the style attribute is a msp!
                          (and (= "style" key-name) (map? v))
                          (doseq [[k v] v]
                            (aset (.-style node) k v))
;; if the attribute starts with "on", then we add an event handler
                          (.startsWith key-name "on")
;; we support :on-click, :on-mouse-down, :onMouseDown, etc.
                          (let [event (-> (subs key-name 2)
                                          (.replaceAll "-" "")
                                          (.toLowerCase))]
                            (.addEventListener node event v))
;; if the attribute false is false or nil, we ignore it
                          :else (when v
                                  (.setAttribute node key-name (str v))))))
;; classes parsed from the tag, e.g. `:div.my-class1.my-class2`
                    (let [class-list (.-classList node)]
                      (doseq [clazz classes]
                        (.add class-list clazz)))
;; finally we handle the id parsed from the tag, e.g. `:div#my-id`
                    (when id
                      (set! node -id id))
                    node))]
       node)
     :else
     (throw (do
              (js/console.error "Invalid hiccup:" hiccup)
              (js/Error. (str "Invalid hiccup: " hiccup)))))))
```

That's it! Pretty simple.

## Patching the DOM

If we simply just re-insert the generated DOM nodes from the hiccup, then we
have several problems. E.g. when we are typing in an input field and we insert a
new input field, we lose focus. Also we lose event handlers, although this isn't
really an issue if we re-generate them via the hiccup-to-DOM function above.
A better approach is to cleverly patch the DOM and re-use existing DOM nodes when possible.

The `patch` function takes a parent node, some child nodes and tries to re-use
or insert the children. There are several libraries that do this far more
intelligently than the below
function. E.g. [Idiomorph](https://github.com/bigskysoftware/idiomorph) is such
a library. One problem I encountered with Idiomorph is that it doesn't preserve
event listeners. There's also [Snabbdom](https://github.com/snabbdom/snabbdom),
a small but effective vDOM library that you could use. But where's the fun in
that, if you can make your own naive patch function. Here we go! When we render
a component for the first time, Reagami calls `patch` with the root node and the
rendered hiccup as the only child, usually something like `div#app` or
`div#container`. Read along with the comments again.

``` clojure
(defn- patch [parent new-children]
;; First we take a look at the current children of the root node, already mounted in the DOM
  (let [old-children (.-childNodes parent)]
;; If the amount of children isn't the same, we just give up and replace all the children with the new children.
;; This typically happens on first render or when elements disappear due to conditionals in the hiccup code.
;; More mature patching libraries do much more clever things here
;; but I've found this works well enough for the basic use cases I tested.
    (if (not= (count old-children) (count new-children))
      (parent.replaceChildren.apply parent new-children)
;; When the number of children is the same we compare them one by one.
      (doseq [[old new] (mapv vector old-children new-children)]
        (cond
          (and old new (= (.-nodeName old) (.-nodeName new)))
;; Only when the node names are the same, we consider it the same node.
;; If it's Text node, we need to copy over the text content.
          (if (= 3 (.-nodeType old))
            (let [txt (.-textContent new)]
              (set! (.-textContent old) txt))
            (let [new-attributes (.-attributes new)
                  old-attributes (.-attributes old)]
;; Here we just set all the attribues from the new node to the old node.
              (doseq [attr new-attributes]
                (.setAttribute old (.-name attr) (.-value attr)))
              (doseq [attr old-attributes]
;; When an existing attribute in the old node, doesn't occur in the new node, we remove it
                (when-not (.hasAttribute new (.-name attr))
                  (.removeAttribute old (.-name attr))))
;; Then we recursively descend into the children of the new node.
              (when-let [new-children (.-childNodes new)]
                (patch old new-children))))
;; If the node names aren't the same, we replace the node completely with the new node.
          :else (.replaceChild parent new old))))))
```

## Putting it all together

Reagemi's only public function is called `render` which is just:

``` clojure
(defn render [root hiccup]
  (let [new-node (create-node hiccup)]
    (patch root [new-node])))
```

With this single function, you can write a small reactive app, if we combine it
with `atom` and `add-watch`.  Here's an example of this:

``` clojure
(ns my-app
  (:require ["https://esm.sh/reagami@0.0.6" :refer [render]]) )

(def state (atom {:counter 0}))

(defn my-component []
  [:div
   [:div "Counted: " (:counter @state)]
   [:button {:on-click #(swap! state update :counter inc)}
    "Click me!"]])

(defn do-render []
  (render (js/document.querySelector "#app") [my-component]))

(add-watch state ::render (fn [_ _ _ _]
                            (do-render)))

(do-render)
```

That's it: a Reagent-like library in less than 100 lines of code!

Here are some examples you can play with on the Squint playground:

## Examples

Examples on the Squint playground:

- [Input field + counter](https://squint-cljs.github.io/squint/?src=gzip%3AH4sIAAAAAAAAE41TO2%2FbMBDe%2FSs%2BMyhADn5MHTg0AYqsXTIKRsGI54ipRDLkyYZh%2BL8X1KOCPVUcRB6%2Fx93pJH1GZ5xfAVIn%2BupdIlSiYY5Z73aUu21udonMh%2Bncy367334X0CZjCh2UWq2kpSMyGyZIw6HDVdeh90wJe%2BjchDM49XSbsR69Q3VYAZW27rRC2bz3zMHjqoPf1K2r%2F%2BBJ5rOJ60m5j7a8RjkfWN0KD%2BKtCednCMjx5mVAqyIOeW7IP1wMpNEXV5350tKQbhsS9Eci8rdRuIBiIsiYNpnTnfBw6XzsGVd9Mm1PkNP5zmZ5SlUj4L4qk3OoMXHl5ge%2BQbNJH8QYhZW6LZb%2F26O5%2B87Xaq4G4ufA6GgtZkXpjpJO5J8hB8pjFWqpo9IR4vVEHgNi0Zj79NhMkciKf70s%2Fr8Co7itxUGVNQ1OCoHL%2BIUE%2BZl3NtR9R563Xz2lyxu1VHORezIxijEfaQOHe3CdyDC9tlROENadxJy7zMRrbJyFWCRwT38P9rKNiSJ5q9Qyp4m8pTTOqpxGfjcFS96o%2BukXMNZuzobrZvoUWo%2BwQjx6VL8xrEORKfHRZNr%2FBf022K2GAwAA)
- [Boring crud table](https://squint-cljs.github.io/squint/?src=gzip%3AH4sIAAAAAAAAE71WS28bNxC%2B61dMaBSggKwc9FJgAyQu2lx7aI5b1qDEkcR6l1yTs3IEwf%2B94GOftpz0UgkS9jHzceabb4bkxkMjtVkB8NLhY6cdQsWORK0vb2%2FRNxt%2FvHUoD7LRDErpId%2BI9Xq14gr3sLeuKbTRBJfSyAaBsRUsPiUqTdocPgO5Dp%2BzqzU7BE%2BSELgk28CllEY3svZweX6BkZHCcuOaz30YBqRSRXJ%2FB5UIGfkn2b7rF9gbqOKleInMaySoYvC8%2BJQ90koxpfWVYEAr4J4ccCeNsk3RdVqt16%2BsAMCl93ZXaJPhqyFXrURPXfwrtQKtntfh0yfnsLEnHPOTLxLsWhWj7lGVDusBD2hygpTs3kIaAh1DzCgiIPVAe421KrRpOyqO0qga3QqA%2FYnUOeNBQn4IdJSUl%2FVwknWHoA3Egjeyfb8C6IxCBw94fg9Pmo7ZaO9sA9b8dpTmgIAnNBSUVUXPBzzHwENZc0lzEuF1LkDMJJjme77ZAEJB0h2QoIjLTFlOyZxkrdVnqKLIBslwj4%2FAU5nGNxPnIHG5rTFRAlXirtAqeus98ANSoPVuKYDBcuwTkSRXlQnsUno61wiXcmtd4Ir93H4Db2utYFvL3QNb9EtJ5xaBEX6jRTeWidwfCSbkKhbaL4eC3HxPNEuk2Fs%2Fwabnf9MX4FnkZNv%2FEJaYdH565%2BxTL%2BfYz%2Bnxdche1GOGw5Ti48BK1us%2BRHLJuipJQbWoeY8opjbltiOyBj5%2B3GzJhF%2FROt1Id960XV0XTh%2BONCX5UirtA6oCbiwBn8kydOCyILXePaRWeHX0ROk%2FHdEMCV6zAuCL%2BZBVfhX25Qh6dXJMlA3G0no902vojoFw9lWekAH7ojSxq1xCJHMkMPKqgjDdnMuenhu%2BmKG5sLNA2F%2Fdhw%2B%2F%2F8KEENPZ65GK0POvbSxpxiy2pRfajK%2Bjcy%2BhkND%2F3ePjjvZKW2cNX23rifMbnSxWy2L9kOrf1jxPi98NI3ce%2BO77%2BueT08E1MfNJoWcCZb8qxcbMhFhU1%2FeVVfqUjUIqm8l%2F4cnpFvuK0xGlGoTdKzY8B%2FaHbJCJfDO5EMMI2lqVdzTeyDYlXt2DFPPs%2F76UD3hOBxQ2zsiCDYeCuaCqyRyVo0D40Mt3s2FQTaQtxIQUZy0VO9u01qChBTc391XZOgTeusBJjylgNUL6ASxihY6zDvg%2F%2FlbZXdegoc1jh%2B78FWvckXXAbmTbshQXV5bs3HjnUBJ%2BqTHcAVP6xPrsuEd6B2FbYSMEzN0D3ZvWYYtGzc9k8diSRkI%2BFt%2FmhyFuqOZMpJEShPgkaXfsj5llcukPM%2FcQvyJAhudpwXz9L76X4DewCwAA)
- [Snake game](https://squint-cljs.github.io/squint/?src=gzip%3AH4sIAAAAAAAAE41WW4%2BjNhR%2Bz684y6iS2YoJTLsvZGenlbqqql2pUi9PiFYOPiSeGJu1HRI6yn%2BvbJNAZpnVECngw%2BfP534g0kBDuVwAkFzjlz3XCEW0tbY1%2BXKJprk126VGuqENjyCnBoZFGceLBWFYg%2BH%2FIdylMaxWsNGcBQG5S493aRwgFQoBWYAgrbZBcA9Z2h4Di5IVgrHUolOFWtXAU24k3SEUxTvI0hKKH8PtB3crYQVccsupgABbK9Yv4HzljGsoMkgdsFEdlxvQfLO1E0itFIMiC%2BwjnRNPUFTwDh%2FA6j2ezjZL0FQy1SSBolwAFMSJEi6ttz%2BGZ%2BvysrVRHSaDaW4nMQfavrkYD0BqCcVTvsPeQBGAzhp%2FVtCm9JEw9lQOihJeA5HKDu%2Fji%2F5mNJgItFBskTIgHVZQkO%2BBSLsFUnNtbHBjDGkcpO7INB6Z5q95jmzCkcXO9pmt1OIDkHvwGjnjZlESD4OzvNKkUtKELc5mzzG8Xu%2BtoBcd4nnVj0ExT5DOIvoJIptFbLlNDlSIByBKA3kPR%2B%2B099C%2FQHnlsA%2F3cDynyId76MPziwcZFPUDEKMahJuzt75z6YXGjt6J4%2FI5g3MV%2B5p2oj01RlVg7CXJayoMzqgyUeTVe0J4R3iI0hjOUH5kUkhzTshRGPwGTRyuxWK1gr94gxqEUu2CPJqlQfubtKg7KqY1d5emAf4J%2B7WimkGlpNVKmAW5pYx97FDaz9xYlKjh0SwPXDJ1gGiHPVMHGS2GCsXg8VBWO%2ByB3CbuhpdQTCsb9i1zN9eYXs4RT8y4%2FiqY14GlBmGH%2FbdzLfpZa3X4u41Cd7j31VikkJXx%2BTHJ5kvzOckvzu5nNMmE55U0n7G21zSuQQ8siX9%2BDc0fro9f8yQTolfwMK7PeRN6sptHicbKQlEcoS%2BhUkL5KBS5Fz%2FlRyBv4eiRMeS9W%2FVhNTkrP3Bmh%2FGWb9Ep6hdTSM2FCPyncShsaINJSMcwFHxWXQ%2BB6QA4wU8%2BrUKiFDnjXTijyE23gadBEfI2TONB60GjK%2BmVp3Jje4HwlK%2BVZqghytojGCU4g7Wg1S6a9Wu%2BptVuo9VeMohuEOvodBqAq1Xox%2BeKqJWGoqV6aNNjnv%2FjbQVirAb3Pj5BMUbF74g2GlFGl%2BCuVtNZPUF7R0UaWVSOUOdhUB1q%2FyfouXrIYYtyfngWucVjiP271MU8S1PXt6RNho%2BeIZpRcM4Jol%2FdKb93qN9EZVyOH0laKTeJ%2FcB4NEumqn2D0t5%2B2aPu%2F0SBlVUaohvatlFQgDBl1TW40kgtfhToVhAx3kXxpdWgfQMJZxCNFHC93X0h3bYaW5RsmvwapYt1yLvh8245CJ3eUIzZGSyijCUHaqvt0NvyPMDPrfFf8L%2FS0Tl5OGx4%2Fh8hQm1YdAoAAA%3D%3D)
- [Draggable button](https://squint-cljs.github.io/squint/?src=gzip%3AH4sIAAAAAAAAE5VWXY%2FjNBR9n19xCS%2BOtEm7EiDkIO0K2DfgAYQEiqqVG98k3nHtrO10Ekb978gfyfRr0dA%2BpI3P%2FfC5xychysKBCfUAQKjBz6MwCHXWOzdYutmgPZS23xhkHTuI99tyW36XAWUW0q1dnj88EI4t7CVrHoteSywGbeGZTvDNdgt09pfTgrKOOQTCnD4EyNsICRcmxRHfgTMjnnzaqoI%2FnZDCzdCOqnFCKxuyKGiktvgO6gnmnW%2BdKQ7kByC%2FMtdv2N4CKWACQqertvI8h2%2B3%2BQMA3OBnIHS%2Bj%2FfdpModuqKRApUrDDYOajy60IJEB7UBUnboftSj4kJ1PwXg7x5HysIx06EDPLo834UWnqnENqyFq8nfUKeHgNUDmHyh4cMRlYOeKS7RLBwc9GixOOgjFmkFat22FmM%2FrVp7W7qb%2FDZJmfr%2FK3QSSIpheeQlfuYL7N8Ldl6xuwQmogWS5jHB%2FJKDGLTovkojf17G2zJp8fRl2ATTWRs3HzrD%2FJ%2FrlyrKF30ufI3DGVsq0LfS9XHlq6rAoF8D12OMDP%2BksA4VmkhqGTFhOr%2BkFfhkN09Ccf0E2RqXQSp10w7XT%2BqlodjKJ7tptLJaYil1BzQgPdCTVlVegrBPCoMgQt2GPheBSTygcqson%2BkjzhbqoDGnh93pNSo9U0MceZjNlYBy8Enzm4HQG%2FnkvnJ%2BWpFVBRxboRBmPRoI7C48kFmPwKRBxmfo2RFBnNVIVAK5cwCSONchMs6%2FPD3G%2Bf8Z3U3OcVgzguuZS5Kxl5p5bbFxyJYtnWn0RTercLhhXcf2Eov96JxWUPvt1pSLo69V0%2F4tkMEU1hl4H05V5KOmEZ%2Bcx7pZ%2BvP2TyEUxwm%2BvxwiHbQV3nIhY3ur5egwu0JE7%2FJl7hgtZMOU5VBdxQR7iyE3XhtDrgL2rHnsjFcqZI2W2tAYVmWnKKbsN%2B2gR4MwSGQWs7BZ8tSjArK4QeIh5r4g4hVUvIaMGzpSxfs0XBNxAV7PCA3DT2f%2Fjl8swOxnwzo4YLbLd6tKjNauaPRh0Mo%2FO6JGrqWzwAPa%2B4U2wX64bkZvIeXnEc38B0psnDaQfc2GIU2IcO30JbgxyBx%2BiO4DGRfHdZokWHwhOGQvKeAyfK%2F5XA4GB1T8XO8GFffmGLwxvXZs0k3fN9SXe42vJIzz4om5pk8PFkpjyOr1EL47n9LfjwXT738B1bnPCBYJAAA%3D)
- [CSS transition](https://squint-cljs.github.io/squint/?src=gzip%3AH4sIAAAAAAAAE4VTuW7jMBDt9RUTpqGAlewU2YJVmgW2TykIAU2OLGZ5hRzZMYz8%2B0Ky4mNXSMRC5PDN45uL%2BwxOGl8AcJHwbTAJoWE9UcxitcLs6tyvEsqtdOZpXa%2FrnwyEzDCb2rIsCq6xg0ySELik4OAo%2BrDDBJ20GT8%2BIR424b1SwcXg0RM0bQHQCG12cBSZDhbhKPZGUw%2FsYb2O76yAfz%2FRo9n29AVgI9WfbQqD15UKNiTgpgM%2BC3qaVJbAEmoGbGMHZOUCCSXpsyETPLD%2FCNf1YwaUGX%2FAhOtCchfjkqYLbFFMVtIif6gfS3Y%2BlIu6tMnRygOwzuJi9NKara8MocvAFHrCtAR7HTKZ7lCp4GksxRfQU8xs3xtajE0NKY%2BAGMxE8XHBiOArF4aMEznc87yX8W7ulCHq8TfnwgcqFxwtyh1%2B6zg9yX5PZ4esPfdbCoEWG250aERMCDymKtO5GO10c9On7SffRDcOythUr3mlgxoceqrfBkyHZ7SoaMzEvYzxXD2uA4VbuEooCX9ZdFPqtdld1ZpnpDuojAZ2TQO3FJugD3VMGNHrsrwMWEKvMZ0C5fOIrmbjqB6a25ScxldqXe0lqX7OsBAnl%2Btq885D8wLTakfuEXB6ed7%2FBcFoSnxLBAAA)
