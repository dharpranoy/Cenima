package com.example.movieReview.controllers;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.movieReview.models.UserRepository;

import aj.org.objectweb.asm.Type;
import jakarta.servlet.http.HttpServletRequest;

import com.example.movieReview.auth.CustomUserDetails;
import com.example.movieReview.models.User;

@Controller
@ResponseBody
public class AuthController {

  private final UserRepository userRepository;

  private final AuthenticationManager authenticationManager;

  private final RedisTemplate<String, Object> redisTemplate;

  public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager,
      RedisTemplate<String, Object> redisTemplate) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.redisTemplate = redisTemplate;
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
  public ResponseEntity<String> signInUser(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {

    String username = (String) requestBody.get("username");
    String password = (String) requestBody.get("password");

    try {

      Authentication authentication = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(username, password));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      System.out.println(((UserDetails) authentication.getPrincipal()).getUsername());
      redisTemplate.opsForValue().set(request.getSession().getId(), authentication.getPrincipal());

    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
    return ResponseEntity.ok("Successfully logged in");

  }

  @GetMapping("/fetch")
  public ResponseEntity<?> getUserDetails(HttpServletRequest request, Authentication authentication) {

    UserDetails userDetails = (UserDetails) redisTemplate.opsForValue().get(request.getSession().getId());
    System.out.println(userDetails.getUsername());

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      System.out.println(authentication.getName());
      return ResponseEntity.ok("g");
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @PostMapping("/user/private")
  public String PrivateResponse() {
    return "Private Entity";
  }

}
