#!/usr/bin/env bb

(ns ungandr.game
  (:require
   [babashka.cli :as cli]
   [ungandr.op :as op]
   [ungandr.sh :as sh]
   [ungandr.show :as show]
   [ungandr.stringer :refer [strr pad-end]]))

(def dev? (atom true))
(def prev-key (atom nil))

(def store (atom {:walls [[:wall (- 32 8 1)]]}))
(def key-pressed (atom nil))
(def reading? true)

(defn print-dev-info! []
  (sh/pl (sh/colorize
          (pad-end (strr @store) 64)
          [:fg/black :bg/yellow]))
  (let [k @prev-key]
    (sh/pl (sh/colorize
            (pad-end (str "Previous input: " k " " (some-> k char)) 64)
            [:fg/black :bg/yellow]))))

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
      (sh/pl "(s)tart (q)uit\n")
      (sh/pl (show/the-border tick))
      (sh/pl (show/generate-line tick game-state))
      (sh/pl (show/the-border tick))
      (sh/pl)
      (when @dev? (print-dev-info!))
      (sh/make-it-so)
      (Thread/sleep 50)
      (when-let [key (get-key! \$)]
        (case (get op/keymap (char key) ::op/cmd-not-found)
          ::op/cmd-not-found (do
                               (-> game-state op/move-enemies update-game-state!)
                               (recur (inc tick)))

          ::op/power (do
                       (-> game-state op/power op/move-enemies update-game-state!)
                       (recur (inc tick)))

          ::op/destroy (do
                         (-> game-state op/destroy op/move-enemies update-game-state!)
                         (recur (inc tick)))

          ::op/toggle-dev (do
                            (reset! dev? (not @dev?))
                            (recur (inc tick)))
          ::op/quit nil)                   ; end loop
        ))))

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
