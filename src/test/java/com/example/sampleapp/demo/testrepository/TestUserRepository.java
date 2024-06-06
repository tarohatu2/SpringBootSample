package com.example.sampleapp.demo.testrepository;

import com.example.sampleapp.demo.entity.database.User;
import org.springframework.data.repository.CrudRepository;

public interface TestUserRepository extends CrudRepository<User, Integer> {
}
