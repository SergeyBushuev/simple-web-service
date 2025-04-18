package ru.practicum.BootBlogApp.repository.interfaces;


import ru.practicum.BootBlogApp.model.Post;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {
    Optional<Post> findById(long id);

    void like(Long id, boolean like);

    Post updatePost(Post post);

    Post addPost(Post post);

    int getPostCount();

    int getPostCount(String search);

    List<Post> getAllPosts(int pageSize, int offset);

    List<Post> getSearchPosts(String search, int pageSize, int offset);

    void delete(long postId);
}
