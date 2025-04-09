package org.example.repository.interfaces;

import org.example.model.Comment;

import java.util.List;

public interface ICommentRepository {

    Comment findById(Long id);

    List<Comment> findByPostId(Long postId);

    Comment save(Comment comment);

    void deleteById(Long id);
}