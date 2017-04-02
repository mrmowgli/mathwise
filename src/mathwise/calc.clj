(ns mathwise.calc 
  (:gen-class))


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

; Bulk of the work.
(defn apply-matrix [function maxX maxY] 
  (for [x (range maxX) y (range maxY)] 
    [x y (function x y)]
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

(defn display-calc [args]
  "Should do the actual work."
  (let [{:keys [width height algo monochrome debug] 
      :or {width 200 height 200 algo "multiply" monochrome false debug false}} args]
    (println width height algo monochrome debug)
    (let [gfx (createFrame width height) operation (get fn-map algo)]
      (cond (= debug true) (println width height algo monochrome))      
      (doseq [[x y result] (apply-matrix operation width height)]
        (cond (= monochrome true) 
          (.setColor gfx (java.awt.Color. (clmp result) (clmp result) (clmp result)))
          :else  (.setColor gfx (java.awt.Color. (clmp (+ result x)) (clmp (+ result y)) (clmp (+ result x y))))
        )
        (.fillRect gfx x y 1 1)  
      )
    )
  )
)

