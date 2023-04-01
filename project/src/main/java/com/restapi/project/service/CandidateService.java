package com.restapi.project.service;

import com.restapi.project.dto.CandidateDto;
import com.restapi.project.model.Candidate;

import java.util.List;

public interface CandidateService {
    List<CandidateDto> getAllCandidates();

    CandidateDto getCandidate(Long id);

    void createCandidate(CandidateDto candidateDto) throws Exception;

    void removeCandidate(Long id);

    void putCandidate(CandidateDto newCandidate, Long id) throws Exception;

    Candidate findByEmail(String email);

    void saveCandidate(Candidate candidate);
}
