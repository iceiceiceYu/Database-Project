package edu.fudan.database.controller;

import edu.fudan.database.controller.request.SelectRequest;
import edu.fudan.database.controller.request.doctor.ModifyLevelRequest;
import edu.fudan.database.controller.request.doctor.TestRequest;
import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Report;
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
    private final DoctorService doctorService;

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
                selectRequest.getUsername(), selectRequest.getType(), selectRequest.getValue()));

    }

    @PostMapping("/doctor/chiefNurse")
    public ResponseEntity<List<Staff>> chiefNurse(@RequestBody String doctorUsername) {
        return ResponseEntity.ok(doctorService.chiefNurse(doctorUsername));
    }

    @PostMapping("/doctor/wardNurse")
    public ResponseEntity<List<Staff>> wardNurse(@RequestBody String doctorUsername) {
        return ResponseEntity.ok(doctorService.wardNurse(doctorUsername));
    }

    @PostMapping("/doctor/patientsOfNurse")
    public ResponseEntity<List<String>> patientsOfNurse(@RequestBody String doctorUsername) {
        return ResponseEntity.ok(doctorService.patientsOfNurse(doctorUsername));
    }

    @PostMapping("/doctor/modifyLevel")
    public ResponseEntity<Patient> modifyLevel(@RequestBody ModifyLevelRequest modifyLevelRequest) {
        return ResponseEntity.ok(doctorService.modifyLevel(modifyLevelRequest.getPatientId(), modifyLevelRequest.getNewLevel()));
    }

    @PostMapping("/doctor/modifyAlive")
    public ResponseEntity<Patient> modifyAlive(@RequestBody Long patientId) {
        return ResponseEntity.ok(doctorService.modifyAlive(patientId));
    }

    @PostMapping("/doctor/addReport")
    public ResponseEntity<Report> addReport(@RequestBody TestRequest testRequest) {
        return ResponseEntity.ok(doctorService.addReport(
                testRequest.getPatientId(), testRequest.getPatientName(), testRequest.isPositive(),
                testRequest.getLevel(), testRequest.getDate(), testRequest.getDoctor()));
    }

    @PostMapping("/doctor/discharge")
    public ResponseEntity<String> discharge(@RequestBody Long patientId) {
        return ResponseEntity.ok(doctorService.discharge(patientId));
    }

    @PostMapping("/doctor/getMessage")
    public ResponseEntity<List<String>> getMessage(@RequestBody String doctorUsername) {
        return ResponseEntity.ok(doctorService.getMessage(doctorUsername));
    }
}
