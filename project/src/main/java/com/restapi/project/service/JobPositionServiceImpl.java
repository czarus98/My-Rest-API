package com.restapi.project.service;

import com.restapi.project.dto.CandidateDto;
import com.restapi.project.dto.JobPositionDto;
import com.restapi.project.dto.SkillDto;
import com.restapi.project.model.Candidate;
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

    @Autowired
    private CandidateService candidateService;

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
            relateSkillsWithJobPosition(newPosition);
        }
    }

    @Override
    public void removePosition(Long id) {
        JobPosition jobPosition = jobPositionRepository.findByJobPositionId(id);
        if (jobPosition != null) {
            List<SkillDto> skillsDto = skillService.getAllSkills();
            List<CandidateDto> candidateDtos = candidateService.getAllCandidates();
            Skill skill;
            Candidate candidate;
            for (SkillDto skillDto : skillsDto) {
                skill = skillService.findBySkillName(skillDto.getName());
                skill.getSkillsNeededForJob().remove(jobPosition);
            }
            for (CandidateDto candidateDto : candidateDtos) {
                candidate = candidateService.findByEmail(candidateDto.getEmail());
                candidate.getJobPositions().remove(jobPosition);
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

    @Override
    public void patchPosition(JobPositionDto jobPositionDto, Long id) {
        JobPosition existingJobPosition = jobPositionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobPosition not found"));
        if (jobPositionDto.getTitle() != null) {
            existingJobPosition.setTitle(jobPositionDto.getTitle());
        }
        if (jobPositionDto.getDescription() != null) {
            existingJobPosition.setDescription(jobPositionDto.getDescription());
        }

        if (jobPositionDto.getSkills() != null && !jobPositionDto.getSkills().isEmpty()) {
            Set<SkillDto> skillDtos = jobPositionDto.getSkills();
            Set<Skill> skills = existingJobPosition.getSkills();
            for (SkillDto skillDto : skillDtos) {
                Skill skillToModify = skillService.findBySkillName(skillDto.getName());
                if (skillToModify != null) {
                    if (!skills.contains(skillToModify)) {
                        skillToModify.getSkillsNeededForJob().add(existingJobPosition);
                    }
                    skillService.saveSkill(skillToModify);
                } else {
                    Skill newSkill = new Skill(skillDto.getName());
                    if (!skills.contains(newSkill)) {
                        newSkill.getSkillsNeededForJob().add(existingJobPosition);
                    }
                    skillService.saveSkill(newSkill);
                }
            }
        }

        if (jobPositionDto.getCandidates() != null && !jobPositionDto.getCandidates().isEmpty()) {
            List<CandidateDto> candidateDtos = jobPositionDto.getCandidates();
            List<Candidate> candidates = existingJobPosition.getCandidates();
            for (CandidateDto candidate : candidateDtos) {
                Candidate candidateToModify = candidateService.findByEmail(candidate.getEmail());
                if (candidateToModify != null) {
                    if (!candidates.contains(candidateToModify)) {
                        candidateToModify.getJobPositions().add(existingJobPosition);
                    }
                    candidateService.saveCandidate(candidateToModify);
                } else {
                    Candidate newCandidate = new Candidate(candidate.getFirstname(), candidate.getLastname(), candidate.getJobTitle(), candidate.getEmail());
                    if (!candidates.contains(newCandidate)) {
                        newCandidate.getJobPositions().add(existingJobPosition);
                    }
                    candidateService.saveCandidate(newCandidate);
                }
            }
        }

        jobPositionRepository.save(existingJobPosition);
    }

    private void relateSkillsWithJobPosition(JobPosition existingJobPosition) {
        Set<Skill> skillList = existingJobPosition.getSkills();
        List<Skill> skillsToAdd = new ArrayList<>(skillList);
        for (Skill skill : skillsToAdd) {
            System.out.println("jestem w petli");
            Skill skillToAdd = skillService.findBySkillName(skill.getName());
            if (skillToAdd != null) {
                skillToAdd.getSkillsNeededForJob().add(existingJobPosition);
                skillService.saveSkill(skillToAdd);
            } else {
                Skill newSkill = new Skill(skill.getName());
                newSkill.getSkillsNeededForJob().add(existingJobPosition);
                skillService.saveSkill(newSkill);
            }
        }
    }
}
