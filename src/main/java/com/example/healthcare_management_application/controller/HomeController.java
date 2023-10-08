package com.example.healthcare_management_application.controller;

import com.example.healthcare_management_application.model.Patient;
import com.example.healthcare_management_application.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Controller
public class HomeController {
    private final PatientService patientService;

    public HomeController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("patients", patientService.listPatients());
        return "startPage";
    }

    @PostMapping("/search")
    public void searchPatient(@RequestBody String userInput) {
        List<Patient> updatedPatients = patientService.findPatients(userInput.substring(5));
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
            engine.put("updatedPatients", updatedPatients);
            engine.eval(new FileReader("src/main/resources/templates/js/UpdateList.js"));
            engine.eval("updatePatientList(updatedPatients)");
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
//        return "startPage";

    }

}
