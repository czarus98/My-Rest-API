package com.restapi.project.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class TokenDto {

    @NotBlank
    private final String token;

    @NotBlank
    private final String username;

    public TokenDto(String token, String username) {
        this.token = token;
        this.username = username;
    }
}
