package edu.fudan.database.controller;

import edu.fudan.database.controller.request.user.LoginRequest;
import edu.fudan.database.controller.request.user.ModifyInfoRequest;
import edu.fudan.database.controller.request.user.ModifyPassRequest;
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

    @PostMapping("/user/info")
    public ResponseEntity<Staff> info(@RequestBody String username) {
        return ResponseEntity.ok(userService.info(username));
    }

    @PostMapping("/user/login")
    public ResponseEntity<Staff> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    @PostMapping("/user/modifyInfo")
    public ResponseEntity<String> modifyInfo(@RequestBody ModifyInfoRequest modifyInfoRequest) {
        return ResponseEntity.ok(userService.modifyInfo(
                modifyInfoRequest.getUsername(), modifyInfoRequest.getBirth(), modifyInfoRequest.getName()));
    }

    @PostMapping("/user/modifyPass")
    public ResponseEntity<String> modifyPass(@RequestBody ModifyPassRequest modifyPassRequest) {
        return ResponseEntity.ok(userService.modifyPass(
                modifyPassRequest.getUsername(), modifyPassRequest.getOldPassword(), modifyPassRequest.getNewPassword()));
    }
}
