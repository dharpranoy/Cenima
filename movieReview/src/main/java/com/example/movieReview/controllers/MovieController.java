
package com.example.movieReview.controllers;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.example.movieReview.models.Movie;
import com.example.movieReview.models.MovieRepository;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Controller
@ResponseBody
public class MovieController {

  @Autowired
  MovieRepository movieRepository;

  @GetMapping("/api/scrollMovies")
  public List<Movie> GetMoviesAll(@RequestParam("page") int page) {
    int pageSize = 5;
    int offset = (page - 1) * pageSize;
    int pageNumber = offset / pageSize;
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Movie> moviePage = movieRepository.findAll(pageable);
    List<Movie> movies = moviePage.getContent();
    return movies;
  }

}
