package org.example.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;


public class Post {
    private long id;
    private String title;
    private String text;
    private byte[] image;
    private int likesCount;
    private List<Comment> comments;
    private Set<String> tags;

    public Post() {
    }

    public Post(long id, String title, String text, byte[] image, int likesCount, List<Comment> comments, Set<String> tags) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.image = image;
        this.likesCount = likesCount;
        this.comments = comments;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
