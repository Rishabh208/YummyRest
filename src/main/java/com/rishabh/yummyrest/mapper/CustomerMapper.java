package com.rishabh.yummyrest.mapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rishabh.yummyrest.dto.CustomerRequest;
import com.rishabh.yummyrest.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public Customer toEntity(CustomerRequest request) {
        String hashedPassword = passwordEncoder.encode(request.password());
        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(hashedPassword)
                .build();
    }
}
