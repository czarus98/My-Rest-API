package com.restapi.project.service;

import com.restapi.project.dto.RegistrationDto;
import com.restapi.project.exception.ResourceAlreadyExistsError;
import com.restapi.project.model.Role;
import com.restapi.project.model.User;
import com.restapi.project.repository.RoleRepository;
import com.restapi.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(RegistrationDto registrationDto) throws ResourceAlreadyExistsError {
        User user = new User();
        if (userRepository.existsByUsernameEquals(registrationDto.getUsername())) {
            throw new ResourceAlreadyExistsError("User " + registrationDto.getUsername() + " already exists");
        }
        user.setFirstname(registrationDto.getFirstname());
        user.setLastname(registrationDto.getLastname());
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        if (roleRepository.findByName("ROLE_USER") != null) {
            user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        } else {
            user.setRoles(Collections.singletonList(new Role("ROLE_USER")));
        }
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}