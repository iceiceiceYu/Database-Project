package edu.fudan.database.controller.request.doctor;

public class ModifyLevelRequest {
    private Long patientId;
    private String newLevel;

    public ModifyLevelRequest() {

    }

    public ModifyLevelRequest(Long patientId, String newLevel) {
        this.patientId = patientId;
        this.newLevel = newLevel;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getNewLevel() {
        return newLevel;
    }

    public void setNewLevel(String newLevel) {
        this.newLevel = newLevel;
    }
}
