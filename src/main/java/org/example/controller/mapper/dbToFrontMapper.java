package org.example.controller.mapper;

import org.example.controller.model.PostFront;
import org.example.model.Post;

import java.util.List;

public class dbToFrontMapper {

    public static PostFront dbToFront(Post post) {
        PostFront postFront = new PostFront();
        postFront.setId(post.getId());
        postFront.setTitle(post.getTitle());
        postFront.setImage(post.getImage());
        postFront.setText(post.getText());
        postFront.setTextParts(List.of(post.getText().split("\\n")));
        postFront.setComments(post.getComments());
        postFront.setTags(post.getTags());
        return postFront;
    }
}
