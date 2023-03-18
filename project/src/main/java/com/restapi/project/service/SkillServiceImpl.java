package com.restapi.project.service;

import com.restapi.project.dto.SkillDto;
import com.restapi.project.model.Skill;
import com.restapi.project.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<SkillDto> getAllSkills() {
        return skillRepository
                .findAll()
                .stream()
                .map(skill -> new SkillDto(skill.getName())).collect(Collectors.toList());
    }
}
