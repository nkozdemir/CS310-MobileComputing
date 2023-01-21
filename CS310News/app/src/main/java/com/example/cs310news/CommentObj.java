package com.example.cs310news;

public class CommentObj {

    private int commentId;
    private int newsId;
    private String text;
    private String name;

    public CommentObj(int commentId, int newsId, String text, String name) {
        this.commentId = commentId;
        this.newsId = newsId;
        this.text = text;
        this.name = name;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
