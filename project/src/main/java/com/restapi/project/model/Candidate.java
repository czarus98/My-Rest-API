package com.restapi.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "candidates", schema = "public")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "candidate_id")
    private Long candidateId;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, name = "job_title")
    private String jobTitle;

    @Column(nullable = false, name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "candidates_jobs",
            joinColumns = {@JoinColumn(name = "candidate_id")},
            inverseJoinColumns = {@JoinColumn(name = "job_id")})
    private List<JobPosition> jobPositions = new ArrayList<>();

    @ManyToMany(mappedBy = "candidatesSkills")
    private List<Skill> skills;

    public Candidate(String firstname, String lastname, String jobTitle, String email, List<Skill> skills) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.jobTitle = jobTitle;
        this.email = email;
        this.skills = skills;
    }

    public Candidate(String firstname, String lastname, String jobTitle, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.jobTitle = jobTitle;
        this.email = email;
    }
}
