package org.example.controller;

import org.example.controller.model.Paging;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.example.config.DataSourceConfiguration;
import org.example.config.WebConfiguration;

import static org.example.utils.TestUtils.createMultipartFile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringJUnitConfig(classes = {
        DataSourceConfiguration.class,
        WebConfiguration.class})
@WebAppConfiguration()
@TestPropertySource(locations = "classpath:applicationTest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PostControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getPosts_RedirectOk_Test() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void getPosts_PostsPageOk_Test() throws Exception {
        Paging paging = new Paging(1, 10, false, false);

        mockMvc.perform(get("/posts")
                        .param("search", "")
                        .param("pageSize", "10")
                        .param("pageNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("posts"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("paging"))
                .andExpect(model().attribute("paging", paging))
                .andExpect(xpath("//table/tr").nodeCount(7))
                .andExpect(xpath("//table/tr[2]/td/h2").string("Test Post Six"));
    }

    @Test
    void addPost_AddPostPageOk_Test() throws Exception {
        mockMvc.perform(get("/posts/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-post"));
    }

    @Test
    void getPost_GetFirstPostOk_Test() throws Exception {
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(xpath("//h2").string("Test Post One"))
                .andReturn();
    }

    @Test
    void addPost_CreateNewPostOk_Test() throws Exception {
        mockMvc.perform(multipart("/posts")
                        .file(createMultipartFile())
                        .param("title", "New Test Post(Seven)")
                        .param("text", "Completely New Test Post")
                        .param("tags", "Goose newGoose"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/7"));

        mockMvc.perform(get("/posts/7"))
                .andExpect(status().isOk())
                .andExpect(xpath("//h2").string("New Test Post(Seven)"));
    }

    @Test
    void editPost_EditPostPageOk_Test() throws Exception {
        mockMvc.perform(get("/posts/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-post"))
                .andExpect(model().attributeExists("post"));
    }

    @Test
    void updatePost_UpdatePostOk_Test() throws Exception {
        mockMvc.perform(multipart("/posts/1")
                        .file(createMultipartFile())
                        .param("title", "Updated Title")
                        .param("text", "Updated Text")
                        .param("tags", "tag1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(xpath("//h2").string("Updated Title"));
    }

    @Test
    void deletePost_DeletePostOk_Test() throws Exception {
        mockMvc.perform(post("/posts/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }
    @Test
    void likePost_likePostOk_Test() throws Exception {
        mockMvc.perform(post("/posts/1/like")
                        .param("like", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk()) ////table/tr[2]/td/p/form/span[1]
                .andExpect(xpath("//h2").string("Test Post One"))
                .andExpect(xpath("//table/tr[2]/td/p/form/span[1]").string("4"));
    }

    @Test
    void likePost_dislikePostOk_Test() throws Exception {
        mockMvc.perform(post("/posts/1/like")
                        .param("like", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk()) ////table/tr[2]/td/p/form/span[1]
                .andExpect(xpath("//h2").string("Test Post One"))
                .andExpect(xpath("//table/tr[2]/td/p/form/span[1]").string("2"));
    }

    @Test
    void addComment_AddCommentOk_Test() throws Exception {
        mockMvc.perform(post("/posts/1/comments")
                        .param("text", "New comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(xpath("//h2").string("Test Post One"))
                .andExpect(xpath("//table/tr[7]//span[@id='comment4']").string("New comment"));
    }

    @Test
    void editComment_EditCommentOk_Test() throws Exception {
        mockMvc.perform(post("/posts/1/comments/1")
                        .param("text", "Updated comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(xpath("//h2").string("Test Post One"))
                .andExpect(xpath("//table/tr[5]//span[@id='comment1']").string("Updated comment"));
    }

    @Test
    void deleteComment_DeleteCommentOk_Test() throws Exception {
        mockMvc.perform(post("/posts/1/comments/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    void getImage_GetImageOk_Test() throws Exception {
        mockMvc.perform(get("/images/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }

}
