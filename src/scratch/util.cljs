(ns scratch.util)

(defn on-mobile [theme]
  ((get-in theme [:breakpoints :down]) "sm"))

(defn on-desktop [theme]
  ((get-in theme [:breakpoints :up]) "sm"))

(defn make-classes [prefix classes]
  (into {} (for [c classes]
             [c (str prefix (name c))])))

(defn set-classes [classes styles]
  (into {} (for [[k v] styles
                 :let [k' (cond
                            (string? k) k
                            (= :root k) (str "&." (:root classes))
                            :else (str "& ." (get classes k)))]]
             [k' v])))
