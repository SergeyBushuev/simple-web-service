package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PostController {

    @GetMapping("/")
    public String getPostsRedirect() {
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String getPosts(@RequestParam(value = "search", defaultValue = "") String search,
                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                           @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
        return "posts";
    }

    @GetMapping("/posts{id}")
    public String getPost(@RequestParam Long id) {
        return "post";
    }

    @GetMapping("/posts/add")
    public String addPostPage() {
        return "add-post";
    }

    @PostMapping("/posts")
    public String addPost(@RequestParam(value = "title") String title,
                          @RequestParam(value = "text") String text,
                          @RequestParam(value = "image") MultipartFile image,
                          @RequestParam(value = "tags") String tags) {

        return "redirect:/posts/" /* TODO +postID */;
    }

    @PostMapping("/posts/{id}/edit")
    public String editPostPage(@RequestParam Long id) {
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}")
    public String editPost(@RequestParam Long id,
                           @RequestParam(value = "title") String title,
                           @RequestParam(value = "text") String text,
                           @RequestParam(value = "image") MultipartFile image,
                           @RequestParam(value = "tags") String tags) {
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/like")
    public String likePost(@RequestParam Long id,
                           @RequestParam(value = "like") boolean like) {
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@RequestParam Long id) {
        return "redirect:/posts/";
    }

    @PostMapping("/posts/{id}/comments")
    public String addComment(@RequestParam Long id,
                             @RequestParam(value = "text") String text) {
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/comments/{commentId}")
    public String editComment(@RequestParam Long id,
                              @RequestParam(value = "commentId") int commentId,
                              @RequestParam(value = "tex") String text) {
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/comments/{commentId}/delete")
    public String deleteComment(@RequestParam Long id,
                                @RequestParam(value = "commentId") int commentId) {
        return "redirect:/posts" + id;
    }

    @GetMapping("/images/{id}")
    public String getImage(@RequestParam Long id) {
        return null; /* TODO image load*/
    }

}
