package edu.fudan.database.controller;

import edu.fudan.database.controller.request.SelectRequest;
import edu.fudan.database.controller.request.chiefNurse.ModifyRequest;
import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Staff;
import edu.fudan.database.service.ChiefNurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class ChiefNurseController {
    private final ChiefNurseService chiefNurseService;

    @Autowired
    public ChiefNurseController(ChiefNurseService chiefNurseService) {
        this.chiefNurseService = chiefNurseService;
    }

    @PostMapping("/chiefNurse/getPatientInfo")
    public ResponseEntity<List<Patient>> getPatientInfo(@RequestBody String chiefNurseUsername) {
        return ResponseEntity.ok(chiefNurseService.getPatientInfo(chiefNurseUsername));
    }

    @PostMapping("/chiefNurse/select")
    public ResponseEntity<List<Patient>> select(@RequestBody SelectRequest selectRequest) {
        return ResponseEntity.ok(chiefNurseService.select(
                selectRequest.getUsername(), selectRequest.getType(), selectRequest.getType()));
    }

    @PostMapping("/chiefNurse/wardNurse")
    public ResponseEntity<List<Staff>> wardNurse(@RequestBody String chiefNurseUsername) {
        return ResponseEntity.ok(chiefNurseService.wardNurse(chiefNurseUsername));
    }

    @PostMapping("/chiefNurse/patientsOfNurse")
    public ResponseEntity<List<String>> patientsOfNurse(@RequestBody String chiefNurseUsername) {
        return ResponseEntity.ok(chiefNurseService.patientsOfNurse(chiefNurseUsername));
    }

    @PostMapping("/chiefNurse/newNurse")
    public ResponseEntity<Staff> newNurse(@RequestBody ModifyRequest modifyRequest) {
        return ResponseEntity.ok(chiefNurseService.newNurse(
                modifyRequest.getChiefNurse(), modifyRequest.getNurseName()));
    }

    @PostMapping("/chiefNurse/deleteNurse")
    public ResponseEntity<String> deleteNurse(@RequestBody ModifyRequest modifyRequest) {
        return ResponseEntity.ok(chiefNurseService.deleteNurse(
                modifyRequest.getChiefNurse(), modifyRequest.getNurseName()));
    }

    @PostMapping("/chiefNurse/searchBackupWard")
    public ResponseEntity<List<Staff>> searchBackupWard(@RequestBody String chiefNurseUsername) {
        return ResponseEntity.ok(chiefNurseService.searchBackupWard(chiefNurseUsername));
    }
}
