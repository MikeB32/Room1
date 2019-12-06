package com.task.room.model;

import com.google.gson.annotations.SerializedName;

public class Result {


        @SerializedName("sectionName")
        private String sectionName;
        @SerializedName("webTitle")
        private String webTitle;
        @SerializedName("webUrl")
        private String webUrl;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
