package com.example.sampleapp.demo.controller;

import com.example.sampleapp.demo.entity.request.CreateCommentRequest;
import com.example.sampleapp.demo.entity.response.CreateCommentResponse;
import com.example.sampleapp.demo.entity.response.FindCommentResponse;
import com.example.sampleapp.demo.error.APIResponseError;
import com.example.sampleapp.demo.error.APIErrors;
import com.example.sampleapp.demo.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class CommentControllerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @MockBean
    CommentService service;


    @Nested
    @DisplayName("モックを使用したテスト")
    class MockingTest {
        @Autowired
        MockMvc mockMvc;

        @Test
        @DisplayName("正常にcommentを追加できること")
        public void testPostComment() throws Exception {
            // arrange
            var response = new CreateCommentResponse(1, "first comment", new Date(), new Date(), 1, 1);
            var request = new CreateCommentRequest("first comment", 1);
            when(service.create(
                    1, request
            )).thenReturn(response);
            var objectMapper = new ObjectMapper();

            // act
            this.mockMvc.perform(
                            post("/arts/1/comments")
                                    .content(objectMapper.writeValueAsString(request))
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("first comment")));
        }

        @Test
        @DisplayName("リクエストボディが存在しない場合、400を返すこと")
        public void testPostCommentWithoutBody() throws Exception {
            // arrange

            // act
            this.mockMvc.perform(
                            post("/arts/1/comments")
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Articleが存在しない場合、404を返すこと")
        @WithMockUser
        public void testPostCommentArticleNotFound() throws Exception {
            // arrange
            var request = new CreateCommentRequest("first comment", 1);
            when(service.create(
                    2, request
            )).thenThrow(new APIResponseError(APIErrors.NOT_FOUND, new IllegalArgumentException(), "記事が見つかりません"));
            var objectMapper = new ObjectMapper();
            // act
            this.mockMvc.perform(
                            post("/arts/2/comments")
                                    .content(objectMapper.writeValueAsString(request))
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Userが存在しない場合、404を返すこと")
        public void testPostCommentUserNotFound() throws Exception {
            // arrange
            var request = new CreateCommentRequest("first comment", 2);
            when(service.create(
                    1, request
            )).thenThrow(new APIResponseError(APIErrors.NOT_FOUND, new IllegalArgumentException(), "ユーザーが見つかりません"));
            var objectMapper = new ObjectMapper();
            // act
            this.mockMvc.perform(
                            post("/arts/1/comments")
                                    .content(objectMapper.writeValueAsString(request))
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Titleが空文字の場合、400を返すこと")
        public void testPostCommentTitleEmpty() throws Exception {
            // arrange
            var request = new CreateCommentRequest("", 1);
            var objectMapper = new ObjectMapper();

            // act
            this.mockMvc.perform(
                            post("/arts/1/comments")
                                    .content(objectMapper.writeValueAsString(request))
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("コメントが正常に取得できること")
        public void testGetComment() throws Exception {
            // arrange
            var response = new FindCommentResponse(
                    1,"コメント111", "ユーザー222", 1, new Date(), new Date(), 1, "記事333"
            );
            when(service.findComment(1L)).thenReturn(response);
            // act
            this.mockMvc
                    .perform(get("/arts/1/comments/1"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("コメント111")))
                    .andExpect(content().string(containsString("ユーザー222")))
                    .andExpect(content().string(containsString("記事333")));

        }

    }


}