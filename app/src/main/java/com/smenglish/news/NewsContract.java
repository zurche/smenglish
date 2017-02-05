package com.smenglish.news;

import com.smenglish.news.model.News;

import java.util.List;

/**
 * Created by alejandro.zurcher on 2/4/2017.
 */

interface NewsContract {
    interface View {
        void onFeedRetrieved(List<News> newsList);
    }

    interface Presenter {
        void retrieveNewsFeed();
    }
}
