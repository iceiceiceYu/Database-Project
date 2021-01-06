package edu.fudan.database.domain;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String patient;
    private boolean positive; // true - positive, false - negative
    private String level; // mild, severe, critical
    private String date;
    private String time;
    private String doctor;

    public Report() {

    }

    public Report(String patient, boolean positive, String level, String date, String time, String doctor) {
        this.patient = patient;
        this.positive = positive;
        this.level = level;
        this.date = date;
        this.time = time;
        this.doctor = doctor;
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
    public String getPatient() {
        return patient;
    }

    @Setter
    public void setPatient(String patient) {
        this.patient = patient;
    }

    @Getter
    public boolean isPositive() {
        return positive;
    }

    @Setter
    public void setPositive(boolean positive) {
        this.positive = positive;
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
    public String getDate() {
        return date;
    }

    @Setter
    public void setDate(String date) {
        this.date = date;
    }

    @Getter
    public String getTime() {
        return time;
    }

    @Setter
    public void setTime(String time) {
        this.time = time;
    }

    @Getter
    public String getDoctor() {
        return doctor;
    }

    @Setter
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
