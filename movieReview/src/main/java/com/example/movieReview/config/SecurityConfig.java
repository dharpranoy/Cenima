package com.example.movieReview.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.movieReview.auth.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;

  public PasswordEncoder passwordEncoder() {
    return new CustomPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http
        .getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    return authenticationManagerBuilder.build();
    /*
     * return authentication -> {
     * String username = authentication.getPrincipal().toString();
     * String password = authentication.getCredentials().toString();
     * 
     * UserDetails userDetails =
     * userDetailsServiceImpl.loadUserByUsername(username);
     * System.out
     * .println(userDetails.getUsername() + " " + userDetails.getPassword() + " " +
     * userDetails.getAuthorities());
     * 
     * if (!passwordEncoder().matches(password, userDetails.getPassword())) {
     * throw new BadCredentialsException("Invalid username/password");
     * }
     * 
     * return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
     * userDetails.getPassword(),
     * userDetails.getAuthorities());
     * };
     */
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetailsServiceImpl);
    return provider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors().and().csrf().disable()
        .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
            .requestMatchers("/api/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**").hasRole("USER")
            .anyRequest().permitAll()

        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Set session creation policy
            .maximumSessions(3) // Allow only one session per user
            .maxSessionsPreventsLogin(false))
        .httpBasic().disable();
    return http.build();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
  }

}
