package edu.fudan.database.domain;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

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

    @ElementCollection
    private Set<String> ward;

    public Section() {

    }

    public Section(String level, String doctor, String chiefNurse, Set<String> wardNurse, Set<String> ward) {
        this.level = level;
        this.doctor = doctor;
        this.chiefNurse = chiefNurse;
        this.wardNurse = wardNurse;
        this.ward = ward;
    }

    @Getter
    public Long getId() {
        return id;
    }

    @Setter
    public void setId(Long id) {
        this.id = id;
    }

    @Getter
    public String getLevel() {
        return level;
    }

    @Setter
    public void setLevel(String level) {
        this.level = level;
    }

    @Getter
    public String getDoctor() {
        return doctor;
    }

    @Setter
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    @Getter
    public String getChiefNurse() {
        return chiefNurse;
    }

    @Setter
    public void setChiefNurse(String chiefNurse) {
        this.chiefNurse = chiefNurse;
    }

    @Getter
    public Set<String> getWardNurse() {
        return wardNurse;
    }

    @Setter
    public void setWardNurse(Set<String> wardNurse) {
        this.wardNurse = wardNurse;
    }

    @Getter
    public Set<String> getWard() {
        return ward;
    }

    @Setter
    public void setWard(Set<String> ward) {
        this.ward = ward;
    }
}
