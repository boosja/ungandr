(ns ungandr.show
  (:require [ungandr.sh :as sh]))

(defn materialize-ungandr [worm]
  (loop [step (dec (count worm))
         worm worm
         ugandr {}]
    (if (<= 0 step)
      (let [curr (str (last worm) (apply str (butlast worm)))]
        (recur (dec step) curr (assoc ugandr step curr)))
      ugandr)))

(def ungandr (materialize-ungandr ".·°˚°·._"))

(defn glorify [ungandr shiners]
  (let [length (-> ungandr count dec)]
    (apply str (map-indexed (fn [i ch]
                              (if-let [shine (nth shiners (- length i) false)]
                                (sh/colorize ch shine)
                                ch))
                            ungandr))))

(defn the-wyrm [tick game-state]
  (let [gandr (ungandr (mod tick (count ungandr)))]
    (if (:ungandr/shiners game-state)
      (glorify gandr (:ungandr/shiners game-state))
      gandr)))

#_".·°˚°·._"
#_".·'°·.˛¸"

(defn the-border [tick]
  (apply str (take 45 (cycle (if (= 0 (mod tick 2))
                               ["=" "-"]
                               ["-" "="])))))

(def wall-types {:wall "[]"
                 :reinforced "()"
                 :encased "{|}"})

(defn render-dist-ahead [s dist]
  (apply str (repeat (- dist (count s)) \space)))

(defn render-walls [walls]
  (-> (fn [s [tpe dist]]
        (str s
             (render-dist-ahead s dist)
             (get wall-types tpe)))
      (reduce "" walls)))

(defn walls? [game-state]
  (< 0 (-> game-state :walls count)))

(defn generate-line [tick game-state]
  (str (the-wyrm tick game-state)
       (when (walls? game-state)
         (render-walls (:walls game-state)))))
