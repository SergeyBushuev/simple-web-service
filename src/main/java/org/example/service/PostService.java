package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Post;
import org.example.repository.PostRepository;
import org.example.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    @Autowired
    PostRepository postRepository;

    @Override
    public Post getPostById(long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null);
    }
}
