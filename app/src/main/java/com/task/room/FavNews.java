package com.task.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_table")
public class FavNews {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private String url;

    private String offlineData;

    public String getOfflineData() {
        return offlineData;
    }

    public void setOfflineData(String offlineData) {
        this.offlineData = offlineData;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}