package edu.fudan.database.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String level; // mild, severe, critical
    private int capacity;
    private String doctor;
    private String chiefNurse;
    @ElementCollection
    private Set<String> hospitalNurse;
}
