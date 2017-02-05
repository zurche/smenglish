package com.smenglish.news.model;

import com.smenglish.util.ConstantUtil;

/**
 * Created by alejandro.zurcher on 2/5/2017.
 */

public class News {

    private String story;
    private String created_time;
    private String id;
    private String message;
    private String post_url;

    public News() {
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getCreatedTime() {
        return created_time;
    }

    public void setCreatedTime(String created_time) {
        this.created_time = created_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        String postId = id.split("_")[1];
        this.post_url = ConstantUtil.BASE_POST_URL + postId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPostUrl() {
        return post_url;
    }
}
