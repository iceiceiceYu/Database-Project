package edu.fudan.database.domain;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.util.List;
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
    private List<String> wardNurses;

    @ElementCollection
    private List<String> wards;

    public Section() {

    }

    public Section(String level, String doctor, String chiefNurse, List<String> wardNurses, List<String> wards) {
        this.level = level;
        this.doctor = doctor;
        this.chiefNurse = chiefNurse;
        this.wardNurses = wardNurses;
        this.wards = wards;
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
    public List<String> getWardNurses() {
        return wardNurses;
    }

    @Setter
    public void setWardNurses(List<String> wardNurse) {
        this.wardNurses = wardNurse;
    }

    @Getter
    public List<String> getWards() {
        return wards;
    }

    @Setter
    public void setWards(List<String> ward) {
        this.wards = ward;
    }
}
