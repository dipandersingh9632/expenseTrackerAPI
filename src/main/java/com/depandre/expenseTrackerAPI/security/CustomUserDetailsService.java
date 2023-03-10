package com.depandre.expenseTrackerAPI.security;

import com.depandre.expenseTrackerAPI.entity.User;
import com.depandre.expenseTrackerAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("User Not found for the email: " + email);
        }
        User existingUser = optionalUser.get();
        return new org.springframework.security.core.userdetails.User
                (existingUser.getEmail(), existingUser.getPassword(), new ArrayList<>());
    }
}
