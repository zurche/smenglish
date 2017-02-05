package com.smenglish.news;

/**
 * Created by alejandro.zurcher on 2/4/2017.
 */

interface NewsContract {
    interface View {
        void onFeedRetrieved(String s);
    }

    interface Presenter {
        void retrieveNewsFeed();
    }
}
