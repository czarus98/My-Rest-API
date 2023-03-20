package com.restapi.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class JobPositionDto {

    public Set<SkillDto> skills;
    private Long id;
    @NotBlank
    private String title;
    private String description;
    private List<CandidateDto> candidates;

    public JobPositionDto(String title) {
        this.title = title;
    }


    public JobPositionDto(String title, String description) {
        this.title = title;
        this.description = description;
    }


    public JobPositionDto(String title, Long id, Set<SkillDto> skills, String description, List<CandidateDto> candidates) {
        this.title = title;
        this.id = id;
        this.skills = skills;
        this.description = description;
        this.candidates = candidates;
    }
}
