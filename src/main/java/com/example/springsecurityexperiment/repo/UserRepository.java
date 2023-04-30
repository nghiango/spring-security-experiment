package com.example.springsecurityexperiment.repo;

import com.example.springsecurityexperiment.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {
}
