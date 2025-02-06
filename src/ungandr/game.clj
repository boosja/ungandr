#!/usr/bin/env bb

(ns ungandr.game
  (:require
   [babashka.cli :as cli]
   [ungandr.op :as op]
   [ungandr.sh :as sh]
   [ungandr.show :as show]))

(def store (atom {:walls [[:wall (- 32 8 1)]]}))
(def key-pressed (atom nil))
(def reading? true)

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
            (reset! key-pressed key))))
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
      (sh/make-it-so)
      (Thread/sleep 50)
      (when-let [key (get-key! \$)]
        (case (get op/keymap (char key) ::op/cmd-not-found)
          ::op/cmd-not-found (do
                               (-> game-state op/move-enemies update-game-state!)
                               (recur (inc tick)))

          ::op/destroy (do
                         (-> game-state op/destroy op/move-enemies update-game-state!)
                         (recur (inc tick)))

          ::op/quit nil)                   ; end loop
        ))))

(defn provoke-ungandr [_opts]
  (try
    (sh/read-char-by-char!)
    (sh/to-beyond-the-ether)
    (sh/shine-that-thang)
    (start-listening-fo-the-key-pressers)
    (game-loop)
    (sh/back-to-reality)
    (catch Exception e
      (sh/back-to-reality)
      (sh/pl e))
    (finally
      (sh/read-line-by-line!))))

(def dispatch-table
  [{:cmds [] :fn provoke-ungandr}])

(defn -main [& args]
  (cli/dispatch dispatch-table args))

(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))
