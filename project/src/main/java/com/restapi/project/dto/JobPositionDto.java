package com.restapi.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JobPositionDto {


    @NotBlank
    private String title;

    private String description;

    private List<CandidateDto> candidates = new ArrayList<>();

    public JobPositionDto(String title) {
        this.title = title;
    }
}
