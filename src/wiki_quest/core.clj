(ns wiki-quest.core
  (:require [error-codes.core :as ec]
            [me.raynes.laser :as l]))

(defn get-article [article]
  (first (l/select
          (l/parse (slurp (str "http://es.wikipedia.org/wiki/" article)))
          (l/element= :p))))

(defn extract-text [node]
  (if (= (type node) java.lang.String)
    node
    (apply str (map extract-text (:content node)))))

(defn kill-word [w t]
  (apply str (map (fn [w2]
                    (if (> (:distance (ec/edits w w2)) 1)
                      w2 "XXX"))
                  (map (partial apply str) (partition-by #(Character/isLetter %) t)))))

(defn wiki-quest [article]
  (kill-word article (extract-text (get-article article))))

(comment   (Aufruf " mit Internetverbindung:"
wiki-quest.core> (kill-word "lago" (extract-text (get-article "lago")))
"Un XXX (del latín lacus) es un cuerpo de agua dulce, de una extensión considerable, que se encuentra separado del mar. El aporte de agua a los XXX viene de los ríos, de aguas freáticas y precipitación sobre el espejo de agua."

(kill-word "río" (extract-text (get-article "río")))
"Un XXX es una corriente natural de agua que fluye con continuidad. Posee un caudal determinado, rara vez es constante a lo largo del año, y desemboca en el mar, en un lago o en otro XXX, en cuyo caso se denomina afluente. La parte final de un XXX es su desembocadura. Algunas veces terminan en zonas desérticas donde sus aguas se pierden por infiltración y evaporación; es el caso de los XXX alóctonos (llamados así porque sus aguas proceden de otros lugares con clima más húmedo), como el caso del Okavango en el falso delta donde desemboca, numerosos uadis (wadi en inglés) del Sahara y de otros desiertos. Los cursos fluviales que son muy estrechos, se secan en alguna parte del año o tienen poco caudal reciben los nombres de «riacho», «riachuelo», «quebrada» o «arroyo»."

wiki-quest.core> (wiki-quest "Mantequilla")
"La XXX (en algunos países manteca) es la emulsión de agua en grasa, obtenida como resultado del suero, lavado y amasado de los conglomerados de glóbulos grasos, que se forman por el batido de la crema de leche y es apta para consumo, con o sin maduración biológica producida por bacterias específicas."
wiki-quest.core> (wiki-quest "playa")
"Una XXX es un depósito de sedimentos no consolidados que varían entre arena y grava, excluyendo el fango ya que no es un plano aluvial o costa de manglar, que se extiende desde la base de la duna o el límite donde termina la vegetación hasta una profundidad por donde los sedimentos ya no se mueven. Esta profundidad varía entre XXX y XXX dependiendo de la batimetría, geomorfología y el oleaje. También se encuentran generalmente en bahías protegidas del oleaje y se suelen formar en zonas llanas. También en las riberas de los ríos , véase la XXX de las Moreras en Valladolid (España)."
wiki-quest.core> (wiki-quest "jardin")
"XXX es una población y comuna francesa, en la región de Ródano-Alpes, departamento de Isère, en el distrito de Vienne y cantón de Vienne-Sud."
wiki-quest.core> (wiki-quest "llaves")
"Una XXX es un instrumento que se usa para abrir y cerrar las cerraduras incorporadas a objetos que se pretende proteger de accesos no deseados."))
