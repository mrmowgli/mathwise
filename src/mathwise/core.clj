(ns app.core 
  (:require [clojure.tools.cli :refer [parse-opts]]) 
  (:gen-class))

(defn apply-matrix [function maxX maxY] 
  (for [x (range maxX) y (range maxY)] 
    [x y (function x y)]
  )   
)

(defn createFrame [width height]
  "This function does the bare minimum for drawing to the canvas as it were.  It returns a gfx context."
  (let [mFrame (java.awt.Frame. "Try Out Xors") ]
    (doto mFrame
      ; AWT overwrites every draw so this is useless.
      ;(.add (java.awt.Label. "Cool Function!" (java.awt.Label/CENTER)))
      (.setSize (java.awt.Dimension. width height))
      (.setVisible true)
      (.addWindowListener
        (proxy [java.awt.event.WindowAdapter] []
          (windowClosing [e] (.dispose mFrame)))))
    ; Return our graphics context 
    (.getGraphics mFrame)
  )
)

; Roll over RGB colors. 
(defn clmp [x] (Math/abs (rem x 255)))

(defn rusk [x y] 
  (int (clmp (Math/floor (* (Math/abs (* (Math/sin x) (Math/cos y))) 100))))
)

(def fn-map {"sine" rusk 
             "xor" bit-xor 
             "and" bit-and
             "or" bit-or
             "plus" +
             "minus" - 
             "multiply" *
             "right" bit-shift-right
             "left" bit-shift-left
             "and-not" bit-and-not
             })


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
     sine - A modified Sin(x) * Cos(y)
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
    :parse-fn #(String/parseStr %)
    :validate [(partial re-seq #"[sine|Sine|SINE|xor|XOR|Xor|and|And|AND|or|Or|OR|plus|Plus|PLUS]" nil) "Must be a supported algorithm."]]
   ;; A non-idempotent option
   ["-v" nil "Verbosity level"
    :id :verbosity
    :default 0
    :assoc-fn (fn [m k _] (update-in m [k] inc))]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])


;; Equiv to Class Main.
(defn -main [& {:keys 
                    [width height functype] 
                ;}]
                :or {width 400 height 400 functype "xor"}}]
  (let [gfx (createFrame width height) operation (get fn-map functype)]
    (println width height functype)
    (doseq [[x y opr] (apply-matrix operation width height)]
      (.setColor gfx (java.awt.Color. (clmp (+ opr x)) (clmp (+ opr y)) (clmp (+ opr x y))))
      ;(.setColor gfx (java.awt.Color. (clmp opr) (clmp opr) (clmp opr)))
      (.fillRect gfx x y 1 1)  
    )
  )
        
)

