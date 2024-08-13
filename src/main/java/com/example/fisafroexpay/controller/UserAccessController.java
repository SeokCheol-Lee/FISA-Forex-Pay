package com.example.fisafroexpay.controller;

import com.example.fisafroexpay.dto.RegisterForm;
import com.example.fisafroexpay.service.UserAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/login")
@RequiredArgsConstructor
@Controller
public class UserAccessController {

  private final UserAccessService userAccessService;

  @PostMapping("/signup")
  public String join(@ModelAttribute RegisterForm registerForm) {
    userAccessService.registerUser(registerForm);
    return "redirect:/login.html";
  }

}
