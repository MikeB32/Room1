package com.task.room.model;

import com.google.gson.annotations.SerializedName;



public class NewsResponse {

    @SerializedName("response")
    private NewsArticle response;

    public NewsArticle getResponse() {
        return response;
    }

    public void setResponse(NewsArticle response) {
        this.response = response;
    }

}
