package com.smenglish.news;

import com.smenglish.news.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by alejandro.zurcher on 2/5/2017.
 */

class NewsListTransformer {

    private static final String TAG = NewsListTransformer.class.getSimpleName();

    private final String smPattern = "HH:mm dd/MM/yyyy";
    private final SimpleDateFormat smDateFormatter = new SimpleDateFormat(smPattern, Locale.US);
    private final String facebookPostDatePattern = "yyyy-mm-dd'T'HH:mm:ss'+0000'";
    private final SimpleDateFormat facebookPostDateFormatter = new SimpleDateFormat(facebookPostDatePattern, Locale.US);


    private static final String STORY_TAG = "story";
    private static final String CREATED_TIME_TAG = "created_time";
    private static final String MESSAGE_TAG = "message";
    private static final String ID_TAG = "id";

    List<News> transform(JSONArray newsList) throws JSONException {
        ArrayList<News> transformedNewsList = new ArrayList<>();

        for (int index = 0; index < newsList.length(); index++) {
            JSONObject jsonObject = (JSONObject) newsList.get(index);
            News currentNewsObject = new News();

            try {
                Date postFormattedDate = facebookPostDateFormatter.parse(jsonObject.get(CREATED_TIME_TAG).toString());
                currentNewsObject.setCreatedTime(smDateFormatter.format(postFormattedDate));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                currentNewsObject.setStory(jsonObject.get(STORY_TAG).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                currentNewsObject.setId(jsonObject.get(ID_TAG).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                currentNewsObject.setMessage(jsonObject.get(MESSAGE_TAG).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            transformedNewsList.add(currentNewsObject);
        }

        return transformedNewsList;
    }

}
