package org.example.repository.interfaces;

import org.example.model.Post;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {
    Optional<Post> findById(long id);

    List<Post> findByTag(String searchTag);

    long PostsCount();

    void like(Long id, boolean like);

    Post update(Post post);

    Post save(Post post);

    void delete(Post post);
}
