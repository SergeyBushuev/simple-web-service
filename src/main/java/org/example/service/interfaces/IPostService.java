package org.example.service.interfaces;

import org.example.controller.model.Paging;
import org.example.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPostService {

    List<Post> getPosts(String search, int pageNumber, int pageSize);

    Post getPostById(long id);

    Post createPost(String title, String text, MultipartFile image, String tags);

    void likePost(Long postId, boolean like);

    Post editPost(long id, String title, String text, MultipartFile image, String tags);

    Paging generatePaging(int pageSize, int pageNum);
}
