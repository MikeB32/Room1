package com.task.room.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsArticle {

    @SerializedName("results")
    private List<Result> results = null;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }


}
