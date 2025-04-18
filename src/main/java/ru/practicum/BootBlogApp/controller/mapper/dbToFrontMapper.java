package ru.practicum.BootBlogApp.controller.mapper;



import ru.practicum.BootBlogApp.controller.model.PostFront;
import ru.practicum.BootBlogApp.model.Post;

import java.util.List;

public class dbToFrontMapper {

    public static PostFront dbToFront(Post post) {
        PostFront postFront = new PostFront();
        postFront.setId(post.getId());
        postFront.setTitle(post.getTitle());
        postFront.setImage(post.getImage());
        postFront.setText(post.getText());
        postFront.setTextParts(List.of(post.getText().split("\\n")));
        postFront.setLikesCount(post.getLikesCount());
        postFront.setComments(post.getComments());
        postFront.setTags(post.getTags());
        return postFront;
    }
}
