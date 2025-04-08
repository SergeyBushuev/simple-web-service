package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PostController {

    @GetMapping("/")
    public String getPostsRedirect() {}

    @GetMapping("/posts")
    public String getPosts(String search, int pageSize, int pageNumber) {}

    @GetMapping("/posts{id}")
    public String getPost(int id) {}

    @GetMapping("/posts/add")
    public String addPostPage() {}

    @PostMapping("/posts")
    public String addPost(String title, String text, MultipartFile image, String tags) {}

    @PostMapping("/posts/{id}/edit")
    public String editPostPage(int id) {}

    @PostMapping("/posts/{id}")
    public String editPost(int id, String title, String text, MultipartFile image, String tags) {}

    @PostMapping("/posts/{id}/like")
    public String likePost(int id, boolean like) {}

    @PostMapping("/posts/{id}/delete")
    public String deletePost(int id) {}

    @PostMapping("/posts/{id}/comments")
    public String addComment(int id, String text) {}

    @PostMapping("/posts/{id}/comments/{commentId}")
    public String editComment(int id, int commentId, String text) {}

    @PostMapping("/posts/{id}/comments/{commentId}/delete")
    public String deleteComment(int id, int commentId) {}

    @GetMapping("/images/{id}")
    public String getImage(int id) {}


}
