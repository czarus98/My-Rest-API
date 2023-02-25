package com.restapi.project.service;

import com.restapi.project.dto.RegistrationDto;
import com.restapi.project.exception.ResourceAlreadyExistsError;
import com.restapi.project.model.User;
import com.restapi.project.repository.UserRepository;
import com.restapi.project.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    BCryptPasswordEncoder encoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByUsername(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.getRole());
    }

    @Override
    public User save(RegistrationDto registrationDto) throws ResourceAlreadyExistsError {
        if (userRepository.existsByUsernameEquals(registrationDto.getUsername()))
            throw new ResourceAlreadyExistsError("User " + registrationDto.getUsername() + " already exists");
        User newUser = new User(encoder.encode(registrationDto.getPassword()), registrationDto.getFirstname(), registrationDto.getLastname(), registrationDto.getUsername());
        return userRepository.save(newUser);
    }
}