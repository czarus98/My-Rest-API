package com.restapi.project.repository;

import com.restapi.project.model.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
    JobPosition findByJobPositionId(Long id);
}
