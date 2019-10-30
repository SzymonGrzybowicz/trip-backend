package com.kodilla.tripbackend.security;

import com.kodilla.tripbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {


        Optional<com.kodilla.tripbackend.domains.User> optionalUser = userRepository.findByUsername(username);
        User.UserBuilder builder = null;
        if (optionalUser.isPresent()) {
            com.kodilla.tripbackend.domains.User user = optionalUser.get();
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.disabled(!user.isEnabled());
            builder.password(user.getPassword());
            builder.authorities("USER");
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
}
