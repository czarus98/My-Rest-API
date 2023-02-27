package com.restapi.project.service;

import com.restapi.project.dto.LoginDto;
import com.restapi.project.dto.RegistrationDto;
import com.restapi.project.dto.TokenDto;
import com.restapi.project.exception.ResourceAlreadyExistsError;
import com.restapi.project.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(RegistrationDto registrationDto) throws ResourceAlreadyExistsError;

    User findUserByUsername(String username);

    TokenDto loginUser(LoginDto loginDto);
}
