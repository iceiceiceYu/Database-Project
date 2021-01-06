package edu.fudan.database.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String level; // mild, severe, critical
    private String doctor;
    private String chiefNurse;

    @ElementCollection
    private Set<String> wardNurse;

    public Section() {

    }

    public Section(String level, String doctor, String chiefNurse, Set<String> wardNurse) {
        this.level = level;
        this.doctor = doctor;
        this.chiefNurse = chiefNurse;
        this.wardNurse = wardNurse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getChiefNurse() {
        return chiefNurse;
    }

    public void setChiefNurse(String chiefNurse) {
        this.chiefNurse = chiefNurse;
    }

    public Set<String> getWardNurse() {
        return wardNurse;
    }

    public void setWardNurse(Set<String> wardNurse) {
        this.wardNurse = wardNurse;
    }
}
