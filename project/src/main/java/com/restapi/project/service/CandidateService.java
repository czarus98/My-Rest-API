package com.restapi.project.service;

import com.restapi.project.dto.CandidateDto;

import java.util.List;

public interface CandidateService {
    List<CandidateDto> getAllCandidates();

    CandidateDto getCandidate(Long id);

    void createCandidate(CandidateDto candidateDto) throws Exception;
}
