package com.example.sampleapp.demo.service;

import com.example.sampleapp.demo.entity.database.User;
import com.example.sampleapp.demo.entity.response.FindUserResponse;
import com.example.sampleapp.demo.error.APIResponseError;
import com.example.sampleapp.demo.repository.UserRepository;
import com.example.sampleapp.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DisplayName("UserServiceImplクラスのテスト")
public class UserServiceTests {
    @Nested
    @DisplayName("ユーザー全件検索のテスト")
    public class FindAllTest {
        @Test
        @DisplayName("正常に全件取得できること")
        public void testNormalCase() {

        }

        @Test
        @DisplayName("0件の場合、空のリストを返すこと")
        public void testEmptyCase() {

        }
    }

    @Nested
    @DisplayName("ユーザー個別検索のテスト")
    public class FindByIdTest {

        @MockBean
        UserRepository repository;

        @Autowired
        UserService service;

        private AutoCloseable closeable;
        @BeforeEach
        public void openMocks() {
            closeable = MockitoAnnotations.openMocks(this);
        }
        @AfterEach
        public void releaseMocks() throws Exception {
            closeable.close();
        }

        @ParameterizedTest
        @ValueSource(ints = { 1, 2, 3 })
        public void testSuccessCase(int id) {
            // arrange
            User user = new User();
            user.setName("testUser");
            user.setPassword("password");
            when(repository.findById(id)).thenReturn(Optional.of(user));

            // act
            FindUserResponse response = service.findById(id);

            // assert
            assertEquals("testUser", response.name());
        }

        @ParameterizedTest
        @ValueSource(ints = { 0, -1, 999 })
        public void testFailureCase(int id) {
            // arrange
            when(repository.findById(id)).thenReturn(Optional.empty());

            // act
            try {
                FindUserResponse response = service.findById(id);
                fail();
            } catch (APIResponseError error) {
                // assert
                assertEquals(404, error.getError().getStatus().value());
                assertEquals("指定したユーザーが存在しません", error.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("ユーザー新規追加のテスト")
    public class SaveUserTest {

    }
}
