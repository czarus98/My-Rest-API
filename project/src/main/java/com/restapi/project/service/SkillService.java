package com.restapi.project.service;

import com.restapi.project.model.Skill;

public interface SkillService {
    Skill findBySkillName(String skillName);

    void saveSkill(Skill skill);
}
