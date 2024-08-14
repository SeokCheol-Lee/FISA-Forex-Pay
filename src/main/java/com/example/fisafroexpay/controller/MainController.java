package com.example.fisafroexpay.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  @GetMapping("/profile")
  public String getUser() {
    Authentication authentication = SecurityContextHolder.getContext()
        .getAuthentication();
    return authentication.getName();
  }
}
