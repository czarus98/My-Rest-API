package com.restapi.project.dto;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class RegistrationDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;
}
