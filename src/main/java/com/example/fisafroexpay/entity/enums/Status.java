package com.example.fisafroexpay.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    FAIL("FAIL"),SUCCESS("SUCCESS");
    private final String description;
}
