package com.restapi.project.service;

import com.restapi.project.dto.JobPositionDto;

import java.util.List;

public interface JobPositionService {
    List<JobPositionDto> getAllJobs();

    JobPositionDto getPosition(Long id);

    void createPosition(JobPositionDto jobPositionDto) throws Exception;

    void removePosition(Long id);

    void replacePosition(JobPositionDto jobPositionDto, Long id) throws Exception;

    void patchPosition(JobPositionDto jobPositionDto, Long id);
}
