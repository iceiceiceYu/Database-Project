package edu.fudan.database.controller;

import edu.fudan.database.service.EmergencyNurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class EmergencyNurseController {
    private EmergencyNurseService emergencyNurseService;

    @Autowired
    public EmergencyNurseController(EmergencyNurseService emergencyNurseService) {
        this.emergencyNurseService = emergencyNurseService;
    }
}
