package edu.fudan.database.controller.request.emergencyNurse;

public class PatientRequest {
    private String name;
    private String gender;
    private int age;
    private String level; // mild, severe, critical

    public PatientRequest() {

    }

    public PatientRequest(String name, String gender, int age, String level) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
