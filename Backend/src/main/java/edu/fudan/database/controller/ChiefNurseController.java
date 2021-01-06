package edu.fudan.database.controller;

import edu.fudan.database.service.ChiefNurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ChiefNurseController {
    private ChiefNurseService chiefNurseService;

    @Autowired
    public ChiefNurseController(ChiefNurseService chiefNurseService) {
        this.chiefNurseService = chiefNurseService;
    }
}
