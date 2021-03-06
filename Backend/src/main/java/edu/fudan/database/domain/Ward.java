package edu.fudan.database.domain;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String level; // mild, severe, critical

    @Column(unique = true)
    private String name;
    private int capacity;

    @ElementCollection
    private List<String> patients;

    @ElementCollection
    private List<Integer> sickbeds;

    public Ward() {

    }

    public Ward(String level, String name, int capacity, List<String> patients, List<Integer> sickbeds) {
        this.level = level;
        this.name = name;
        this.capacity = capacity;
        this.patients = patients;
        this.sickbeds = sickbeds;
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
    public String getLevel() {
        return level;
    }

    @Setter
    public void setLevel(String level) {
        this.level = level;
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
    public int getCapacity() {
        return capacity;
    }

    @Setter
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Getter
    public List<String> getPatients() {
        return patients;
    }

    @Setter
    public void setPatients(List<String> patients) {
        this.patients = patients;
    }

    @Getter
    public List<Integer> getSickbeds() {
        return sickbeds;
    }

    @Setter
    public void setSickbeds(List<Integer> sickBeds) {
        this.sickbeds = sickBeds;
    }
}
