One of the recurring themes in Clojure land is:

- There is a popular widely used platform (JVM, browser, .NET, Node.js, ...).
- We may not like the language that comes with that platform.
- Adding Clojure on top of that platform might make the platform more tolerable and fun to use.

Enter PHP. PHP isn't a platform / language I like to use, but it sure is
practical and widely adopted. I already have a PHP server running, so I thought,
let's try some Clojure on top of this in the form of
[babashka](https://babashka.org/). Babashka already has a built-in http server,
but using this approach it isn't used. We just rely on PHP calling babashka when
the PHP script is requested via nginx. The babashka script prints HTML and then
exits. PHP sends the HTML to the browser as it would normally do. If you have
already paid the operational costs of installing and maintaining PHP, this
approach doesn't add much cost to it. A closely related CGI-based approach, but
one that doesn't use PHP at all, has been explored before by Eccentric-J and you
can read his blog post
[here](https://eccentric-j.com/blog/clojure-like-its-php.html).

The example described in this blog post is a guestbook.  You can view the
guestbook [here](https://cgi.michielborkent.nl/guestbook.php) and the code is on
[Github](https://github.com/borkdude/bb-php-guestbook). Below is a brief
explanation of the components involved.

## Database table

I created the following database table to store a name, greeting and some metadata:

``` sql
CREATE TABLE public.guestbook
(
name text,
message text,
_created timestamp without time zone,
_session text,
CONSTRAINT session_unique UNIQUE (session)
)
```

## PHP Babashka wrapper

This small PHP script forwards some of the request data to babashka:

``` php
<?php
session_start();
$post_data=escapeshellarg(json_encode($_POST));
$query_params=escapeshellarg(json_encode($_GET));
$sess_id=session_id();
passthru("POST_DATA=$post_data QUERY_PARAMS=$query_params SESSION_ID=$sess_id ./bb guestbook.clj");
?>
```

The `passthru` command calls a locally installed version of babashka, in the
same directory as the PHP script and adds some environment variables. It encodes
posted data and query parameters as JSON, which in the babashka script we will
decode as JSON:

``` clojure
(def post-data (-> (System/getenv "POST_DATA")
                   (cheshire/parse-string
                    true)))
```

## Babashka guestbook script

In the babashka script, I use the babashka [PostgreSQL pod](https://github.com/babashka/babashka-sql-pods) to interact with the database:

``` clojure
(require '[babashka.pods :as pods])
(pods/load-pod "./pod-babashka-postgresql")
(require '[pod.babashka.postgresql :as sql])
```

Note that I also downloaded the pod locally into the directory. I needed to do
this because the PHP server runs under a different user which doesn't have a
home directory, so installing from the [pod
registry](https://github.com/babashka/pod-registry) didn't work for that reason.

Here I check if a user already posted a greeting before, using the session id:

``` clojure
(def session-id (System/getenv "SESSION_ID"))

(def db {:dbtype "postgresql"
         :user "guestbook"
         :password "guestbook"
         :database "guestbook"
         :port 5434})

(def posted-before
  (-> (sql/execute-one! db ["select count(*) from guestbook where _session = ?" session-id])
      :count))
```

The script uses hiccup to render HTML. E.g here is the code for the guestbook:

``` clojure
(defn render-messages []
  [:table.table
   [:thead
    [:tr
     [:th "Name"]
     [:th "Greeting"]]]
   [:tbody
    (for [{:guestbook/keys [name message]} entries]
      [:tr
       [:td name]
       [:td message]])]])
```

The guestbook form has some fields to protect against spambots: the user is
supposed to fill in the outcome of a sum or multiplication. Also it is required
to post the data using the same session id that you got when you entered the
data. You can see that in the function `process-post-data`
[here](https://github.com/borkdude/bb-php-guestbook/blob/main/guestbook.clj#L26).

The babashka script can print at any time and the output is rendered via the PHP
wrapper as HTML. This is pretty handy for debugging. I wrote the script remotely
on a server and just refreshed my browser any time I added some debug
information, old school style.

One downside of this approach is whenever an exception happens you might not see
any useful output in the page, so some defensive programming using `try/catch`
is sometimes necessary.

So here you have it, documented for any PHP + Clojure user who might find it
useful.
