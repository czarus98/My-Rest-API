package com.restapi.project.service;

import com.restapi.project.dto.SkillDto;
import com.restapi.project.exception.ResourceAlreadyExistsError;
import com.restapi.project.model.Skill;
import com.restapi.project.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                .map(skill -> new SkillDto(skill.getSkillId(), skill.getName())).collect(Collectors.toList());
    }

    @Override
    public SkillDto getSkill(Long id) throws ResourceNotFoundException {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
        return new SkillDto(skill.getSkillId(), skill.getName());
    }

    @Override
    public void createSkill(SkillDto skillDto) throws Exception {
        if (skillRepository.existsByName(skillDto.getName())) {
            throw new ResourceAlreadyExistsError("Skill " + skillDto.getName() + " already exists");
        }
        Skill newSkill = new Skill(skillDto.getName());
        try {
            skillRepository.save(newSkill);
        } catch (Exception exception) {
            throw new Exception("Error while saving skill information: " + exception.getMessage());
        }
    }

    @Override
    public void removeSkill(Long id) throws ResourceNotFoundException {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));
        try {
            skillRepository.delete(skill);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting skill", e);
        }
    }

    @Override
    public void putSkill(SkillDto skillDto, Long id) throws Exception {
        Optional<Skill> skill = skillRepository.findById(id);
        if (skill.isPresent()) {
            if (skillRepository.existsByName(skillDto.getName())) {
                throw new ResourceAlreadyExistsError("Skill " + skillDto.getName() + " already exists");
            }
            skill.get().setName(skillDto.getName());
            skillRepository.save(skill.get());
        } else {
            createSkill(skillDto);
        }
    }
}
