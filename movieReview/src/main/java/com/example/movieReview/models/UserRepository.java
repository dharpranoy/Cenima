package com.example.movieReview.models;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  List<User> findByUserName(String username);

  Boolean existsByEmailId(String emailId);

  Boolean existsByUserName(String username);
}
