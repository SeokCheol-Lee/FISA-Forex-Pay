package com.example.fisafroexpay.controller;

import com.example.fisafroexpay.dto.RegisterForm;
import com.example.fisafroexpay.service.UserAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/login")
@RequiredArgsConstructor
@RestController
public class UserAccessController {

  private final UserAccessService userAccessService;

  @PostMapping("/join")
  public String join(@RequestBody RegisterForm registerForm) {
    userAccessService.registerUser(registerForm);
    return "redirect:/login";
  }

}
