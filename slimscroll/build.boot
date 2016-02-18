(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.0" :scope "test"]
                  [cljsjs/jquery "1.11.3-0"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "1.3.7")
(def +version+ (str +lib-version+ "-0"))

(task-options!
  push {:ensure-clean false}
  pom  {:project     'cljsjs/slimscroll
        :version     +version+
        :description "A small jQuery plugin that transforms any div into a scrollable area with a nice scrollbar"
        :url         "https://github.com/rochal/jQuery-slimScroll"
        :license     {"MIT" "http://opensource.org/licenses/MIT"}
        :scm         {:url "https://github.com/cljsjs/packages"}})

(comment (require '[boot.core :as c]
                  '[boot.tmpdir :as tmpd]
                  '[clojure.java.io :as io]
                  '[clojure.string :as string]))

(deftask package []
  (comp
   (download :url (format "https://github.com/rochal/jQuery-slimScroll/archive/v%s.zip" +lib-version+)

              :unzip true)
   (sift :move {#"^.*slim[sS]croll-(.*)/jquery\.slimscroll\.js" "cljsjs/slimscroll/common/slimscroll.inc.js"
                #"^.*slim[sS]croll-(.*)/jquery\.slimscroll\.min\.js"  "cljsjs/slimscroll/common/slimscroll.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.slimscroll"
               :requires ["cljsjs.jquery"])))
