// src/main/java/com/rishabh/yummyrest/service/LoginService.java
package com.rishabh.yummyrest.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.rishabh.yummyrest.util.JwtUtil;
import com.rishabh.yummyrest.dto.LoginRequest;
import com.rishabh.yummyrest.entity.Customer;
import com.rishabh.yummyrest.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final CustomerRepo customerRepo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String login(LoginRequest request) {
        Customer customer = customerRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));


        if (passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            String token = jwtUtil.generateToken(request.getEmail());
            System.out.println("Generated Token: " + token);

            return "User logged in successfully.";
        } else {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }
}
