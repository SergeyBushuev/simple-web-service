package org.example.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class Post {
    private int id;
    private String title;
    private String text;
    private MultipartFile image;
    private int likesCount;
    private List<String> comments;

    public Post() {
    }

    public Post(int id, String title, String text, MultipartFile image, int likesCount, List<String> comments) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.image = image;
        this.likesCount = likesCount;
        this.comments = comments;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
