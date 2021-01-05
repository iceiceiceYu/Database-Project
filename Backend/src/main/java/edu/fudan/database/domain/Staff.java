package edu.fudan.database.domain;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String type; // doctor, chief nurse, hospital nurse, emergency nurse
    private String section;

    public Staff() {

    }

    public Staff(String name, String username, String password, String type, String section) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.type = type;
        this.section = section;
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
    public String getName() {
        return name;
    }

    @Setter
    public void setName(String name) {
        this.name = name;
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
    public String getType() {
        return type;
    }

    @Setter
    public void setType(String type) {
        this.type = type;
    }
}
