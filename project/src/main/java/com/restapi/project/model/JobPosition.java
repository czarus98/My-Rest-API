package com.restapi.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    private List<Candidate> candidates;

    @ManyToMany(mappedBy = "skillsNeededForJob")
    private Set<Skill> skills = new HashSet<>();

    public JobPosition(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public JobPosition(String title, String description, Set<Skill> skills) {
        this.title = title;
        this.description = description;
        this.skills = skills;
    }
}
