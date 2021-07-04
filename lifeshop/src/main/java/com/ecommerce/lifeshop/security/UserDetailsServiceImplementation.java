package com.ecommerce.lifeshop.security;

import com.ecommerce.lifeshop.model.User;
import com.ecommerce.lifeshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       
    	Optional<User> user = repository.findByEmail(email);
        
        user.orElseThrow(() -> new UsernameNotFoundException(email + " not found."));

        return user.map(UserDetailsImplementation::new).get();
    	
    }

}
