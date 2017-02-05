package com.smenglish.news;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.smenglish.news.model.News;
import com.smenglish.util.ConstantUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by alejandro.zurcher on 2/4/2017.
 */

class NewsPresenter implements NewsContract.Presenter {

    private static final String TAG = NewsPresenter.class.getSimpleName();
    private static final String DATA_ROOT_ELEMENT = "data";

    private final NewsContract.View mView;
    private final NewsListTransformer mNewsListTransformer;

    NewsPresenter(NewsContract.View newsView) {
        mView = newsView;
        mNewsListTransformer = new NewsListTransformer();
    }

    /**
     * Retrieve news feed for the SM English Group facebook page.
     */
    public void retrieveNewsFeed() {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + ConstantUtil.SM_ENGLISH_PAGE_ID + "/feed",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d(TAG, "Got Feed Response: " + response.getRawResponse());

                        JSONArray responseArray;
                        List<News> newsList = null;

                        try {
                            responseArray = (JSONArray) response.getJSONObject().get(DATA_ROOT_ELEMENT);
                            newsList = mNewsListTransformer.transform(responseArray);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing news response", e);
                        }

                        mView.onFeedRetrieved(newsList);
                    }
                }
        ).executeAsync();
    }
}
