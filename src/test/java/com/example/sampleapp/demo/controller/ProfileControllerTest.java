package com.example.sampleapp.demo.controller;

import com.example.sampleapp.demo.entity.request.CreateCommentRequest;
import com.example.sampleapp.demo.entity.request.CreateProfileRequest;
import com.example.sampleapp.demo.entity.response.CreateProfileResponse;
import com.example.sampleapp.demo.service.ProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@DisplayName("ProfileController")
class ProfileControllerTest {
    @MockBean
    ProfileService service;

    @Nested
    @DisplayName("POST /profiles/{userId}")
    class PostProfileTest {
        @Autowired
        MockMvc mockMvc;

        @Test
        @DisplayName("正常にprofileを登録できること")
        void testPostProfile() throws Exception {
            // arrange
            var request = new CreateProfileRequest("us");
            var response = new CreateProfileResponse(1, "us", 2);
            var objectMapper = new ObjectMapper();
            when(service.create(2, request)).thenReturn(response);

            // act & assert
            this.mockMvc.perform(
                    post("/profiles/2")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("us")));

        }

        @Test
        @DisplayName("nicknameがnullの場合、400エラーが返ること")
        void testFailureNickNameNull() throws Exception {
            // arrange
            var request = new CreateProfileRequest(null);
            var objectMapper = new ObjectMapper();

            // act & assert
            this.mockMvc.perform(
                            post("/profiles/2")
                                    .content(objectMapper.writeValueAsString(request))
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("nicknameが全角の場合、400エラーが返ること")
        void testFailureZenkaku() throws Exception {
            // arrange
            var request = new CreateProfileRequest("俺くん");
            var objectMapper = new ObjectMapper();

            // act & assert
            this.mockMvc.perform(
                            post("/profiles/2")
                                    .content(objectMapper.writeValueAsString(request))
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("nicknameが正規表現から外れた場合、400エラーが返ること")
        void testFailureNotRegexp() throws Exception {
            // arrange
            var request = new CreateProfileRequest("Tiger&Bunny");
            var objectMapper = new ObjectMapper();

            // act & assert
            this.mockMvc.perform(
                            post("/profiles/2")
                                    .content(objectMapper.writeValueAsString(request))
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }
}