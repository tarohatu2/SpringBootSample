package com.example.sampleapp.demo.service.impl;

import com.example.sampleapp.demo.entity.database.Profile;
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

    @Nested
    @DisplayName("findById(int profileId")
    class FindProfileTest {
        int profileId = 0;
        int userId = 0;

        @BeforeEach
        public void setup() {
            var user = new User();
            user.setName("ユーザー");
            user.setPassword("password");
            var result = testUserRepository.save(user);
            this.userId = result.getId();

            var profile = new Profile();
            profile.setNickname("ゆざぽん");
            profile.setUser(user);
            var profileResult = testProfileRepository.save(profile);

            this.profileId = profileResult.getId();
        }

        @AfterEach
        public void tearDown() {
            testProfileRepository.deleteAll();
            testUserRepository.deleteAll();

            this.userId = 0;
            this.profileId = 0;
        }

        @Test
        @DisplayName("一件検索が正常に完了すること")
        public void testSuccessCase() {
            var result = service.findById(this.profileId);

            // assert
            assertEquals(this.userId, result.userId());
            assertEquals(this.profileId, result.id());
            assertEquals("ユーザー", result.userName());
            assertEquals("ゆざぽん", result.nickname());
        }

        @Test
        @DisplayName("プロフィールが見つからなかった場合に404を返すこと")
        public void testFailureProfileNotFound() {
            try {
                var result = service.findById(this.profileId + 1);
                fail();
            } catch (APIResponseError error) {
                assertEquals(404, error.getError().getStatus().value());
            }
        }

        @Test
        @DisplayName("ユーザーが削除された場合にProfileも削除されて404を返すこと")
        public void testFailureAfterUserDeleted() {
            // arrange
            testUserRepository.deleteById(this.userId);
            // act
            try {
                var result = service.findById(this.profileId + 1);
                fail();
            } catch (APIResponseError error) {
                assertEquals(404, error.getError().getStatus().value());
            }
        }
    }
}