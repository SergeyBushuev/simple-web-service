package org.example.controller.model;

import org.example.model.Comment;

import java.util.List;
import java.util.Set;

public class PostFront {

    private Long id;
    private String title;
    private String text;
    private List<String> textParts;
    private byte[] image;
    private int likesCount;
    private Set<String> tags;
    private List<Comment> comments;

    public PostFront() {
    }

    public PostFront(Long id, String title, String text, List<String> textParts, byte[] image, int likesCount, Set<String> tags, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.textParts = textParts;
        this.image = image;
        this.likesCount = likesCount;
        this.tags = tags;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<String> getTextParts() {
        return textParts;
    }

    public void setTextParts(List<String> textParts) {
        this.textParts = textParts;
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

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTextPreview() {
        if (text == null) {
            return "";
        }
        return text.split("\n")[0];
    }

    public String getTagsAsText() {
        return String.join(" ", tags);
    }
}
