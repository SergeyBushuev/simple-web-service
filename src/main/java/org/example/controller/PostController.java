package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.mapper.dbToFrontMapper;
import org.example.controller.model.PostFront;
import org.example.model.Post;
import org.example.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping
@RequiredArgsConstructor
public class PostController {
    @Autowired
    PostService postService;

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

    @GetMapping("/posts/{id}")
    public String getPost(Model model, @PathVariable(value = "id") Long id) {
        Post post = postService.getPostById(id);
        PostFront postFront = dbToFrontMapper.dbToFront(post);
        model.addAttribute("post", postFront);
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

    @GetMapping("/posts/{id}/edit")
    public String editPostPage(Model model, @PathVariable(value = "id") Long id) {
        Post post = postService.getPostById(id);
        PostFront postFront = dbToFrontMapper.dbToFront(post);
        model.addAttribute("post", postFront);
        return "add-post";
    }

//    @PostMapping("/posts/{id}")
//    public String editPost(@PathVariable(value = "id") Long id,
//                           @RequestParam(value = "title") String title,
//                           @RequestParam(value = "text") String text,
//                           @RequestParam(value = "image") MultipartFile image,
//                           @RequestParam(value = "tags") String tags) {
//        Post post = postService.editPost(id, title, text, image, tags);
//        return "redirect:/posts/" + post.getId();
//    }

    @PostMapping("/posts/{id}")
    public String editPost(@PathVariable(value = "id") Long id,
                           @RequestPart("title") String title,
                           @RequestPart("text") String text,
                           @RequestPart("tags") String tags,
                           @RequestPart("image") MultipartFile image
                           ) {
        Post post = postService.editPost(id, title, text, image, tags);
        return "redirect:/posts/" + post.getId();
    }
//    @RequestBody

    @PostMapping("/posts/{id}/like")
    public String likePost(@PathVariable(value = "id") Long id,
                           @RequestParam(value = "like") boolean like) {
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable(value = "id") Long id) {
        return "redirect:/posts/";
    }

    @PostMapping("/posts/{id}/comments")
    public String addComment(@PathVariable(value = "id") Long id,
                             @RequestParam(value = "text") String text) {
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/comments/{commentId}")
    public String editComment(@PathVariable(value = "id") Long id,
                              @PathVariable(value = "commentId") int commentId,
                              @RequestParam(value = "tex") String text) {
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable(value = "id") Long id,
                                @PathVariable(value = "commentId") int commentId) {
        return "redirect:/posts" + id;
    }

    @GetMapping("/images/{id}")
    public String getImage(@PathVariable(value = "id") Long id) {
        return null; /* TODO image load*/
    }

}
