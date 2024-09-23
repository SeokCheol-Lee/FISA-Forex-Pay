package com.example.fisafroexpay.controller;

import com.example.fisafroexpay.dto.RegisterForm;
import com.example.fisafroexpay.entity.User;
import com.example.fisafroexpay.service.AccountService;
import com.example.fisafroexpay.service.UserAccessService;
import jakarta.transaction.Transactional;
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
  private final AccountService accountService;

  @Transactional
  @PostMapping("/signup")
  public String join(@ModelAttribute RegisterForm registerForm) {
    User user = userAccessService.registerUser(registerForm);
    accountService.createAccount(user);
    return "redirect:/login.html";
  }

}
