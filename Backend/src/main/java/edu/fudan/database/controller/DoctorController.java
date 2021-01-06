package edu.fudan.database.controller;

import edu.fudan.database.domain.Patient;
import edu.fudan.database.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class DoctorController {
    private DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/doctor/getPatientInfo")
    public ResponseEntity<List<Patient>> getPatientInfo(@RequestBody String doctorUsername) {
        return ResponseEntity.ok(doctorService.getPatientInfo(doctorUsername));
    }
}
