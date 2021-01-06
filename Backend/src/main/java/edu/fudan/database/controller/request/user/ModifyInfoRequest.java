package edu.fudan.database.controller.request.user;

public class ModifyInfoRequest {
    private String username;
    private String birth;
    private String name;

    public ModifyInfoRequest() {
    }

    public ModifyInfoRequest(String username, String birth, String name) {
        this.username = username;
        this.birth = birth;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
