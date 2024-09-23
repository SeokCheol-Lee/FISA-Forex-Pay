package com.example.fisafroexpay.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    FAILED("FAIL"),
    PROCESSING("PROCESSING"),
    COMPLETED("COMPLETED");
    private final String description;
}
