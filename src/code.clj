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
    :bullets ["Brought my family to CA to align time zones"
              "Responsible for requirement breakdown & delivery of parts of the stock trading system composed of Clojure based micro services."
              "Stack" ["Datomic"
                       "QuickFIX/J"
                       "Kafka"
                       "Graphql"
                       "ClojureScript"]
              "On boarded Multiple developer and QAs"
              "Developed an internal tool to ingest kafka messages and make them queriable with datalog using crux"
              "One of the oldest devs involved including the time during freelance"
              ["Saw the company grow from few people to ~20 developers"
               "Survived RIF that happened in the middle of funding rounds"]]}
   {:place "Clojurists Together"
    :duration "May 2021 ~ Present"
    :link "https://www.clojuriststogether.org/"
    :title "Board Member"
    :bullets ["Elected as member via election to serve the broad Clojure Community"
              "Discuss and vote on which open source projects are to be funded"]}
   {:place "Youtube Channel Host"
    :duration "Jan 2021 ~ Present"
    :link "https://www.youtube.com/channel/UC4wTwYzpaL7yKWHOKlUpwJQ"
    :bullets ["Discuss Clojure Stuff"
              "Majority of Episodes in Japanese"
              "~2k total views/200 watch hours/70 Subscribers"]}
   {:place "Husband/Parent of 3"
    :duration "2013 ~ Present"
    :bullets ["Moved to Japan right after college to be with my wife who already had a career there"
              "Have children born in 2015/2016/2020"]}
   {:place "Freelance Developer"
    :duration "October 2017 - May 2019"
    :location "Japan"
    :title "Multiple Part Time Roles at multiple places"
    :bullets ["Sales/Frontend Development for physical retail trafic analytics product at Locarise"
              "Web Development for bioinformatics products at Xcoo"
              ["Ongoing partial engagements"]
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
  (cond
    (keyword? (first bts)) [:li bts]
    (coll? bts) [:ul (map ul-list bts)]
    :else [:li bts]))

(def work-history
  (->> history
       (map (fn [{:keys [title place duration link location
                         bullets]}]
              [:div.card.col-6.col #_{:style "width: 20rem"}
               [:div.card-body
                [:h4.card-title place]
                [:p.card-text
                 location (when location " / ")
                 duration " " (when link [:a {:href link} "[link]"])]
                [:h5 title]
                (when bullets
                  (ul-list bullets))]]))
       (partition-all 2)
       (map (fn [entries]
              [:div.row entries]))))
(comment (update))
(def compentency
  [:div.row
   [:div.card.col-4.col
    [:h4.card-title "Clojure"]
    (ul-list
     ["5 years using it with <3 professionally"
      ["Traditional Web App Backend"
       "Graphql APIs with Lacinia"
       "Kafka Streams Apps"
       "Babashka Scripts"]])]
   [:div.card.col-4.col
    [:h4.card-title "ClojureScript"]
    (ul-list
     ["5 years using it with <3 professionally"
      ["Can develop a Reagent Based SPA from scratch with routing and a npm hosted component framework"
       "Complicated geometric UI features with SVGs"
       "Some limited time developing features for a React Narive Apps(re-natal)"]])]
   [:div.card.col-4.col
    [:h4.card-title "Datomic"]
    (ul-list
     ["Write applications that use dynamic queries joining across multiple entities"
      "Worked on query perf investigation"])]
   [:div.card.col-4.col
    [:h4.card-title "Mentoring/Onboarding"]
    (ul-list
     ["Have been a goto onboarding buddy for newcomers at Parkside"
      "Introduce tech stack/domain knowledge that is relevant to the first several task assigned"
      "Alternate between hands on and hands off time"])]
   [:div.card.col-4.col
    [:h4.card-title "Documentation & Presentations"]
    (ul-list
     ["Create concise, high value documentation of features developed"
      "Collaborate with PMs on feature demos for wider audiences"
      "Clearly communicate what the work was done to facilitate efficient QA"])]
   [:div.card.col-4.col
    [:h4.card-title "Japanese"]
    (ul-list
     ["Native speaker"
      "Have performed roles as Interpreter/Translator"])]])

(defn handler [req]
  {:status 200
   :body (hp/html5
          [:head
           [:meta {:charset "UTF-8"}]
           [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
           [:meta {:http-equiv "X-UA-Compatible" :content "ie=edge"}]
           [:link {:rel "stylesheet" :href "https://unpkg.com/papercss@1.8.2/dist/paper.min.css"}]]
          [:div.paper.container
           [:h1 {:style "text-align: center"}
            "Ikuru Leif Kyogoku"]
           [:p {:style "text-align: center"}
            "A motivated, authentic Clojure/ClojureScript developer based in Mountain View, CA"]
           [:div.row.flex-spaces.tabs
            [:input {:id "tab1" :type "radio" :name "tabs" :checked true}]
            [:label {:for "tab1"} "Work/Education History"]

            [:input {:id "tab2" :type "radio" :name "tabs"}]
            [:label {:for "tab2"} "Competencies"]

            [:input {:id "tab3" :type "radio" :name "tabs"}]
            [:label {:for "tab3"} "Contacts/Online Presence"]

            [:div.content {:id "content1"}
             work-history]
            [:div.content {:id "content2"}
             compentency]
            [:div.content {:id "content3"}
             (ul-list
              [[:a {:href "https://www.linkedin.com/in/ikuru-kanuma-bb83496a/"}
                "LinkedIn"]
               [:a {:href "https://github.com/iku000888/"}
                "Github"]
               [:a {:href "https://twitter.com/iku000888"}
                "Twitter"]
               [:a {:href "https://scrapbox.io/iku000888-notes/"}
                "Notes"]
               "Email"
               ["kanumaiku@gmail.com"]])]]])})

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
