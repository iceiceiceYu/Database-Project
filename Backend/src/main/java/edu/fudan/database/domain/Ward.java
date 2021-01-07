package edu.fudan.database.domain;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String level; // mild, severe, critical
    private String name;
    private int capacity;

    @ElementCollection
    private Set<String> patients;

    @ElementCollection
    private Set<Integer> sickBeds;

    public Ward() {

    }

    public Ward(String level, String name, int capacity, Set<String> patients, Set<Integer> sickBeds) {
        this.level = level;
        this.name = name;
        this.capacity = capacity;
        this.patients = patients;
        this.sickBeds = sickBeds;
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
    public Set<String> getPatients() {
        return patients;
    }

    @Setter
    public void setPatients(Set<String> patients) {
        this.patients = patients;
    }

    @Getter
    public Set<Integer> getSickBeds() {
        return sickBeds;
    }

    @Setter
    public void setSickBeds(Set<Integer> sickBeds) {
        this.sickBeds = sickBeds;
    }
}
