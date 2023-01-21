package com.example.cs310news;

public class NewsObj {

    private int id;
    private String title;
    private String text;
    private String date;
    private String imagePath;
    private String categoryName;

    public NewsObj(int id, String title, String text, String date, String imagePath, String categoryName) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.imagePath = imagePath;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
