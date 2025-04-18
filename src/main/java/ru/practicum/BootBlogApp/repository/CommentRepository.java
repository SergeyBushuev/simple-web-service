package ru.practicum.BootBlogApp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.practicum.BootBlogApp.model.Comment;
import ru.practicum.BootBlogApp.repository.interfaces.ICommentRepository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static ru.practicum.BootBlogApp.repository.mappers.RowMappers.commentRowMapper;


@Repository
public class CommentRepository implements ICommentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Comment> findById(Long id) {
        String sqlQuery = "select * from comments where id = " + id;
        List<Comment> comment = jdbcTemplate.query(sqlQuery, commentRowMapper);
        if (comment.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(comment.getFirst());
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        String sqlQuery = "SELECT * FROM comments where comments.post_id = " + postId;
        return jdbcTemplate.query(sqlQuery, commentRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        String sqlQuery = "DELETE FROM comments where id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public Comment addNewComment(Comment comment){
        String sqlQuery = "INSERT INTO comments (post_id, text) VALUES (?,?)";
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setBigDecimal(1, BigDecimal.valueOf(comment.getPostId()));
            ps.setString(2, comment.getText());

            return ps;
        }, generatedKeyHolder);
        Long id = (Long) generatedKeyHolder.getKeyList().get(0).get("id");
        comment.setId(id);
        return comment;
    }

    @Override
    public Comment editComment(Comment comment){
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
