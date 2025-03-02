#!/usr/bin/env bb

(ns ungandr.game
  (:require [babashka.cli :as cli]
            [ungandr.op :as op]
            [ungandr.sh :as sh]
            [ungandr.show :as show]
            [ungandr.stringer :refer [strr pad-end get-frame]]))

(def dev? (atom true))
(def prev-key (atom nil))

(def store (atom {:walls [[:wall (- 45 8 1)]]}))
(def key-pressed (atom nil))
(def reading? true)

(defn get-dev-info! []
  (let [k @prev-key
        state (strr @store)
        previous-input (str "Previous input: " k " " (some-> k char))]
    (sh/colorize
     (get-frame
      (mapv #(pad-end % 64) [state previous-input]))
     [:fg/black :bg/yellow])))

(defn update-game-state! [new-state]
  (reset! store new-state))

(defn get-key! [default]
  (if-let [key @key-pressed]
    (do (reset! key-pressed nil)
        key)
    default))

(defn start-listening-fo-the-key-pressers []
  (future
    (try
      (while reading?
        (let [key (sh/read-key)]
          (when (not= key -1)
            (reset! key-pressed key)
            (reset! prev-key key))))
      (catch Exception e
        (prn e)))))

(defn game-loop []
  (loop [tick 0]
    (let [game-state @store]
      (sh/shine-that-thang)
      (->> [
            "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="
            "   ·..·°˚  ·..·°˚  Ungandr  ˚°·..·  ˚°·..·   "
            "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="
            ""
            ""
            ""
            ""
            ""
            (show/the-border tick)
            (show/generate-line tick game-state)
            (show/the-border tick)
            ""]
           get-frame
           sh/pl)
      (when @dev? (sh/pl (get-dev-info!)))
      (sh/make-it-so)
      (Thread/sleep 50)
      (when-let [key (get-key! \$)]
        (when-let [newstate (case (get op/keymap (char key) ::op/cmd-not-found)
                            ::op/cmd-not-found game-state
                            ::op/power (-> game-state op/power)
                            ::op/destroy (-> game-state op/destroy)
                            ::op/toggle-dev (do (reset! dev? (not @dev?))
                                                game-state)
                            ::op/quit nil)]
          (-> newstate
              op/move-enemies
              update-game-state!)
          (recur (inc tick)))))))

(defn provoke-ungandr [_opts]
  (try
    (sh/read-char-by-char!)
    (sh/hide-cursor!)
    (sh/take-control-over-powers-of-thee!)
    (sh/to-beyond-the-ether)
    (sh/shine-that-thang)
    (start-listening-fo-the-key-pressers)
    (game-loop)
    (sh/back-to-reality)
    (catch Exception e
      (sh/back-to-reality)
      (sh/pl e))
    (finally
      (sh/relinquish-powers-to-thou!)
      (sh/show-cursor!)
      (sh/read-line-by-line!))))

(def dispatch-table
  [{:cmds [] :fn provoke-ungandr}])

(defn -main [& args]
  (cli/dispatch dispatch-table args))

(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))
