package com.task.room;



import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_table")
public class FavNews {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;


    public FavNews(String title, String description) {
        this.title = title;
        this.description = description;

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


}