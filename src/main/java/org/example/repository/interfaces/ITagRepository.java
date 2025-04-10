package org.example.repository.interfaces;

import java.util.Set;

public interface ITagRepository {

    Set<String> findByPostId(Long postId);

    String createTag(String name);

    void changePostTags(Long postId, Set<String> tags);

    void deleteTag(String tag);

    void linkToPost(Long postId, Long tagId);

    void unLinkToPost(Long postId);
}
