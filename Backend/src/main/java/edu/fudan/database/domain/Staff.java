package edu.fudan.database.domain;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;

@Entity
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String birth;

    private String name;
    private String type; // doctor, chief nurse, hospital nurse, emergency nurse
    private String section;


    public Staff() {

    }

    public Staff(String username, String password, String birth,
                 String name, String type, String section) {
        this.username = username;
        this.password = password;
        this.birth = birth;
        this.name = name;
        this.type = type;
        //this.section = section;
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
    public String getSection() {
        return section;
    }

    @Setter
    public void setSection(String section) {
        this.section = section;
    }

    @Getter
    public String getUsername() {
        return username;
    }

    @Setter
    public void setUsername(String username) {
        this.username = username;
    }

    @Getter
    public String getPassword() {
        return password;
    }

    @Setter
    public void setPassword(String password) {
        this.password = password;
    }

    @Getter
    public String getBirth() {
        return birth;
    }

    @Setter
    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Getter
    public String getName() {
        return name;
    }

    @Setter
    public void setName(String name) {
        this.name = name;
    }

    @Getter
    public String getType() {
        return type;
    }

    @Setter
    public void setType(String type) {
        this.type = type;
    }
}
