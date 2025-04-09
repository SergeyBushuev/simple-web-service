package org.example.repository.interfaces;

import java.util.Set;

public interface ITagRepository {

    Set<String> findByPostId(Long postId);

    String createTag(String name);

    void linkToPost(Long postId, Long tagId);

    void unLinkToPost(Long postId);
}
