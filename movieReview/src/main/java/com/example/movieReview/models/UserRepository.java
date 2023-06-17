package com.example.movieReview.models;

import java.util.*;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

  List<User> findByUserName(String username);

  Boolean existsByUserName(String username);
}
