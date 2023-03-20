package com.restapi.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class SkillDto {

    private Long id;

    @NotBlank
    private String name;

    public SkillDto(String skillName) {
        this.name = skillName;
    }

    public SkillDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
