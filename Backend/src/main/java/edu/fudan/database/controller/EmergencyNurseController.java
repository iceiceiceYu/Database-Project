package edu.fudan.database.controller;

import edu.fudan.database.controller.request.emergencyNurse.PatientRequest;
import edu.fudan.database.domain.Patient;
import edu.fudan.database.service.EmergencyNurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class EmergencyNurseController {
    private final EmergencyNurseService emergencyNurseService;

    @Autowired
    public EmergencyNurseController(EmergencyNurseService emergencyNurseService) {
        this.emergencyNurseService = emergencyNurseService;
    }

    @PostMapping("/emergencyNurse/newPatient")
    public ResponseEntity<Patient> newPatient(@RequestBody PatientRequest patientRequest) {
        return ResponseEntity.ok(emergencyNurseService.newPatient(
                patientRequest.getName(), patientRequest.getGender(),
                patientRequest.getAge(), patientRequest.getLevel()));
    }
}
