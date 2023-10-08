package com.example.healthcare_management_application.model;


public class Prescription {
    private Long prescriptionId;
    private Long patientId;
    private String name;
    private int daysCount;

    public Prescription() {
        // Default constructor
    }

    public Prescription(Long prescriptionId, Long patientId, String name, int daysCount) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.name = name;
        this.daysCount = daysCount;
    }

    // Getters and setters for all fields

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }
}

