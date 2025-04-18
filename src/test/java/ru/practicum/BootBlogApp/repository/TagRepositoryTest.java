package ru.practicum.BootBlogApp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.BootBlogApp.repository.interfaces.ITagRepository;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {
        TagRepository.class})
@TestPropertySource(locations = "classpath:applicationTest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TagRepositoryTest {

    @Autowired
    private ITagRepository tagRepository;

    @Test
    void findTagsByPostId_findByPostIdOk_Test() {
        Set<String> tagSet = Set.of("Goose", "Road");

        Set<String> allPostTags = tagRepository.findByPostId(1L);
        assertTrue(allPostTags.containsAll(tagSet));
    }

    @Test
    void linkTagToPost_linkOk_Test() {
        Set<String> newPostTags = tagRepository.linkToPost(Set.of("newTag", "newTag2"), 1L);

        Set<String> allPostTags = tagRepository.findByPostId(1L);
        assertTrue(allPostTags.containsAll(newPostTags));
    }

    @Test
    void unlikeTagsFromPost() {
        Set<String> postTags = tagRepository.findByPostId(1L);
        assertEquals(2, postTags.size());

        tagRepository.unLinkFromPost(1L);

        Set<String> zeroTags = tagRepository.findByPostId(1L);
        assertEquals(0, zeroTags.size());
    }

}
