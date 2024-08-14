package com.example.fisafroexpay.service;

import com.example.fisafroexpay.dto.RegisterForm;
import com.example.fisafroexpay.entity.User;
import com.example.fisafroexpay.repository.UserRepository;

import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAccessService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(RegisterForm form) {
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = User.builder()
                .username(form.getUsername())
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .totalAssets(0L)
                .discountRate(BigDecimal.valueOf(0))
                .gender(form.getGender())
                .age(form.getAge()).build();

        userRepository.save(user);
    }

}
