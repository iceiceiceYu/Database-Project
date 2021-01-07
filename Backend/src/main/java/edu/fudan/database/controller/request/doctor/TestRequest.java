package edu.fudan.database.controller.request.doctor;

public class TestRequest {
    private Long patientId;
    private String patientName;
    private boolean positive; // true - positive, false - negative
    private String level; // mild, severe, critical
    private String date;
    private String doctor;

    public TestRequest() {

    }

    public TestRequest(Long patientId, String patientName, boolean positive, String level, String date, String doctor) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.positive = positive;
        this.level = level;
        this.date = date;
        this.doctor = doctor;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
