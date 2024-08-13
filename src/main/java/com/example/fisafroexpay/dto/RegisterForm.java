package com.example.fisafroexpay.dto;

import com.example.fisafroexpay.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {
    private String username;
    private String email;
    private String password;
    private Gender gender;
    private int age;
}
