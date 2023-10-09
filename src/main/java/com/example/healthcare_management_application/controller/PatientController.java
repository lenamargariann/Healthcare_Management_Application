package com.example.healthcare_management_application.controller;

import com.example.healthcare_management_application.model.Appointment;
import com.example.healthcare_management_application.model.Prescription;
import com.example.healthcare_management_application.service.PatientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller(value = "/patient")
public class PatientController {
    ObjectMapper objectMapper;
    PatientService patientService;

    {
        this.objectMapper = new ObjectMapper();
    }

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patient")
    public String main(@RequestParam(name = "patientId") String patientId, Model model) {
        model.addAttribute("patient", patientService.getPatientById(Long.parseLong(patientId)));
        return "patientPage";
    }

    //    @PostMapping("/")
//    public void searchPatient(@RequestParam String searching){
    //   }
    @PostMapping("/patient/addPrescription")
    public ResponseEntity<String> addPrescription(@RequestBody String body) {
        try {
            JsonNode json = objectMapper.readTree(body);
            Prescription prescription = new Prescription();
            Long patientId = json.get("patientId").asLong();
            String name = json.get("name").asText();
            int daysCount = json.get("daysCount").asInt();
            prescription.setPatientId(patientId);
            prescription.setName(name);
            prescription.setDaysCount(daysCount);
            patientService.savePrescription(prescription);
            return ResponseEntity.ok("Data saved successfully");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/patient/addAppointment")
    public ResponseEntity<String> addAppointment(@RequestBody String body) {
        try {
            JsonNode json = objectMapper.readTree(body);
            Appointment appointment = new Appointment();
            Long patientId = json.get("patientId").asLong();
            String date = json.get("date").asText();
            String doctor = json.get("doctor").asText();
            appointment.setPatientId(patientId);
            appointment.setDate(date);
            appointment.setDoctor(doctor);
            patientService.saveAppointment(appointment);
            return ResponseEntity.ok("Data saved successfully");

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
