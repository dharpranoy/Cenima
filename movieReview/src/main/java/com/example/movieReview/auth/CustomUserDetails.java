package com.example.movieReview.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class CustomUserDetails implements UserDetails {

        private String username;
        private String password;
        private List<GrantedAuthority> authorities;

        public CustomUserDetails() {

        }

        public void setUsername(String username) {
                this.username = username;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public void setAuthorities(List<GrantedAuthority> authorities) {
                this.authorities = authorities;
        }

        @Override
        public List<GrantedAuthority> getAuthorities() {
                return authorities;
        }

        @Override
        public String getPassword() {
                return password;
        }

        @Override
        public String getUsername() {
                return username;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

}
