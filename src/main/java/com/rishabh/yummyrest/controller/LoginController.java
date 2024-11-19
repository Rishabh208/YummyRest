// src/main/java/com/rishabh/yummy rest/controller/LoginController.java
package com.rishabh.yummyrest.controller;

import com.rishabh.yummyrest.dto.LoginRequest;
import com.rishabh.yummyrest.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {

        String jwtToken = loginService.login(request);
        return ResponseEntity.ok(jwtToken);
    }

    // Exception handler for invalid credentials
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalidCredentials(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }


}