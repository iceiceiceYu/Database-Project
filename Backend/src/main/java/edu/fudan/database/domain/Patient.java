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
    private String gender;
    private int age;
    private String level; // mild, severe, critical
    private boolean quarantined;
    private String section; // mild, severe, critical
    private String wardNurse;
    private String wardName;
    private int sickbed;
    private int status; // 1: 康复出院, 0: 在院治疗, -1: 病亡

    public Patient() {

    }

    public Patient(String name, String gender, int age, String level) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.level = level;
    }

    public Patient(String name, String gender, int age, String level, boolean quarantined) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.level = level;
        this.quarantined = quarantined;
    }

    public Patient(String name, String gender, int age, String level, boolean quarantined, String section, String wardNurse, String wardName, int sickbed, int status) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.level = level;
        this.quarantined = quarantined;
        this.section = section;
        this.wardNurse = wardNurse;
        this.wardName = wardName;
        this.sickbed = sickbed;
        this.status = status;
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
    public String getGender() {
        return gender;
    }

    @Setter
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Getter
    public int getAge() {
        return age;
    }

    @Setter
    public void setAge(int age) {
        this.age = age;
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
    public boolean isQuarantined() {
        return quarantined;
    }

    @Setter
    public void setQuarantined(boolean quarantined) {
        this.quarantined = quarantined;
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
    public String getWardName() {
        return wardName;
    }

    @Setter
    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    @Getter
    public int getSickbed() {
        return sickbed;
    }

    @Setter
    public void setSickbed(int sickBed) {
        this.sickbed = sickBed;
    }

    @Getter
    public int getStatus() {
        return status;
    }

    @Setter
    public void setStatus(int status) {
        this.status = status;
    }
}
