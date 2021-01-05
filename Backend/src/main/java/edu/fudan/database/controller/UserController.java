package edu.fudan.database.controller;

import edu.fudan.database.controller.request.LoginRequest;
import edu.fudan.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        Map<String, String> map = new HashMap<>();
        String response = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        map.put("response", response);
        return ResponseEntity.ok(map);
    }
}
