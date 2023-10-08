package com.example.healthcare_management_application.model;


public class Appointment {
    private Long id;
    private Long patientId;
    private String date;
    private String doctor;

    public Appointment() {
        // Default constructor
    }

    public Appointment(Long id, Long patientId, String date, String doctor) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.doctor = doctor;
    }

    // Getters and setters for all fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}

