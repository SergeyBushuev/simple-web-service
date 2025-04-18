package ru.practicum.BootBlogApp.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.practicum.BootBlogApp.model.Comment;
import ru.practicum.BootBlogApp.model.Post;
import ru.practicum.BootBlogApp.repository.interfaces.ICommentRepository;
import ru.practicum.BootBlogApp.repository.interfaces.IPostRepository;

import java.util.*;

import static org.example.utils.TestUtils.createMultipartFile;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private IPostRepository postRepository;

    @Mock
    private ICommentRepository commentRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void addPost_Ok_Test() {


        when(postRepository.addPost(any())).thenAnswer(invocation -> {
            Post post = invocation.getArgument(0);
            post.setId(1L);
            return post;
        });

        Post result = postService.addPost("title", "text", createMultipartFile(), "tag tag2");
        assertEquals("title", result.getTitle());
        assertEquals("text", result.getText());
        assertEquals(Set.of("tag", "tag2"), result.getTags());
        assertArrayEquals("image bytes".getBytes(), result.getImage());
    }

    @Test
    void getPostById_Ok_Test() {
        Post post = new Post(1, "title", "text", "image test bytes".getBytes(), 0, new ArrayList<>(), Set.of("tag", "tag2"));

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Post result = postService.getPostById(1L);
        assertEquals("title", result.getTitle());
        assertEquals("text", result.getText());
        assertEquals(Set.of("tag", "tag2"), result.getTags());
        assertArrayEquals("image test bytes".getBytes(), result.getImage());
    }

    @Test
    void getPostById_NotFountException_Test() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(RuntimeException.class, () -> postService.getPostById(1L));
        assertEquals("Post with id 1 not found", e.getMessage());
    }

    @Test
    void getPosts_Ok_Test() {
        Post post = new Post(1, "title", "text", new byte[5], 0, new ArrayList<>(), Set.of("tag", "tag2"));
        Post post2 = new Post(2, "title2", "text2", new byte[5], 0, new ArrayList<>(), Set.of("tag", "tag2"));
        Post post3 = new Post(3, "title3", "text3", new byte[5], 0, new ArrayList<>(), Set.of("tag2", "tag3"));
        Post post4 = new Post(4, "title4", "text4", new byte[5], 0, new ArrayList<>(), Set.of("tag3", "tag4"));
        when(postRepository.getAllPosts(10, 0)).thenReturn(List.of(post, post2, post3, post4));

        assertEquals(4, postService.getPosts("", 1, 10).size());
    }

    @Test
    void updatePosts_Ok_Test() {
        Post post = new Post(1, "title", "text", new byte[5], 0, new ArrayList<>(), Set.of("tag", "tag2"));

        MultipartFile image = createMultipartFile();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.updatePost(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        postService.editPost(1L, "updated tiltle", "updated text", image, "tag3 tag4");

        assertEquals("updated tiltle", post.getTitle());
        assertEquals("updated text", post.getText());
        assertArrayEquals("image bytes".getBytes(), post.getImage());
        Set<String> expectedTags = new HashSet<>(Arrays.asList("tag3", "tag4"));
        assertEquals(expectedTags, post.getTags());
    }

    @Test
    void updatePosts_PostNotFoundException_Test() {
        MultipartFile image = createMultipartFile();

        when(postRepository.findById(70000)).thenReturn(Optional.empty());
        Exception e = assertThrows(RuntimeException.class, () ->
                postService.editPost(70000, "updated tiltle", "updated text", image, "tag3 tag4"));

        assertEquals("Post with id 70000 not found", e.getMessage());
    }

    @Test
    void deletePost_DeletePostOk_Test() {
        postService.deletePostById(1);
        verify(postRepository).delete(1);
    }

    @Test
    void addComment_addCommentOk_Test() {

        Comment comment = new Comment(1L, 1L, "newComment");
        Post post = new Post(1, "title", "text", new byte[5], 0, List.of(comment), Set.of("tag", "tag2"));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(commentRepository.addNewComment(any(Comment.class))).thenReturn(comment);

        postService.addComment(1L, "newComment");

        assertEquals(1, post.getComments().size());
        assertEquals("newComment", post.getComments().getFirst().getText());
    }

    @Test
    void addComment_PostNotFound_Test() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        Exception e = assertThrows(RuntimeException.class, () ->
                postService.addComment(1L, "newComment"));

        assertEquals("Post with id 1 not found", e.getMessage());
    }

    @Test
    void editComment_editCommentOk_Test() {
        Comment comment = new Comment(1L, 1L, "newComment");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.editComment(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Comment editedComment = postService.editComment(1L, 1L, "newComment");

        assertEquals("newComment", editedComment.getText());
    }

    @Test
    void editComment_CommentNotFound_Test() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());
        Exception e = assertThrows(RuntimeException.class, () ->
                postService.editComment(1L, 1L, "newComment"));

        assertEquals("Comment with id 1 on post 1 not found", e.getMessage());
    }


    @Test
    void deleteComment_deleteComnetOk_Test() {

        when(commentRepository.findById(1L)).thenReturn(Optional.of(new Comment()));

        postService.deleteCommentById(1L);
        verify(commentRepository).deleteById(1L);
    }

    @Test
    void deleteComment_CommentNotFound_Test() {

        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception e = assertThrows(RuntimeException.class, () ->
                postService.deleteCommentById(1L));
        assertEquals("Comment with id 1 not found", e.getMessage());

    }

}
