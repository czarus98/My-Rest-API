package com.restapi.project.service;

import com.restapi.project.model.Skill;
import com.restapi.project.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public Skill findBySkillName(String name) {
        return skillRepository.findByName(name);
    }

    @Override
    public void saveSkill(Skill skill) {
        skillRepository.save(skill);
    }

}
