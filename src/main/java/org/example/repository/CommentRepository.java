package org.example.repository;

import org.example.model.Comment;
import org.example.repository.interfaces.ICommentRepository;

import java.util.List;

public class CommentRepository implements ICommentRepository {

    @Override
    public Comment findById(Long id) {
        return null;
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        return List.of();
    }

    @Override
    public Comment save(Comment comment) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
