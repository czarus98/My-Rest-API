package com.restapi.project.service;

import com.restapi.project.dto.SkillDto;
import com.restapi.project.model.Skill;

import java.util.List;

public interface SkillService {
    Skill findBySkillName(String skillName);

    void saveSkill(Skill skill);

    List<SkillDto> getAllSkills();

    SkillDto getSkill(Long id);

    void createSkill(SkillDto skillDto) throws Exception;

    void removeSkill(Long id) throws Exception;

    void putSkill(SkillDto skillDto, Long id) throws Exception;
}
