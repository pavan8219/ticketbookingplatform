package com.movieticket.user_service.security;

import com.movieticket.user_service.entity.User;
import com.movieticket.user_service.repository.UserRepository;
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
        System.out.println("in CustomUserDetailsService");
        User user=userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found with email "+username));
        return new CustomUserDetails(user);
    }
}
