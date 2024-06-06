package com.example.sampleapp.demo.testrepository;

import com.example.sampleapp.demo.entity.database.Article;
import org.springframework.data.repository.CrudRepository;

public interface TestArticleRepository extends CrudRepository<Article, Long> {
}
