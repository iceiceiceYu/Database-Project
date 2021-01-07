package edu.fudan.database.controller.request.chiefNurse;

public class ModifyRequest {
    private String chiefNurse;
    private String nurseName;

    public ModifyRequest() {

    }

    public ModifyRequest(String chiefNurse, String nurseName) {
        this.chiefNurse = chiefNurse;
        this.nurseName = nurseName;
    }

    public String getChiefNurse() {
        return chiefNurse;
    }

    public void setChiefNurse(String chiefNurse) {
        this.chiefNurse = chiefNurse;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }
}
