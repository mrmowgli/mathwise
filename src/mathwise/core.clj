(ns mathwise.core 
  (:require [clojure.tools.cli :refer [parse-opts]]) 
  (:require [mathwise.calc :refer [display-calc]]) 
  (:gen-class))


(def cli-options
  ;; An option with a required argument
  [["-h" "--height HEIGHT" "Calculation Height (in Pixels)"
    :default 200
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-w" "--width WIDTH" "Calculation Width (in Pixels)"
    :default 200
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-a" "--algo ALGO" 
    "Algorithm to use.  One of: 
     sine      - A modified Sin(x) * Cos(y)
     xor       - Bitwise xor (Default) 
     and       - Bitwise and
     or        - Bitwise or
     plus      - Addition
     minus     - Subtraction 
     multiply  - Multiplication 
     right     - Bitwise shift right
     left      - Bitwise shift left
     and-not   - Bitwise and-not"
    :default "xor"
    :parse-fn #(str %)
    :validate [(partial re-seq 
                 #"[sine|Sine|SINE|xor|XOR|Xor|and|And|AND|or|Or|OR|plus|Plus|PLUS]"
               ) "Must be a supported algorithm."]]
   ;; A non-idempotent option
   ["-m" "--monochrome" "Display in greyscale"]
   ["-d" "--debug" "Some debug information"]
   ;; A boolean option defaulting to nil
   [nil "--help" "This help message."]])


;; Equiv to Class Main.
(defn -main [& args]
  (let [opts (parse-opts args cli-options) {
                {debug :debug} :options 
                {help :help} :options 
                {errors :errors} :options 
              } opts]  
    (cond (= help true) (seq (println "Usage: mathwise [options]\n" (:summary opts)) (System/exit 0)))
    ;(println (:options opts)) 
    (display-calc (:options opts)) 
  )        
)

