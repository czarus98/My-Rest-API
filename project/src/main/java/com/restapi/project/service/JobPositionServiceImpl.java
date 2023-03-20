package com.restapi.project.service;

import com.restapi.project.dto.CandidateDto;
import com.restapi.project.dto.JobPositionDto;
import com.restapi.project.dto.SkillDto;
import com.restapi.project.model.JobPosition;
import com.restapi.project.model.Skill;
import com.restapi.project.repository.JobPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobPositionServiceImpl implements JobPositionService {

    @Autowired
    private JobPositionRepository jobPositionRepository;

    @Autowired
    private SkillService skillService;

    @Override
    public List<JobPositionDto> getAllJobs() {
        return jobPositionRepository.findAll()
                .stream()
                .map(jobPosition -> new JobPositionDto(jobPosition.getTitle(), jobPosition.getJobPositionId(), jobPosition.getSkills().stream().map(skill -> new SkillDto(skill.getSkillId(), skill.getName())).collect(Collectors.toSet()), jobPosition.getDescription(),
                        jobPosition.getCandidates().stream().map(candidate -> new CandidateDto(candidate.getFirstname(), candidate.getLastname(), candidate.getEmail(), candidate.getJobTitle())).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public JobPositionDto getPosition(Long id) {
        JobPosition jobPosition = jobPositionRepository.findByJobPositionId(id);
        if (jobPosition != null) {
            return new JobPositionDto(jobPosition.getTitle(), jobPosition.getJobPositionId(), jobPosition.getSkills().stream().map(skill -> new SkillDto(skill.getSkillId(), skill.getName())).collect(Collectors.toSet()), jobPosition.getDescription(), jobPosition.getCandidates().stream().map(candidate -> new CandidateDto(candidate.getFirstname(), candidate.getLastname(), candidate.getEmail(), candidate.getJobTitle())).collect(Collectors.toList()));
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public void createPosition(JobPositionDto jobPositionDto) throws Exception {
        if (jobPositionDto.getSkills() == null) {
            JobPosition newPosition = new JobPosition(jobPositionDto.getTitle(), jobPositionDto.getDescription());
            try {
                jobPositionRepository.save(newPosition);
            } catch (Exception exception) {
                throw new Exception("Error while saving job position information: " + exception.getMessage());
            }
        } else {
            JobPosition newPosition = new JobPosition(jobPositionDto.getTitle(), jobPositionDto.getDescription(),
                    jobPositionDto.getSkills().stream().map(skillDto -> new Skill(skillDto.getName())).collect(Collectors.toSet()));
            try {
                jobPositionRepository.save(newPosition);
            } catch (Exception exception) {
                throw new Exception("Error while saving job position information: " + exception.getMessage());
            }
            Set<Skill> skillList = newPosition.getSkills();
            List<Skill> skillsToAdd = new ArrayList<>(skillList);
            for (Skill skill : skillsToAdd) {
                Skill skillToAdd = skillService.findBySkillName(skill.getName());
                if (skillToAdd != null) {
                    skillToAdd.getSkillsNeededForJob().add(newPosition);
                    skillService.saveSkill(skillToAdd);
                } else {
                    Skill newSkill = new Skill(skill.getName());
                    newSkill.getSkillsNeededForJob().add(newPosition);
                    skillService.saveSkill(newSkill);
                }
            }
        }
    }

    @Override
    public void removePosition(Long id) {
        JobPosition jobPosition = jobPositionRepository.findByJobPositionId(id);
        if (jobPosition != null) {
            List<SkillDto> skillsDto = skillService.getAllSkills();
            Skill skill;
            for (SkillDto skillDto : skillsDto) {
                skill = skillService.findBySkillName(skillDto.getName());
                skill.getSkillsNeededForJob().remove(jobPosition);
            }
            jobPositionRepository.delete(jobPosition);
        }
    }

    @Override
    public void replacePosition(JobPositionDto jobPositionDto, Long id) throws Exception {
        JobPosition jobPosition = jobPositionRepository.findByJobPositionId(id);
        if (jobPosition != null) {
            jobPosition.setTitle(jobPositionDto.getTitle());
            jobPosition.setDescription(jobPositionDto.getDescription());
            jobPositionRepository.save(jobPosition);
        } else {
            createPosition(jobPositionDto);
        }
    }
}
