package com.restapi.project.controller;

import com.restapi.project.dto.RegistrationDto;
import com.restapi.project.exception.ResourceAlreadyExistsError;
import com.restapi.project.model.User;
import com.restapi.project.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/api")
public class RegistrationController {
    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> registerRequest(@Valid @RequestBody RegistrationDto registrationDto) {
        try {
            return ResponseEntity.ok(userService.save(registrationDto));
        } catch (ResourceAlreadyExistsError e) {
            logger.info(e.getMessage() + " " + registrationDto.toString());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.info(illegalArgumentException.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
