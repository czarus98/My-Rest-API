package com.restapi.project.service;

import com.restapi.project.dto.SkillDto;
import com.restapi.project.model.Skill;

import java.util.List;

public interface SkillService {
    Skill findBySkillName(String skillName);

    void saveSkill(Skill skill);

    List<SkillDto> getAllSkills();

}
