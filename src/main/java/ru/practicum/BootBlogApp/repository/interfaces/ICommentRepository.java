package org.example.repository.interfaces;

import org.example.model.Comment;

import java.util.List;
import java.util.Optional;

public interface ICommentRepository {

    Optional<Comment> findById(Long id);

    List<Comment> findByPostId(Long postId);

    void deleteById(Long id);

    Comment addNewComment(Comment comment);

    Comment editComment(Comment comment);
}