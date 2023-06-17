package com.example.movieReview.controllers;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.movieReview.models.UserRepository;
import com.example.movieReview.models.User;

@Controller
@ResponseBody
public class AuthController {

  private final UserRepository userRepository;

  private final AuthenticationManager authenticationManager;

  public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/api/register")
  public ResponseEntity<String> registerUser(@RequestBody Map<String, Object> requestBody) {

    String uName = (String) requestBody.get("username");
    String pWdd = (String) requestBody.get("password");
    long phoneNo = Long.parseLong(requestBody.get("phoneNo").toString());

    if (userRepository.existsByUserName(uName)) {
      return ResponseEntity.badRequest().body("Username already taken");
    }

    String uid = UUID.randomUUID().toString();
    User new_user = new User(uid, uName, BCrypt.hashpw(pWdd, BCrypt.gensalt(12)), phoneNo);
    userRepository.save(new_user);
    return ResponseEntity.ok("Registration Successfull");

  }

  @PostMapping("/api/login")
  public ResponseEntity<String> signInUser(@RequestBody Map<String, Object> requestBody) {

    String username = (String) requestBody.get("username");
    String password = (String) requestBody.get("password");

    try {

      Authentication authentication = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(username, password));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      // UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
    return ResponseEntity.ok("Successfully logged in");

  }

  @PostMapping("/user/private")
  public String PrivateResponse() {
    return "Private Entity";
  }

}
