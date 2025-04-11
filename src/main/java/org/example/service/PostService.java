package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.model.Comment;
import org.example.model.Post;
import org.example.repository.PostRepository;
import org.example.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    @Autowired
    PostRepository postRepository;

    private final int DEFAULT_LIKES = 0;
    private final int NEW_ID = -1;

    @Override
    public Post getPostById(long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null); //TODO доделать нормально...
    }

    @Override
    public Post createPost(String title, String text, MultipartFile image, String tagsString) {
        byte[] imageBytes;
        try {
            imageBytes = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Can't load image", e);
        }
        Set<String> tags = tagsString != null ? Set.of(tagsString.split(",")) : new HashSet<>();
        Post post = new Post(NEW_ID, title, text, imageBytes, DEFAULT_LIKES, new ArrayList<Comment>(), tags);
        return postRepository.save(post);
    }

    public byte[] getImageByPostId(long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.map(Post::getImage).orElse(null);
    }

    @Override
    public Post editPost(long id, String title, String text, MultipartFile image, String tagsString) {
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

        Set<String> tags = tagsString != null ? Set.of(tagsString.split(" ")) : new HashSet<>();
        post.setTags(tags);
        return postRepository.update(post);
    }


}
