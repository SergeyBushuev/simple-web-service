package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.mapper.dbToFrontMapper;
import org.example.controller.model.Paging;
import org.example.controller.model.PostFront;
import org.example.model.Post;
import org.example.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
@RequestMapping
@RequiredArgsConstructor
public class PostController {
    @Autowired
    IPostService postService;

    @GetMapping("/")
    public String getPostsRedirect() {
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String getPosts(Model model,
                           @RequestParam(value = "search", defaultValue = "") String search,
                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                           @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
        List<PostFront> frontPosts = postService.getPosts(search, pageNumber, pageSize)
                .stream().map(dbToFrontMapper::dbToFront)
                .toList();

        int postCount = postService.getPostCount();
        Paging paging = new Paging(
                pageNumber,
                pageSize,
                pageNumber * pageSize < postCount,
                pageNumber > 1);

        model.addAttribute("posts", frontPosts);
        model.addAttribute("search", search);
        model.addAttribute("paging", paging);

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
    public String addPost(@RequestPart(value = "title", required = false) String title,
                          @RequestPart(value = "text", required = false) String text,
                          @RequestPart(value = "tags", required = false) String tags,
                          @RequestPart(value = "image", required = false) MultipartFile image) {
        Post post = postService.createPost(title, text, image, tags);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}/edit")
    public String editPostPage(Model model, @PathVariable(value = "id") Long id) {
        Post post = postService.getPostById(id);
        PostFront postFront = dbToFrontMapper.dbToFront(post);
        model.addAttribute("post", postFront);
        return "add-post";
    }

    @PostMapping(value = "/posts/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String editPost(@PathVariable(value = "id") Long id,
                           @RequestPart(value = "title", required = false) String title,
                           @RequestPart(value = "text", required = false) String text,
                           @RequestPart(value = "tags", required = false) String tags,
                           @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        Post post = postService.editPost(id, title, text, image, tags);
        return "redirect:/posts/" + post.getId();
    }

    @PostMapping("/posts/{id}/like")
    public String likePost(@PathVariable(value = "id") Long id,
                           @RequestParam(value = "like") boolean like) {
        postService.likePost(id, like);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable(value = "id") Long id) {
        postService.deletePostById(id);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{id}/comments")
    public String addComment(@PathVariable(value = "id") Long id,
                             @RequestParam(value = "text") String text) {
        return "redirect:/posts/" + id;//TODO
    }

    @PostMapping("/posts/{id}/comments/{commentId}")
    public String editComment(@PathVariable(value = "id") Long id,
                              @PathVariable(value = "commentId") int commentId,
                              @RequestParam(value = "text") String text) {
        return "redirect:/posts/" + id;//TODO
    }

    @PostMapping("/posts/{id}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable(value = "id") Long id,
                                @PathVariable(value = "commentId") int commentId) {
        return "redirect:/posts" + id;//TODO
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable(value = "id") Long id) {
        byte[] image = postService.getImageByPostId(id);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new ByteArrayResource(image));
    }

}
