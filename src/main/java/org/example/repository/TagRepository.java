package org.example.repository;

import org.example.repository.interfaces.ITagRepository;

import java.util.Set;

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
