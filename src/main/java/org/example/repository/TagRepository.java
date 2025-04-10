package org.example.repository;

import org.example.repository.interfaces.ITagRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class TagRepository implements ITagRepository {
    @Override
    public Set<String> findByPostId(Long postId) {
        return Set.of();
    }

    @Override
    public String createTag(String name) {
        return "";
    }

    @Override
    public void linkToPost(Long postId, Long tagId) {

    }

    @Override
    public void unLinkToPost(Long postId) {

    }
}
