package com.example.sampleapp.demo.service.impl;

import com.example.sampleapp.demo.entity.database.User;
import com.example.sampleapp.demo.entity.request.CreateProfileRequest;
import com.example.sampleapp.demo.error.APIResponseError;
import com.example.sampleapp.demo.service.ProfileService;
import com.example.sampleapp.demo.testrepository.TestProfileRepository;
import com.example.sampleapp.demo.testrepository.TestUserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("ProfileServiceImpl")
@TestPropertySource(locations = "classpath:application-test.properties")
class ProfileServiceImplTest {
    @Autowired
    ProfileService service;

    @Autowired
    TestUserRepository testUserRepository;

    @Autowired
    TestProfileRepository testProfileRepository;

    @Nested
    @DisplayName("create(int userId, CreateProfileRequest request)")
    class CreateProfileTest {
        int userId = 0;

        @BeforeEach
        void setUp() {
            var user = new User();
            user.setName("ユーザー");
            user.setPassword("password");
            var result = testUserRepository.save(user);
            this.userId = result.getId();
        }

        @AfterEach
        void tearDown() {
            testProfileRepository.deleteAll();
            testUserRepository.deleteAll();
            userId = 0;
        }

        @Test
        @DisplayName("正常にProfileを登録できること")
        public void testSuccessCase() {
            // arrange
            var request = new CreateProfileRequest("ゆーざー");

            // act
            var response = service.create(this.userId, request);

            // assert
            assertEquals("ゆーざー", response.nickname());
            assertEquals(this.userId, response.userId());
        }

        @Test
        @DisplayName("同じユーザーIdで登録すると409エラーになる")
        public void testFailureNotUnique() {
            // arrange
            var request = new CreateProfileRequest("ゆーざー");
            service.create(this.userId, request);

            // act
            try {
                service.create(this.userId, request);
                fail();
            } catch (APIResponseError error) {
                assertEquals(409, error.getError().getStatus().value());
                assertEquals("すでにプロフィールが登録されています", error.getMessage());
            }
        }

        @Test
        @DisplayName("ユーザーが存在しない場合は404エラーになる")
        public void testFailureUserNotFound() {
            // arrange
            var request = new CreateProfileRequest("ゆーざー");

            // act
            try {
                service.create(this.userId + 1, request);
                fail();
            } catch (APIResponseError error) {
                assertEquals(404, error.getError().getStatus().value());
                assertEquals("指定されたユーザーが存在しません", error.getMessage());
            }
        }
    }
}