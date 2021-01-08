package edu.fudan.database.domain;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String staff;
    private Long patientId;
    private String patientName;
    private int messageType; // 1: new patient, 2: can discharge

    public Message() {

    }

    public Message(String staff, Long patientId, String patientName, int messageType) {
        this.staff = staff;
        this.patientId = patientId;
        this.patientName = patientName;
        this.messageType = messageType;
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
    public String getStaff() {
        return staff;
    }

    @Setter
    public void setStaff(String staff) {
        this.staff = staff;
    }

    @Getter
    public Long getPatientId() {
        return patientId;
    }

    @Setter
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Getter
    public String getPatientName() {
        return patientName;
    }

    @Setter
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Getter
    public int getMessageType() {
        return messageType;
    }

    @Setter
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
