
package com.example.movieReview.models;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String> {

  List<Movie> findByTitle(String title);

  Page<Movie> findAll(Pageable pageable);
}
