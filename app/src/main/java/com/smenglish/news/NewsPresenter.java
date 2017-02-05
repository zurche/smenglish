package com.smenglish.news;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

/**
 * Created by alejandro.zurcher on 2/4/2017.
 */

class NewsPresenter implements NewsContract.Presenter {

    private static final String SM_ENGLISH_PAGE_ID = "285663758154050";

    private static final String TAG = NewsPresenter.class.getSimpleName();

    private final NewsContract.View mView;

    NewsPresenter(NewsContract.View newsView) {
        mView = newsView;
    }

    /**
     * Retrieve news feed for the SM English Group facebook page.
     */
    public void retrieveNewsFeed() {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + SM_ENGLISH_PAGE_ID + "/feed",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d(TAG, "Got Feed Response: " + response.getRawResponse());
                        mView.onFeedRetrieved(response.getRawResponse());
                    }
                }
        ).executeAsync();
    }
}
