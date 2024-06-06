package com.example.sampleapp.demo.repository;

import com.example.sampleapp.demo.entity.database.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.stream.Stream;

public interface UserRepository extends
        CrudRepository<User, Integer>,
        PagingAndSortingRepository<User, Integer> {
    @Query("select u from User u where u.name like %?1 order by id desc")
    Stream<User> findByNameEndsWithOrderByIdDesc(String name);

    Page<User> queryFirst10ByName(String name, Pageable pageable);

    @Query(value = "SELECT u from User u JOIN FETCH u.articles WHERE u.id = :id")
    List<User> findUser(Integer id);

    Page<User> findByOrderByNameDesc(Pageable pageable);

}
