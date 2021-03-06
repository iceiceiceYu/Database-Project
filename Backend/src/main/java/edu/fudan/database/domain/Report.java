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
    private Long patientId;
    private String patientName;
    private boolean positive; // true - positive, false - negative
    private String level; // mild, severe, critical
    private String date;
    private String doctor;

    public Report() {

    }

    public Report(Long patientId, String patientName, boolean positive, String level, String date, String doctor) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.positive = positive;
        this.level = level;
        this.date = date;
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
    public Long getPatientId() {
        return patientId;
    }

    @Setter
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Getter
    public String getPatientName() {
        return patientName;
    }

    @Setter
    public void setPatientName(String patientName) {
        this.patientName = patientName;
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
    public String getDoctor() {
        return doctor;
    }

    @Setter
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
