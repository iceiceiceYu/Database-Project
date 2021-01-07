package edu.fudan.database.domain;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DailyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long patientId;
    private String patientName;
    private double temperature;
    private String symptom;
    private boolean positive; // true - positive, false - negative
    private String level; // mild, severe, critical
    private String date;
    private String wardNurse;

    public DailyInfo() {

    }

    public DailyInfo(Long patientId, String patientName, double temperature, String symptom, boolean positive, String level, String date, String wardNurse) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.temperature = temperature;
        this.symptom = symptom;
        this.positive = positive;
        this.level = level;
        this.date = date;
        this.wardNurse = wardNurse;
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
    public double getTemperature() {
        return temperature;
    }

    @Setter
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Getter
    public String getSymptom() {
        return symptom;
    }

    @Setter
    public void setSymptom(String symptom) {
        this.symptom = symptom;
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
    public String getWardNurse() {
        return wardNurse;
    }

    @Setter
    public void setWardNurse(String wardNurse) {
        this.wardNurse = wardNurse;
    }
}

