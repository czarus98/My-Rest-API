package com.restapi.project.service;

import com.restapi.project.dto.CandidateDto;
import com.restapi.project.dto.JobPositionDto;
import com.restapi.project.dto.SkillDto;
import com.restapi.project.exception.ResourceAlreadyExistsError;
import com.restapi.project.model.Candidate;
import com.restapi.project.model.Skill;
import com.restapi.project.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private SkillService skillService;

    @Override
    public List<CandidateDto> getAllCandidates() {
        return candidateRepository
                .findAll()
                .stream()
                .map(candidate -> new CandidateDto(
                        candidate.getFirstname(), candidate.getLastname(), candidate.getJobTitle(), candidate.getCandidateId(),
                        candidate.getSkills().stream().map(skill -> new SkillDto(skill.getName())).collect(Collectors.toList()), candidate.getEmail(),
                        candidate.getJobPositions().stream().map(jobPosition -> new JobPositionDto(jobPosition.getTitle())).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public CandidateDto getCandidate(Long id) {
        Candidate candidate = candidateRepository.findByCandidateId(id);
        if (candidate != null) {
            return new CandidateDto(candidate.getFirstname(), candidate.getLastname(), candidate.getJobTitle(), candidate.getCandidateId(),
                    candidate.getSkills().stream().map(skill -> new SkillDto(skill.getName())).collect(Collectors.toList()), candidate.getEmail());
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public void createCandidate(CandidateDto candidateDto) throws Exception {
        if (candidateRepository.existsByEmailEquals(candidateDto.getEmail())) {
            throw new ResourceAlreadyExistsError("Candidate with " + candidateDto.getEmail() + " email already exists");
        }
        if (candidateDto.getSkills() == null) {
            Candidate newCandidate = new Candidate(candidateDto.getFirstname(), candidateDto.getLastname(), candidateDto.getJobTitle(),
                    candidateDto.getEmail());
            try {
                candidateRepository.save(newCandidate);
            } catch (Exception exception) {
                throw new Exception("Error while saving candidate information: " + exception.getMessage());
            }
        } else {
            Candidate newCandidate = new Candidate(candidateDto.getFirstname(), candidateDto.getLastname(), candidateDto.getJobTitle(),
                    candidateDto.getEmail(), candidateDto.getSkills().stream().map(skillDto -> new Skill(skillDto.getName())).collect(Collectors.toList()));
            try {
                candidateRepository.save(newCandidate);
            } catch (Exception exception) {
                throw new Exception("Error while saving candidate information: " + exception.getMessage());
            }

            for (Skill skill : newCandidate.getSkills()) {
                Skill skillToAdd = skillService.findBySkillName(skill.getName());
                if (skillToAdd != null) {
                    skillToAdd.getCandidatesSkills().add(newCandidate);
                    skillService.saveSkill(skillToAdd);
                } else {
                    Skill newSkill = new Skill(skill.getName());
                    newSkill.getCandidatesSkills().add(newCandidate);
                    skillService.saveSkill(newSkill);
                }
            }
        }
    }
}
