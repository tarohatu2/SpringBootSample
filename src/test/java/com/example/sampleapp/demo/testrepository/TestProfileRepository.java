package com.example.sampleapp.demo.testrepository;

import com.example.sampleapp.demo.entity.database.Profile;
import org.springframework.data.repository.CrudRepository;

public interface TestProfileRepository extends CrudRepository<Profile, Integer> {
}
