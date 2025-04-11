package org.example.service.interfaces;

import org.example.model.Post;
import org.springframework.web.multipart.MultipartFile;

public interface IPostService {
    Post getPostById(long id);

    Post createPost(String title, String text, MultipartFile image, String tags);

    Post editPost(long id, String title, String text, MultipartFile image, String tags);
}
