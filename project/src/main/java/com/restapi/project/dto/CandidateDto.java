package com.restapi.project.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CandidateDto {

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

}
