package edu.fudan.database.controller;

import edu.fudan.database.controller.request.SelectRequest;
import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Staff;
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

    @PostMapping("/doctor/select")
    public ResponseEntity<List<Patient>> select(@RequestBody SelectRequest selectRequest) {
        return ResponseEntity.ok(doctorService.select(
                selectRequest.getUsername(), selectRequest.getType(), selectRequest.getUsername()));
    }

    @PostMapping("/doctor/chiefNurse")
    public ResponseEntity<List<Staff>> chiefNurse(@RequestBody String doctorUsername) {
        return ResponseEntity.ok(doctorService.chiefNurse(doctorUsername));
    }

    @PostMapping("/doctor/wardNurse")
    public ResponseEntity<List<Staff>> wardNurse(@RequestBody String doctorUsername) {
        return ResponseEntity.ok(doctorService.wardNurse(doctorUsername));
    }
}
