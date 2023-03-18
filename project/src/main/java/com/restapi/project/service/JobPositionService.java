package com.restapi.project.service;

import com.restapi.project.dto.JobPositionDto;

import java.util.List;

public interface JobPositionService {
    List<JobPositionDto> getAllJobs();

    JobPositionDto getPosition(Long id);

    void createPosition(JobPositionDto jobPositionDto) throws Exception;
}