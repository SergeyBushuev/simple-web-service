package org.example.repository.mappers;

import org.example.model.Comment;
import org.example.model.Post;
import org.springframework.jdbc.core.RowMapper;

public class RowMappers {
    public static final RowMapper<Post> postRowMapper = (resultSet, rowNum) -> {
        Post post = new Post();
        post.setId(resultSet.getLong("id"));
        post.setTitle(resultSet.getString("title"));
        post.setText(resultSet.getString("text"));
        post.setImage(resultSet.getBytes("image"));
        post.setLikesCount(resultSet.getInt("likes"));

        return post;
    };

    public static final RowMapper<Comment> commentRowMapper = (resultSet, rowNum) -> {
        Comment comment = new Comment();
        comment.setId(resultSet.getLong("id"));
        comment.setText(resultSet.getString("text"));
        comment.setPostId(resultSet.getLong("post_id"));

        return comment;
    };
}
