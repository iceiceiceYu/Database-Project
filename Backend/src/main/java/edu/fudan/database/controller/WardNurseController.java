package edu.fudan.database.controller;

import edu.fudan.database.controller.request.SelectRequest;
import edu.fudan.database.controller.request.WardNurse.DailyInfoRequest;
import edu.fudan.database.domain.DailyInfo;
import edu.fudan.database.domain.Patient;
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
    private final WardNurseService wardNurseService;

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
                selectRequest.getUsername(), selectRequest.getType(), selectRequest.getType()));
    }

    @PostMapping("/wardNurse/dailyInfo")
    public ResponseEntity<DailyInfo> dailyInfo(@RequestBody DailyInfoRequest dailyInfoRequest) {
        return ResponseEntity.ok(wardNurseService.dailyInfo(
                dailyInfoRequest.getPatientId(), dailyInfoRequest.getPatientName(),
                dailyInfoRequest.getTemperature(), dailyInfoRequest.getSymptom(),
                dailyInfoRequest.isPositive(), dailyInfoRequest.getDate(),
                dailyInfoRequest.getWardNurse()));
    }
}
