package com.example.healthcare_management_application.service;

import com.example.healthcare_management_application.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;


import com.example.healthcare_management_application.model.Appointment;
import com.example.healthcare_management_application.model.Prescription;
import com.example.healthcare_management_application.repository.PatientRepository;
import com.example.healthcare_management_application.repository.AppointmentRepository;
import com.example.healthcare_management_application.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    public PatientService(
            PatientRepository patientRepository,
            AppointmentRepository appointmentRepository,
            PrescriptionRepository prescriptionRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    @Transactional
    public long savePatientData(Patient patient) {
        return patientRepository.savePatient(patient);
    }

    @Transactional
    public void savePrescription(Prescription prescription) {
        prescriptionRepository.savePrescription(prescription);
    }

    @Transactional
    public void saveAppointment(Appointment appointment) {
        appointmentRepository.saveAppointment(appointment);
    }

    @Transactional
    public Patient getPatientById(Long id) {
        Patient patient = patientRepository.getPatientById(id);
        patient.setAppointments(appointmentRepository.getAppointmentsByPatientId(id));
        patient.setPrescriptions(prescriptionRepository.getPrescriptionsByPatientId(id));
        return patient;
    }

    public List<Patient> listPatients() {
        return patientRepository.getPatients();
    }

    public List<Patient> findPatients(String key) {
        return patientRepository.findPatients(key);
    }

    public List<Appointment> getPatientAppointments(Long id) {
        return appointmentRepository.getAppointmentsByPatientId(id);
    }

    public List<Prescription> getPatientPrescriptions(Long id) {
        return prescriptionRepository.getPrescriptionsByPatientId(id);
    }

}

