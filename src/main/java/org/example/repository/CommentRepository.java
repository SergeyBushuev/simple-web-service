package org.example.repository;

import org.example.model.Comment;
import org.example.model.Post;
import org.example.repository.interfaces.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.example.repository.mappers.RowMappers.commentRowMapper;

@Repository
public class CommentRepository implements ICommentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Comment> findById(Long id) {
        String sqlQuery = "select * from comments where id = " + id;
        List<Comment> posts = jdbcTemplate.query(sqlQuery, commentRowMapper);
        return Optional.of(posts.getFirst());
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        String sqlQuery = "SELECT * FROM comments where comments.post_id = " + postId;
        List<Comment> posts = jdbcTemplate.query(sqlQuery, commentRowMapper);
        return posts;
    }

    @Override
    public Comment save(Comment comment) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
