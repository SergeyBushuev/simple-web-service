package org.example.repository;

import org.example.model.Comment;
import org.example.model.Post;
import org.example.repository.interfaces.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
        if (comment.getId() < 0) {
            return createNewCommit(comment);
        } else {
            return updateNewCommit(comment);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sqlQuery = "DELETE FROM comments where id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    private Comment createNewCommit(Comment comment){
        String sqlQuery = "INSERT INTO comments (post_id, text) VALUES (?,?)";
        jdbcTemplate.update(sqlQuery, comment.getPostId(), comment.getText());
        return comment;
    }

    private Comment updateNewCommit(Comment comment){
        String sqlQuery = "UPDATE comments set text = ? where id = ?";
        Object[] params = {extractText(comment), comment.getId()};
        jdbcTemplate.update(sqlQuery, params);

        return comment;
    }

    private String extractText(Comment comment) {
        String text;
        if (comment.getText() == null || comment.getText().isEmpty()) {
            text = "USER FORGOT TO WRITE SOME TEXT";
        } else {
            text = comment.getText();
        }
        return text;
    }

}
