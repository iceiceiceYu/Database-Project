package edu.fudan.database.controller.request.WardNurse;

public class DailyInfoRequest {
    private Long patientId;
    private String patientName;
    private double temperature;
    private String symptom;
    private boolean positive; // true - positive, false - negative
    private String date;
    private String wardNurse;

    public DailyInfoRequest() {

    }

    public DailyInfoRequest(Long patientId, String patientName, double temperature, String symptom, boolean positive, String date, String wardNurse) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.temperature = temperature;
        this.symptom = symptom;
        this.positive = positive;
        this.date = date;
        this.wardNurse = wardNurse;
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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWardNurse() {
        return wardNurse;
    }

    public void setWardNurse(String wardNurse) {
        this.wardNurse = wardNurse;
    }
}
