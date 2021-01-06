package edu.fudan.database.controller;

import edu.fudan.database.controller.request.SelectRequest;
import edu.fudan.database.domain.Patient;
import edu.fudan.database.domain.Staff;
import edu.fudan.database.service.WardNurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class WardNurseController {
    private WardNurseService wardNurseService;

    @Autowired
    public WardNurseController(WardNurseService wardNurseService) {
        this.wardNurseService = wardNurseService;
    }

    @PostMapping("/wardNurse/getPatientInfo")
    public ResponseEntity<List<Patient>> getPatientInfo(@RequestBody String doctorUsername) {
        return ResponseEntity.ok(wardNurseService.getPatientInfo(doctorUsername));
    }

    @PostMapping("/wardNurse/select")
    public ResponseEntity<List<Patient>> select(@RequestBody SelectRequest selectRequest) {
        return ResponseEntity.ok(wardNurseService.select(
                selectRequest.getUsername(), selectRequest.getType(), selectRequest.getUsername()));
    }
}
