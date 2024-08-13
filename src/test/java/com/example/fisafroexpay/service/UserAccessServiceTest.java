package com.example.fisafroexpay.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.fisafroexpay.dto.RegisterForm;
import com.example.fisafroexpay.entity.User;
import com.example.fisafroexpay.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserAccessServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @InjectMocks
  private UserAccessService userAccessService;

  @Test
  void registerUser() {
    //given
    given(userRepository.existsByEmail(any()))
        .willReturn(false);
    RegisterForm form = RegisterForm.builder()
        .email("email@email.com")
        .password("password").build();
    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    //when
    userAccessService.registerUser(form);

    //then
    verify(userRepository).save(captor.capture());
    User user = captor.getValue();
    assertEquals(form.getEmail(), user.getEmail());
  }

  @DisplayName("회원가입 실패 - 동일 이메일 존재")
  @Test
  void registerUser_existingEmail() {
    //given
    given(userRepository.existsByEmail(any()))
        .willReturn(true);
    RegisterForm form = RegisterForm.builder()
        .email("email@email.com")
        .password("password").build();
    //when
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> userAccessService.registerUser(form));
    //then
    assertEquals(exception.getMessage(), "Email already in use");
  }
}