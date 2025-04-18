package ru.practicum.BootBlogApp.utils;

import org.springframework.mock.web.MockMultipartFile;

public class TestUtils {


    public static MockMultipartFile createMultipartFile() {
        return new MockMultipartFile(
                "image",
                "testImage.jpg",
                "image/jpeg",
                "image bytes".getBytes());
    }
}
