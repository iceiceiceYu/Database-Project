package edu.fudan.database.controller.request;

public class ModifyRequest {
    private String username;
    private String password;
    private String birth;
    private String name;

    public ModifyRequest() {
    }

    public ModifyRequest(String username, String password, String birth, String name) {
        this.username = username;
        this.password = password;
        this.birth = birth;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
