package com.example.movieReview.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
  public AuthenticationManager authenticationManager() throws Exception {
    return authentication -> {
      String username = authentication.getPrincipal().toString();
      String password = authentication.getCredentials().toString();

      UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

      if (!passwordEncoder().matches(password, userDetails.getPassword())) {
        throw new BadCredentialsException("Invalid username/password");
      }

      return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    };
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
        .httpBasic().disable();
    return http.build();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
  }

}
