//package edu.fudan.database.domain;
//
//import jdk.nashorn.internal.objects.annotations.Getter;
//import jdk.nashorn.internal.objects.annotations.Setter;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//@Entity
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private String username;
//    private String password;
//    private String fullname;
//    private String gender; // male, female
//    private String birth;
//
//    public User() {
//
//    }
//
//    public User(String username, String password, String fullname, String gender, String birth) {
//        this.username = username;
//        this.password = password;
//        this.fullname = fullname;
//        this.gender = gender;
//        this.birth = birth;
//    }
//
//    @Getter
//    public String getUsername() {
//        return username;
//    }
//
//    @Setter
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    @Getter
//    public String getPassword() {
//        return password;
//    }
//
//    @Setter
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @Getter
//    public String getFullname() {
//        return fullname;
//    }
//
//    @Setter
//    public void setFullname(String fullname) {
//        this.fullname = fullname;
//    }
//
//    @Getter
//    public String getGender() {
//        return gender;
//    }
//
//    @Setter
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    @Getter
//    public String getBirth() {
//        return birth;
//    }
//
//    @Setter
//    public void setBirth(String birth) {
//        this.birth = birth;
//    }
//}
