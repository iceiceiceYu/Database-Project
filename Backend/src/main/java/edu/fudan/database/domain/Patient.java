package edu.fudan.database.domain;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String level; // mild, severe, critical
    private String section; // mild, severe, critical
    private String wardNurse;
    private int sickBed;

    public Patient() {

    }

    public Patient(String name, String level, String section, String wardNurse, int sickBed) {
        this.name = name;
        this.level = level;
        this.section = section;
        this.wardNurse = wardNurse;
        this.sickBed = sickBed;
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
    public String getName() {
        return name;
    }

    @Setter
    public void setName(String name) {
        this.name = name;
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
    public String getSection() {
        return section;
    }

    @Setter
    public void setSection(String section) {
        this.section = section;
    }

    @Getter
    public String getWardNurse() {
        return wardNurse;
    }

    @Setter
    public void setWardNurse(String wardNurse) {
        this.wardNurse = wardNurse;
    }

    @Getter
    public int getSickBed() {
        return sickBed;
    }

    @Setter
    public void setSickBed(int sickBed) {
        this.sickBed = sickBed;
    }
}
