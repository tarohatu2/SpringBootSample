package com.example.sampleapp.demo.service.impl;

import com.example.sampleapp.demo.entity.database.Article;
import com.example.sampleapp.demo.entity.database.Comment;
import com.example.sampleapp.demo.entity.database.User;
import com.example.sampleapp.demo.entity.mapper.CommentMapper;
import com.example.sampleapp.demo.entity.request.CreateCommentRequest;
import com.example.sampleapp.demo.entity.response.CreateCommentResponse;
import com.example.sampleapp.demo.error.APIResponseError;
import com.example.sampleapp.demo.repository.ArticleRepository;
import com.example.sampleapp.demo.repository.CommentRepository;
import com.example.sampleapp.demo.repository.UserRepository;
import com.example.sampleapp.demo.service.CommentService;
import com.example.sampleapp.demo.testrepository.TestArticleRepository;
import com.example.sampleapp.demo.testrepository.TestCommentRepository;
import com.example.sampleapp.demo.testrepository.TestUserRepository;
import org.apiguardian.api.API;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class CommentServiceImplTest {
    @Autowired
    CommentService service;

    @Autowired
    TestCommentRepository commentRepository;

    @Autowired
    TestArticleRepository articleRepository;

    @Autowired
    TestUserRepository userRepository;


    @Nested
    @DisplayName("コメントの新規登録テスト")
    public class TestCreateNewComment {
        private int userId = 0;
        private Long articleId = 0L;

        @BeforeEach
        public void setup() {
            var user = new User();
            user.setName("ユーザー1");
            user.setPassword("password");
            var userResult = userRepository.save(user);
            var article = new Article();
            article.setTitle("記事その1");
            article.setUser(userResult);
            var articleResult = articleRepository.save(article);

            this.userId = userResult.getId();
            this.articleId = articleResult.getId();
        }

        @AfterEach
        public void tearDown() {
            commentRepository.deleteAll();
            articleRepository.deleteAll();
            userRepository.deleteAll();

            this.userId = 0;
            this.articleId = 0L;
        }

        @Test
        @DisplayName("正常にコメントが登録できること")
        public void testSuccessCase() {
            // arrange
            var request = new CreateCommentRequest("コメント", this.userId);

            // act
            var result = service.create(this.articleId.intValue(), request);

            // assert
            assertEquals("コメント", result.title());
        }

        @Test
        @DisplayName("ユーザーが存在しない場合404エラーとなること")
        public void testFailureNotFoundUser() {
            // arrange
            var request = new CreateCommentRequest("コメント", this.userId + 1);

            // act
            try {
                var result = service.create(this.articleId.intValue(), request);
                fail();
            } catch (APIResponseError error) {
                // assert
                assertEquals(404, error.getError().getStatus().value());
                assertEquals("指定されたユーザーが存在しません", error.getMessage());
            }
        }

        @Test
        @DisplayName("記事が存在しない場合404エラーとなること")
        public void testFailureNotFoundArticle() {
            // arrange
            var request = new CreateCommentRequest("コメント", this.userId);

            // act
            try {
                var result = service.create(this.articleId.intValue() + 1, request);
                fail();
            } catch (APIResponseError error) {
                // assert
                assertEquals(404, error.getError().getStatus().value());
                assertEquals("指定された記事が存在しません", error.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("コメントの新規登録テスト")
    public class TestFindComment {
        private int userId = 0;
        private Long articleId = 0L;
        private Long commentId = 0L;

        @BeforeEach
        public void setup() {
            var user = new User();
            user.setName("ユーザー1");
            user.setPassword("password");
            var userResult = userRepository.save(user);
            var article = new Article();
            article.setTitle("記事その1");
            article.setUser(userResult);
            var articleResult = articleRepository.save(article);
            var comment = new Comment();
            comment.setTitle("FindComment");
            comment.setUser(userResult);
            comment.setArticle(articleResult);
            var commentResult = commentRepository.save(comment);

            this.userId = userResult.getId();
            this.articleId = articleResult.getId();
            this.commentId = commentResult.getId();
        }

        @AfterEach
        public void tearDown() {
            commentRepository.deleteAll();
            articleRepository.deleteAll();
            userRepository.deleteAll();

            this.userId = 0;
            this.articleId = 0L;
            this.commentId = 0L;
        }

        @Test
        @DisplayName("正常にコメントを参照できること")
        public void testSuccessCase() {
            // arrange
            Long commentId = this.commentId;

            // act
            var result = service.findComment(commentId);

            // assert
            assertEquals("FindComment", result.title());
            assertEquals("記事その1", result.articleTitle());
            assertEquals("ユーザー1", result.userName());
        }

        @Test
        @DisplayName("記事が存在しない場合404エラーとなること")
        public void testFailureNotFoundComment() {
            // arrange
            Long commentId = this.commentId + 1;

            // act
            try {
                var result = service.findComment(commentId);
                fail();
            } catch (APIResponseError error) {
                assertEquals(404, error.getError().getStatus().value());
                assertEquals("指定されたコメントが存在しません", error.getMessage());
            }
        }
    }
    /*
    @Autowired
    CommentService service;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ArticleRepository articleRepository;

    @MockBean
    CommentRepository commentRepository;

    @MockBean
    CommentMapper mapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("正常に戻り値が返されること")
    public void testSuccessCase() {
        // arrange
        var user = new User();
        user.setName("ユーザー1");
        var article = new Article();
        article.setTitle("記事その1");
        var comment = new Comment();
        comment.setTitle("コメント");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(articleRepository.findById(1)).thenReturn(Optional.of(article));
        when(commentRepository.save(comment)).thenReturn(comment);

        var request = new CreateCommentRequest("コメント", 1);
        when(mapper.mapEntity(request)).thenReturn(comment);
        var response = new CreateCommentResponse(1, "コメント", new Date(), new Date(), 1, 1);
        when(mapper.mapResponse(comment)).thenReturn(response);

        // act
        var result = service.create(1, request);

        // assert
        assertEquals("コメント", result.title());

    }
    @Test
    @DisplayName("ユーザーが存在しない場合、404エラーがスローされること")
    public void testFailureCase1() {
        // arrange
        var article = new Article();
        article.setTitle("記事その1");
        var comment = new Comment();
        comment.setTitle("コメント");

        when(userRepository.findById(1)).thenReturn(Optional.empty());
        when(articleRepository.findById(1)).thenReturn(Optional.of(article));
        when(commentRepository.save(comment)).thenReturn(comment);

        var request = new CreateCommentRequest("コメント", 1);

        // act
        try {
            var result = service.create(1, request);
            fail();
        } catch (APIResponseError error) {
            // assert
            assertEquals(404, error.getError().getStatus().value());
            assertEquals("指定されたユーザーが存在しません", error.getMessage());
        }
    }

    @Test
    @DisplayName("記事が存在しない場合、404エラーがスローされること")
    public void testFailureCase2() {
        // arrange
        var user = new User();
        user.setName("ユーザー1");
        var comment = new Comment();
        comment.setTitle("コメント");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(articleRepository.findById(1)).thenReturn(Optional.empty());
        when(commentRepository.save(comment)).thenReturn(comment);

        var request = new CreateCommentRequest("コメント", 1);

        // act
        try {
            var result = service.create(2, request);
            fail();
        } catch (APIResponseError error) {
            // assert
            assertEquals(404, error.getError().getStatus().value());
            assertEquals("指定された記事が存在しません", error.getMessage());
        }
    }
     */
}