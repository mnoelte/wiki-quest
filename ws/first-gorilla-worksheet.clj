;; gorilla-repl.fileformat = 1

;; **
;;; # Gorilla REPL
;;; 
;;; Welcome to gorilla :-)
;;; 
;;; Shift + enter evaluates code. Hit alt+g twice in quick succession or click the menu icon (upper-right corner) for more commands ...
;;; 
;;; It's a good habit to run each worksheet in its own namespace: feel free to use the declaration we've provided below if you'd like.
;; **

;; @@
(ns calm-brook
  (:require [gorilla-plot.core :as plot]
            [wiki-quest.core :as w]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(w/rand-word)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>137</span>","value":"137"}
;; <=

;; @@
(w/wiki-quest3 (w/rand-word) *1)
;; @@
;; ->
;;; piloto
;;; Piloto o su traducción al inglés Pilot puede referirse a:
;;; 
;;; 
;;; 
;;; 
;;; 
;;; Según la concepción lineal del tiempo que tienen la mayoría de las civilizaciones humanas, el ____ es la porción de la línea temporal que todavía no ha sucedido; en otras palabras, es una conjetura que bien puede ser anticipada, predicha, especulada, postulada, teorizada o calculada a partir de datos en un instante de tiempo concreto. En la relatividad especial, el ____ se considera como el ____ absoluto o el como ____ del tiempo. En física, el tiempo es considerado como una cuarta dimensión.
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>65</span>","value":"65"}
;; <=

;; @@
(w/wiki-quest3 (w/rand-word) *1)
;; @@
;; ->
;;; seguridad
;;; El término seguridad (del latín securitas)[1] cotidianamente se puede referir a la ausencia de riesgo o a la confianza en algo o en alguien. Sin embargo, el término puede tomar diversos sentidos según el área o campo a la que haga referencia. En términos generales, seguridad se define como &quot;estado de bienestar que percibe y disfruta el ser humano&quot;.
;;; 
;;; 
;;; 
;;; 
;;; 
;;; ____ (latín peccātum) es la transgresión voluntaria de un precepto tenido por bueno.
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>130</span>","value":"130"}
;; <=

;; @@

;; @@
