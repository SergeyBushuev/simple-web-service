package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.model.Comment;
import org.example.model.Post;
import org.example.repository.interfaces.IPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.example.repository.mappers.RowMappers.postRowMapper;

@Repository
@RequiredArgsConstructor
public class PostRepository implements IPostRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Optional<Post> findById(long id) {
        String sqlQuery = "select * from posts where id = " + id;
        List<Post> posts = jdbcTemplate.query(sqlQuery, postRowMapper);
        if (posts.isEmpty()) {
            return Optional.empty();
        }
        Post post = posts.getFirst();
        List<Comment> postComments = commentRepository.findByPostId(post.getId());
        Set<String> postTags = tagRepository.findByPostId(post.getId());

        post.setComments(postComments);
        post.setTags(postTags);
        return Optional.of(post);
    }

    @Override
    public List<Post> findByTag(String searchTag) {
        return List.of();
    }

    @Override
    public long PostsCount() {
        return 0;
    }

    @Override
    public Post update(Post post) {
        String query = "update posts set title = ?, text = ?, image = ? where id = ?";
        jdbcTemplate.update(query, post.getTitle(), post.getText(), post.getImage(), post.getId());

        tagRepository.unLinkToPost(post.getId());
        tagRepository.linkToPost(post.getTags(), post.getId());
        return post;
    }

    @Override
    public Post save(Post post) {
        String sqlQuery = "INSERT INTO posts (title, text, likes, image) VALUES (?, ?, ?, ?)";


        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getText());
            ps.setInt(3, post.getLikesCount());
            ps.setBytes(4, post.getImage());

            return ps;
        }, generatedKeyHolder);
        Long id = (Long) generatedKeyHolder.getKeyList().get(0).get("id");
        post.setId(id);

        tagRepository.linkToPost(post.getTags(), post.getId());
        return post;
    }

    @Override
    public void delete(Post post) {

    }
}
