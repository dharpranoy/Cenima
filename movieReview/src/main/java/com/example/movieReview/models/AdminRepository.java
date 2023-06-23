package com.example.movieReview.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, String> {

  List<Admin> findByAdminName(String adminName);

  Boolean existsByAdminName(String adminName);
}
