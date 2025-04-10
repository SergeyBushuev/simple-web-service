package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.model.Post;
import org.example.repository.PostRepository;
import org.example.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    @Autowired
    PostRepository postRepository;

    @Override
    public Post getPostById(long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null); //TODO доделать нормально...
    }

    @Override
    public Post editPost(long id, String title, String text, MultipartFile image, String tags) {
        Optional<Post> optPost = postRepository.findById(id);
        if (optPost.isEmpty()) {
            throw new RuntimeException("No such post existed");
        }
        Post post = optPost.get();
        post.setTitle(title);
        post.setText(text);
        try {
            post.setImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't load image", e);
        }
        post.setTags(Arrays.stream(tags.split(" ")).map(String::trim).collect(Collectors.toSet()));
        return postRepository.update(post);
    }


}
