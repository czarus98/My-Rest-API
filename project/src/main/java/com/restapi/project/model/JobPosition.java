package com.restapi.project.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "job_position", schema = "public")
public class JobPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "job_position_id")
    private Long jobPositionId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "jobPositions")
    @Getter(AccessLevel.NONE)
    private List<Candidate> candidates = new ArrayList<>();

    @ManyToMany(mappedBy = "skillsNeededForJob")
    @Getter(AccessLevel.NONE)
    private List<Skill> skills = new ArrayList<>();
}
