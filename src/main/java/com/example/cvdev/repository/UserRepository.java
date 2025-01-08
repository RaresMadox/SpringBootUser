package com.example.cvdev.repository;

import com.example.cvdev.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findByEmail(String email);

}
