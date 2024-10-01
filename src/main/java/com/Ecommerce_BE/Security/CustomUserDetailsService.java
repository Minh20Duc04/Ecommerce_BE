package com.Ecommerce_BE.Security;

import com.Ecommerce_BE.Exception.NotFoundException;
import com.Ecommerce_BE.Model.User;
import com.Ecommerce_BE.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(()->new NotFoundException("User not found"));

        return AuthUser.builder()
                .user(user)
                .build();
    }


















}
