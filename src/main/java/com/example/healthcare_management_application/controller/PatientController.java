package com.example.healthcare_management_application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Controller(value = "/patient")
public class PatientController {
    @GetMapping("/")
    public String main(Model model) {
        return "patientPage";
    }
    @PostMapping("/")
    public void searchPatient(@RequestParam String searching){

    }


}
