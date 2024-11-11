// src/main/java/com/rishabh/yummyrest/service/LoginService.java
package com.rishabh.yummyrest.service;

import com.rishabh.yummyrest.dto.LoginRequest;
import com.rishabh.yummyrest.entity.Customer;
import com.rishabh.yummyrest.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final CustomerRepo customerRepo;

    public String login(LoginRequest request) {
        Customer customer = customerRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

       
        if (request.getPassword().equals(customer.getPassword())) {
            return "User logged in successfully.";
        } else {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }
}
