package org.example.repository;

import org.example.model.Post;
import org.example.repository.interfaces.IPostRepository;

import java.util.List;
import java.util.Optional;

public class PostRepository implements IPostRepository {

    @Override
    public Optional<Post> findById(long id) {
        return Optional.empty();
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
    public Post save(Post post) {
        return null;
    }

    @Override
    public void delete(Post post) {

    }
}
