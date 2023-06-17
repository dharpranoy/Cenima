package com.example.movieReview.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Movie {
  @Id
  public String movieId;
  private String title;
  private String director;
  private String producer;
  private String motionPictureRating;
  private String movieDesc;
  private int runtime;
  private double collection;
  private String language;
  private int releaseYear;

  protected Movie() {

  }

  public Movie(String movieId, String title, String director, String producer, String motionPictureRating,
      String movieDesc, int runtime, double collection, String language, int releaseYear) {
    this.movieId = movieId;
    this.title = title;
    this.director = director;
    this.producer = producer;
    this.motionPictureRating = motionPictureRating;
    this.movieDesc = movieDesc;
    this.runtime = runtime;
    this.collection = collection;
    this.language = language;
    this.releaseYear = releaseYear;
  }

  public void setMovieId(String movieId) {

    this.movieId = movieId;
  }

  public void setTitle(String title) {

    this.title = title;
  }

  public void setDirector(String director) {

    this.director = director;
  }

  public void setProducer(String producer) {

    this.producer = producer;
  }

  public void setMotionPictureRating(String motionPictureRating) {

    this.motionPictureRating = motionPictureRating;
  }

  public void setMovieDesc(String movieDesc) {

    this.movieDesc = movieDesc;
  }

  public void setRuntime(int runtime) {

    this.runtime = runtime;
  }

  public void setCollection(double collection) {

    this.collection = collection;
  }

  public void setLanguage(String language) {

    this.language = language;
  }

  public void setReleaseYear(int releaseYear) {

    this.releaseYear = releaseYear;
  }

  public String getMovieId() {
    return this.movieId;
  }

  public String getTitle() {
    return this.title;
  }

  public String getDirector() {
    return this.director;
  }

  public String getProducer() {
    return this.producer;
  }

  public String getMotionPictureRating() {
    return this.motionPictureRating;
  }

  public String getMovieDesc() {
    return this.movieDesc;
  }

  public String getRuntime() {
    return String.valueOf(this.runtime);
  }

  public String getCollection() {
    return String.valueOf(this.collection);
  }

  public String getLanguage() {
    return this.language;
  }

  public String getReleaseYear() {
    return String.valueOf(this.releaseYear);
  }

}
