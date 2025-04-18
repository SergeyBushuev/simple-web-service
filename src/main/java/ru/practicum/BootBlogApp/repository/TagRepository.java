package ru.practicum.BootBlogApp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.practicum.BootBlogApp.repository.interfaces.ITagRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class TagRepository implements ITagRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Set<String> findByPostId(Long postId) {
        String query = "SELECT tags.tag FROM tags " +
        "JOIN post_tags pt on tags.tag = pt.tag_id AND pt.post_id=" + postId;
        List<String> tags = jdbcTemplate.queryForList(query, String.class);
        return new HashSet<>(tags);
    }

    @Override
    public Set<String> linkToPost(Set<String> tags, Long postId) {
        String query = "INSERT INTO post_tags (post_id, tag_id) " +
                "VALUES (" + postId + ", '" + String.join("'), (" + postId + ", '", tags) + "');";
        jdbcTemplate.update(query);
        return new HashSet<>(tags);
    }

    @Override
    public void unLinkFromPost(Long postId) {
        String query = "DELETE FROM post_tags WHERE post_id=" + postId;
        jdbcTemplate.update(query);
    }

}
