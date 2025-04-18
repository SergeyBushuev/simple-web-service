package org.example.repository;

import org.example.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.example.config.DataSourceConfiguration;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {
        DataSourceConfiguration.class,
        PostRepository.class,
        CommentRepository.class,
        TagRepository.class})
@TestPropertySource(locations = "classpath:applicationTest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void findById_Ok_Test() {
        Optional<Post> optPost = postRepository.findById(5L);
        assertTrue(optPost.isPresent());
        Post post = optPost.get();

        assertEquals("Test Post Five", post.getTitle());
        assertEquals("Gooses a way better than chicken", post.getText());
        assertEquals(2, post.getTags().size());
        assertEquals(post.getTags(), (Set.of("Get", "Goose")));
    }

    @Test
    void addPost_Ok_Test() {
        Post post = new Post(-1, "title", "text", "image".getBytes(), 0, new ArrayList<>(), Set.of("tag", "tag2"));

        long savedPostId = postRepository.addPost(post).getId();
        Optional<Post> optPost = postRepository.findById(savedPostId);
        assertTrue(optPost.isPresent());
        Post newPost = optPost.get();

        assertEquals("title", newPost.getTitle());
        assertArrayEquals("image".getBytes(), newPost.getImage());
        assertEquals(newPost.getTags(), (Set.of("tag", "tag2")));
    }

    @Test
    void editPost_Ok_Test() {
        Post post = new Post(-1, "title", "text", "image".getBytes(), 3, new ArrayList<>(), Set.of("tag", "tag2"));

        Post addedPost = postRepository.addPost(post);

        Post editPost = new Post(addedPost.getId(),
                "update",
                "update",
                "update".getBytes(),
                3, new ArrayList<>(),
                Set.of("update", "update2"));

        Post editedPost = postRepository.updatePost(editPost);
        Optional<Post> optPost = postRepository.findById(editedPost.getId());
        assertTrue(optPost.isPresent());
        Post edited = optPost.get();

        assertEquals("update", edited.getTitle());
        assertEquals("update", edited.getText());
        assertArrayEquals("update".getBytes(), edited.getImage());
        assertEquals(3, edited.getLikesCount());
        assertEquals(Set.of("update", "update2"), edited.getTags());
    }

    @Test
    void findAndSearch_Ok_Test() {
        long goosesCount = postRepository.getPostCount("Goose");
        List<Post> searchedPosts = postRepository.getSearchPosts("Goose", 10, 0);
        assertEquals(goosesCount, searchedPosts.size());
        assertEquals("Test Post One", searchedPosts.getFirst().getTitle());

        List<Post> posts = postRepository.getAllPosts( 10, 0);
        long allPostsCount = postRepository.getPostCount();
        assertEquals(posts.size(), allPostsCount);
        assertEquals(6, allPostsCount);
    }

    @Test
    void testDeletePost() {
        postRepository.delete(1L);

        Optional<Post> opt = postRepository.findById(1L);
        assertTrue(opt.isEmpty());
    }
}
