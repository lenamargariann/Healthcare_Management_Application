package com.example.healthcare_management_application.controller;

import com.example.healthcare_management_application.model.Patient;
import com.example.healthcare_management_application.model.Prescription;
import com.example.healthcare_management_application.service.PatientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller(value = "/")
public class HomeController {
    private final PatientService patientService;
    ObjectMapper objectMapper;

    {
        this.objectMapper = new ObjectMapper();
    }

    public HomeController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("patients", patientService.listPatients());
        return "startPage";
    }

    //    @PostMapping("/search")
//    public void searchPatient(@RequestBody String userInput) {
//        List<Patient> updatedPatients = patientService.findPatients(userInput.substring(5));
//        try {
//            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
//            engine.put("updatedPatients", updatedPatients);
//            engine.eval(new FileReader("src/main/resources/templates/js/UpdateList.js"));
//            engine.eval("updatePatientList(updatedPatients)");
//        } catch (ScriptException e) {
//            throw new RuntimeException(e);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
////        return "startPage";
//
//    }
    @PostMapping("/")
    public ResponseEntity<Object> savePatient(@RequestBody String body) {
        try {
            JsonNode json = objectMapper.readTree(body);
            Patient patient = new Patient();
            String firstname = json.get("firstname").asText();
            String lastname = json.get("lastname").asText();
            String birthdate = json.get("birthdate").asText();
            String gender = json.get("gender").asText();
            patient.setFirstName(firstname);
            patient.setLastName(lastname);
            patient.setDateOfBirth(birthdate);
            patient.setGender(gender);
            long id = patientService.savePatientData(patient);
            Map<String, Object> data = new HashMap<>();
            data.put("patientId", id);

            // Return the data as JSON
            return ResponseEntity.ok(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
