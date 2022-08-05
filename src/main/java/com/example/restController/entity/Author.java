package com.example.restController.entity;

public class Author {

    private int authorId;
    private String authorName;
    private int experience;

    public Author() {

    }

    public Author(int authorId, String authorName, int experience) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.experience = experience;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
