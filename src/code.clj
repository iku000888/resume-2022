(ns code
  (:require [ring.adapter.jetty :as j]
            [hiccup.page :as hp]))

(defonce state (atom {}))

(def history
  [{:place "Parkside Technologies"
    :duration "June 2019 - Present"
    :location  "San Francisco, CA"
    :link "https://parkside.app/#/"
    :title "Senior Software Engineer"
    :bullets ["Responsible for requirement breakdown & delivery of parts of the stock trading system composed of Clojure based microservices."
              "Stack" ["Datomic"
                       "QuickFIX/J"
                       "Kafka"
                       "Graphql"
                       "ClojureScript"]
              "Onboarded Multiple developer and QAs"
              "Developed an internal tool to ingest kafka messages and make them queriable with datalog using crux"
              "One of the oldest devs involved including the time during freelance"
              ["Saw the company grow from few people to ~20 developers"
               "Survived RIF that happened in the middle of funding roundes"]]}
   {:place "Freelance Developer"
    :duration "October 2017 - May 2019"
    :location "Japan"
    :title "Multiple Part Time Roles at multiple places"
    :bullets ["Sales/Frontend Development for physical retail trafic analytics product at Locarise"
              "Web Development for bioinformatics products at Xcoo"
              "React Native development (with ClojureScript) at LiME inc"
              "Backend Development at Parkside Technologies (Current Employer)"]}
   {:place "Toyokumo (Formerly Cybozu Startups)"
    :duration "May 2016 - October 2017"
    :location "Japan"
    :title "Software Engineer"
    :link "https://toyokumo.co.jp/"
    :bullets ["Replacement project of a natural disaster alerting/employee safety confirmation system"
              ["As of 2021, serving over a million end users & several thousand companies"]
              "Internal Customization with ClojureScript of Kintone (~Sales Force Platform popular in Japan) Apps"]}
   {:place "Tata Consultancy Services, Japan"
    :duration "September 2014 - April 2016"
    :location "Japan"
    :title "Bridge Engineer"
    :bullets ["Facilitate Discussions Between Japanese Clients and Delivery team in India"
              "Translate/maintain/coordinate English/Japanese versions of requirement/design documents"]}
   {:place "University of California, Santa Cruz"
    :title "Robotics Engineering B.S."
    :duration "September 2014 - April 2016"
    :location "Japan"
    :bullets ["Finished in 3 years"
              "cum laude"
              "Research topic: Make a robotic arm drill a tooth"]}])
(comment (update))
(defn ul-list [bts]
  (if (coll? bts)
    [:ul (map ul-list bts)]
    [:li bts]))

(def work-history
  (->> history
       (map (fn [{:keys [title place duration link location
                         bullets]}]
              [:div.card.col-6.col #_{:style "width: 20rem"}
               [:div.card-body
                [:h4.card-title place]
                [:p.card-text location " / " duration " " (when link [:a {:href link} "[link]"])]
                [:h5 title]
                (when bullets
                  (ul-list bullets))]]))
       (partition-all 2)
       (map (fn [entries]
              [:div.row entries]))))

(defn handler [req]
  {:status 200
   :body (hp/html5
          [:head
           [:meta {:charset "UTF-8"}]
           [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
           [:meta {:http-equiv "X-UA-Compatible" :content "ie=edge"}]
           [:link {:rel "stylesheet" :href "https://unpkg.com/papercss@1.8.2/dist/paper.min.css"}]]
          [:div.paper.container
           [:h1 {:style "text-align: center"}"Ikuru Leif Kyogoku"]
           [:div.row.flex-spaces.tabs
            [:input {:id "tab1" :type "radio" :name "tabs" :checked true}]
            [:label {:for "tab1"} "Work/Education History"]

            [:input {:id "tab2" :type "radio" :name "tabs"}]
            [:label {:for "tab2"} "Competencies"]

            [:input {:id "tab2" :type "radio" :name "tabs"}]
            [:label {:for "tab2"} "Contact Info"]

            [:div.content {:id "content1"}
             work-history]
            [:div.content {:id "content2"}
             "Bar"]]])})

(defn update []
  (swap! state assoc ::handler handler))

(defn serve []
  (swap! state
         (fn [s]
           (-> s
               (assoc ::handler handler
                      ::server
                      (if (::server s)
                        (::server s)
                        (j/run-jetty (fn [r]
                                       ((::handler @state) r))
                                     {:port 12000
                                      :join? false})))))))
