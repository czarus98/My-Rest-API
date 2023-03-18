package com.restapi.project.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
public class CandidateDto {

    List<JobPositionDto> jobPositions;
    List<SkillDto> skills;
    private Long Id;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    @JsonAlias("title")
    private String jobTitle;
    @NotBlank
    private String email;

    public CandidateDto(String firstname, String lastname, String jobTitle, Long id, List<SkillDto> skillList, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.jobTitle = jobTitle;
        this.Id = id;
        this.skills = skillList;
        this.email = email;
    }

    public CandidateDto(String firstname, String lastname, String jobTitle, Long candidateId, List<SkillDto> skills, String email, List<JobPositionDto> jobPositions) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.jobTitle = jobTitle;
        this.Id = candidateId;
        this.email = email;
        this.jobPositions = jobPositions;
        this.skills = skills;
    }

    public CandidateDto(String firstname, String lastname, String jobTitle, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.jobTitle = jobTitle;
        this.email = email;
    }
}
