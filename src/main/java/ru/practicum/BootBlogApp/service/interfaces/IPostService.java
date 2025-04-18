package org.example.service.interfaces;

import org.example.model.Comment;
import org.example.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPostService {

    List<Post> getPosts(String search, int pageNumber, int pageSize);

    Post getPostById(long id);

    Post addPost(String title, String text, MultipartFile image, String tags);

    void likePost(Long postId, boolean like);

    byte[] getImageByPostId(long id);

    Post editPost(long id, String title, String text, MultipartFile image, String tags);

    void deletePostById(long id);

    int getPostCount(String search);

    Comment addComment(long postId, String text);

    Comment editComment(long id, long postId, String text);

    void deleteCommentById(long id);
}
