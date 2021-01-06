package edu.fudan.database.controller;

import edu.fudan.database.service.WardNurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class WardNurseController {
    private WardNurseService wardNurseService;

    @Autowired
    public WardNurseController(WardNurseService wardNurseService) {
        this.wardNurseService = wardNurseService;
    }
}
