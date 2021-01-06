package edu.fudan.database.controller.request;

public class SelectRequest {
    private String username;
    private String type;
    private String value;

    public SelectRequest() {

    }

    public SelectRequest(String username, String type, String value) {
        this.username = username;
        this.type = type;
        this.value = value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
