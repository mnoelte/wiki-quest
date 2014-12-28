(ns wiki-quest.core
  (:require [error-codes.core :as ec]
            [me.raynes.laser :as l]
            [clojure.string :as str])
  )

;; Usage:
;;     1. start nrepl
;;     2. user> (in-ns 'wiki-quest.core)
;;        #<Namespace wiki-quest.core>
;;     3. in core.clj: C-c C-k
;;     4. 0 <RETURN>    ; wegen des *1  ; ich habe es nicht geschafft *1 als nil o.ä. abzufangen
;;        (wiki-quest3 (rand-word) *1)
;;        ODER: wiki-quest.core> (wiki-quest2)


(defn get-article [article]
  (first  ; only the first paragraph
   (l/select
          (l/parse (slurp (str "http://es.wikipedia.org/wiki/" article)))
          (l/element= :p))))

(defn extract-text [node]
  ; recursively walk through the nodes collecting strings
  (if (= (type node) java.lang.String)
    node
    (apply str (map extract-text (:content node)))))

(defn kill-word [w t]
  ; eliminate word w in text t
  (apply str (map (fn [w2]
                    (if (> (:distance (ec/edits w w2)) 1)
                      w2 "____"))
                  (map (partial apply str) (partition-by #(Character/isLetter %) t)))))

(defn wiki-quest [article]
  (kill-word article (extract-text (get-article article))))

(def lista-de-palabras (str/split (slurp "/home/mn/clojure/wiki-quest/resources/lista-de-palabras.txt") #"\") )

(defn wiki-quest2 [& args]
                                        ; select randomly a word "article" from a given list of words
                                        ; fetch the respective article from wikipedia.es and eliminate the word from the explanation

  (let [article (rand-nth lista-de-palabras)
                           article-text (extract-text (get-article article))]
                       (do (println (kill-word article article-text))
                           (dotimes [n 15] (println ""))
                           (read-line)
                           (println article)
                           (println article-text))))

(defn rand-word []
  (rand-int (count lista-de-palabras))
  )


(defn wiki-quest3 [word-number old-word-number]
  ;;         more functional version of:
  ;; select randomly a word "article" from a given list of words
  ;; fetch the respective article from wikipedia.es and eliminate the word from the explanation

  (let [article (nth lista-de-palabras word-number)
        old-article (nth lista-de-palabras (if (nil? old-word-number) 0 old-word-number))
        article-text (extract-text (get-article article))
        old-article-text (extract-text (get-article old-article))]
    (do (println old-article)
        (println old-article-text)
        (dotimes [n 15] (println ""))
        (println (kill-word article article-text))
        ))
  word-number)


  ;; (comment
  ;;   Mit "lein run" funktioniert (read-line) ... anschliessend in der nrepl aber nicht mehr.
  ;;   Ein "lein clean" (evt. noch "lein compile") schafft dann wieder abhilfe.
  ;;   Es scheint eine Anhaengigkeit bzw. ein Side-Effekt zwischen den
  ;;   Kompilaten fuer die nrepl und "lein run" zu geben.

  ;;   Experimte mit "lein uberjar" nach http://www.beaconhill.com/blog/?p=283
  ;;   ... hat aber nicht funktioniert: "Attempting to call unbound fn: #'wiki-quest.core/-main"
  ;;   Weiter mit ...
  ;;   ---------------------------------------------------------------)

  ;; (defn -main
  ;; "The application's main function"
  ;; [& args]
  ;; (println args))


  ;; Aufruf " mit Internetverbindung:"

  ;;                    wiki-quest.core> (kill-word "lago" (extract-text (get-article "lago")))
  ;;                    "Un XXX (del latín lacus) es un cuerpo de agua dulce, de una extensión considerable, que se encuentra separado del mar. El aporte de agua a los XXX viene de los ríos, de aguas freáticas y precipitación sobre el espejo de agua."

  ;;                    (kill-word "río" (extract-text (get-article "río")))
  ;;                    "Un XXX es una corriente natural de agua que fluye con continuidad. Posee un caudal determinado, rara vez es constante a lo largo del año, y desemboca en el mar, en un lago o en otro XXX, en cuyo caso se denomina afluente. La parte final de un XXX es su desembocadura. Algunas veces terminan en zonas desérticas donde sus aguas se pierden por infiltración y evaporación; es el caso de los XXX alóctonos (llamados así porque sus aguas proceden de otros lugares con clima más húmedo), como el caso del Okavango en el falso delta donde desemboca, numerosos uadis (wadi en inglés) del Sahara y de otros desiertos. Los cursos fluviales que son muy estrechos, se secan en alguna parte del año o tienen poco caudal reciben los nombres de «riacho», «riachuelo», «quebrada» o «arroyo»."

  ;;                    wiki-quest.core> (wiki-quest "Mantequilla")
  ;;                    "La XXX (en algunos países manteca) es la emulsión de agua en grasa, obtenida como resultado del suero, lavado y amasado de los conglomerados de glóbulos grasos, que se forman por el batido de la crema de leche y es apta para consumo, con o sin maduración biológica producida por bacterias específicas."
  ;;                    wiki-quest.core> (wiki-quest "playa")
  ;;                    "Una XXX es un depósito de sedimentos no consolidados que varían entre arena y grava, excluyendo el fango ya que no es un plano aluvial o costa de manglar, que se extiende desde la base de la duna o el límite donde termina la vegetación hasta una profundidad por donde los sedimentos ya no se mueven. Esta profundidad varía entre XXX y XXX dependiendo de la batimetría, geomorfología y el oleaje. También se encuentran generalmente en bahías protegidas del oleaje y se suelen formar en zonas llanas. También en las riberas de los ríos , véase la XXX de las Moreras en Valladolid (España)."
  ;;                    wiki-quest.core> (wiki-quest "jardín")
  ;;                    "Un ____ (del francés ____, huerto), es una zona del terreno donde se cultivan especies vegetales, con posible añadidura de otros elementos como fuentes o esculturas, para el placer de los sentidos. En castellano se llamaba antiguamente huerto de flor para distinguirlo del huerto donde se cultivan hortalizas. La adopción de la palabra en francés hizo más fácil la distinción entre uno y otro vocablos."
  ;;                    wiki-quest.core> (wiki-quest "llaves")
  ;;                    "Una XXX es un instrumento que se usa para abrir y cerrar las cerraduras incorporadas a objetos que se pretende proteger de accesos no deseados."))
