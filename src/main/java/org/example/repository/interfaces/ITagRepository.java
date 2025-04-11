package org.example.repository.interfaces;

import java.util.List;
import java.util.Set;

public interface ITagRepository {

    Set<String> findByPostId(Long postId);

    Set<String> linkToPost(Set<String> name, Long postId);

    void unLinkToPost(Long postId);
}
