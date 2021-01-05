package edu.fudan.database.controller;

import edu.fudan.database.controller.request.LoginRequest;
import edu.fudan.database.controller.request.ModifyRequest;
import edu.fudan.database.domain.Staff;
import edu.fudan.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/login")
    public ResponseEntity<Staff> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    @PostMapping("/user/modify")
    public ResponseEntity<Staff> modify(@RequestBody ModifyRequest modifyRequest) {
        return ResponseEntity.ok(userService.modify(
                modifyRequest.getUsername(), modifyRequest.getPassword(),
                modifyRequest.getBirth(), modifyRequest.getName()));
    }
}
