package com.restapi.project.repository;

import com.restapi.project.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Candidate findByCandidateId(Long id);

    boolean existsByEmailEquals(String email);

    Candidate findByEmail(String email);
}
