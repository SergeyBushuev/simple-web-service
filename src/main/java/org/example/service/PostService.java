package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.controller.model.Paging;
import org.example.model.Comment;
import org.example.model.Post;
import org.example.repository.interfaces.IPostRepository;
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
    IPostRepository postRepository;

    private final int DEFAULT_LIKES = 0;
    private final int NEW_ID = -1;

    public List<Post> getPosts(String search, int pageNumber, int pageSize) {
        long postCount = postRepository.getPostCount();
        int offset = (pageNumber - 1) * pageSize;

        if (search == null || search.isEmpty()) {
            return postRepository.getAllPosts(pageSize, offset);
        } else {
            return postRepository.getSearchPosts(search, pageSize, offset);
        }

    }

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
        Set<String> tags = tagsString != null ?
                tagsStringToSet(tagsString) :
                new HashSet<>();

        Post post = new Post(NEW_ID, title, text, imageBytes, DEFAULT_LIKES, new ArrayList<>(), tags);
        return postRepository.save(post);
    }

    @Override
    public void likePost(Long postId, boolean like) {
        postRepository.like(postId, like);
    }

    @Override
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

        Set<String> tags = tagsString != null ? tagsStringToSet(tagsString) : new HashSet<>();
        post.setTags(tags);
        return postRepository.update(post);
    }

    @Override
    public void deletePostById(long id) {
        postRepository.delete(id);
    }

    @Override
    public int getPostCount() {
        return postRepository.getPostCount();
    }

    private Set<String> tagsStringToSet(String tags) {
        return Arrays.stream(tags.split(" ")).map(String::trim).collect(Collectors.toSet());
    }
}
