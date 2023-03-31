package com.restapi.project.repository;

import com.restapi.project.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Skill findByName(String name);

    boolean existsByName(String name);
}
