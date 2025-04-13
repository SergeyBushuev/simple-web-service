package org.example.repository;

import org.example.model.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.example.config.DataSourceConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, CommentRepository.class})
@TestPropertySource(locations = "classpath:applicationTest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void addComment_addCommentOkTest() {
        Comment comment = new Comment(-1L, 1L, "newTestComment");

        Comment addedComment = commentRepository.addNewComment(comment);
        assertNotNull(addedComment.getId());

        Optional<Comment> found = commentRepository.findById(addedComment.getId());
        assertFalse(found.isEmpty());
        assertEquals("newTestComment", found.get().getText());
        assertEquals(1L, found.get().getPostId());
    }

    @Test
    void editComment_editCommentOkTest() {
        Comment comment = new Comment(-1L, 1L, "newTestComment");

        Comment addedCommit = commentRepository.addNewComment(comment);
        addedCommit.setText("update");
        Comment editedComment = commentRepository.editComment(addedCommit);

        Optional<Comment> found = commentRepository.findById(editedComment.getId());
        assertFalse(found.isEmpty());

        assertEquals("update", found.get().getText());
    }

    @Test
    void testFindByPostId() {
        Comment comment = new Comment(-1L, 1L, "newTestComment1");
        Comment comment2 = new Comment(-1L, 1L, "newTestComment2");

        commentRepository.addNewComment(comment);
        commentRepository.addNewComment(comment2);
        List<Comment> comments = commentRepository.findByPostId(1L);

        assertEquals(4, comments.size());
    }

    @Test
    void testDeleteById() {
        Comment comment = new Comment();
        comment.setPostId(1L);
        comment.setText("Комментарий для удаления");
        Comment saved = commentRepository.addNewComment(comment);
        Long id = saved.getId();
        assertEquals(4, id);
        commentRepository.deleteById(id);
        Optional<Comment> deletedComment = commentRepository.findById(id);


        assertTrue(deletedComment.isEmpty());
    }

}
