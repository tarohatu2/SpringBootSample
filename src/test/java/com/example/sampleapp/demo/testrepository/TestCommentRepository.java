package com.example.sampleapp.demo.testrepository;

import com.example.sampleapp.demo.entity.database.Comment;
import org.springframework.data.repository.CrudRepository;

public interface TestCommentRepository extends CrudRepository<Comment, Long> {
}
