(ns ungandr.op
  "Operations applied to the game state")

(def keymap
  {\$ ::cmd-not-found
   \u ::power
   \space ::destroy

   \. ::toggle-dev
   \q ::quit
   \ ::quit})

(defn move-enemies [game-state]
  (update game-state :walls #(->> %
                                  (mapv (fn [[w d]]
                                          [w (dec d)])))))

(defn power [game-state]
  (update game-state :ungandr/shiners #(conj (or % []) :fg/red)))

(defn destroy [game-state]
  (update game-state :walls (fn [ws] (filter #(< 2 (second %)) ws))))
