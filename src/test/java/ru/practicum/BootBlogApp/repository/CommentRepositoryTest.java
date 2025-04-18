package ru.practicum.BootBlogApp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.BootBlogApp.model.Comment;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {
        CommentRepository.class})
@TestPropertySource(locations = "classpath:applicationTest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void addComment_addCommentOk_Test() {
        Comment comment = new Comment(-1L, 1L, "newTestComment");

        Comment addedComment = commentRepository.addNewComment(comment);
        assertNotNull(addedComment.getId());

        Optional<Comment> found = commentRepository.findById(addedComment.getId());
        assertFalse(found.isEmpty());
        assertEquals("newTestComment", found.get().getText());
        assertEquals(1L, found.get().getPostId());
    }

    @Test
    void editComment_editCommentOk_Test() {
        Comment comment = new Comment(-1L, 1L, "newTestComment");

        Comment addedCommit = commentRepository.addNewComment(comment);
        addedCommit.setText("update");
        Comment editedComment = commentRepository.editComment(addedCommit);

        Optional<Comment> found = commentRepository.findById(editedComment.getId());
        assertFalse(found.isEmpty());

        assertEquals("update", found.get().getText());
    }

    @Test
    void findComment_findCommentForPostOk_Test() {
        Comment comment = new Comment(-1L, 1L, "newTestComment1");
        Comment comment2 = new Comment(-1L, 1L, "newTestComment2");

        commentRepository.addNewComment(comment);
        commentRepository.addNewComment(comment2);
        List<Comment> comments = commentRepository.findByPostId(1L);

        assertEquals(4, comments.size());
    }

    @Test
    void findComment_CommentNotFoundOk_Test() {
        List<Comment> comments = commentRepository.findByPostId(5L);
        assertEquals(0, comments.size());
    }

    @Test
    void deleteComment_deleteCommentOk_Test() {
        Comment comment = new Comment();
        comment.setPostId(1L);
        comment.setText("delete this comment");
        Comment saved = commentRepository.addNewComment(comment);
        Long id = saved.getId();
        assertEquals(4, id);
        commentRepository.deleteById(id);
        Optional<Comment> deletedComment = commentRepository.findById(id);


        assertTrue(deletedComment.isEmpty());
    }

}
