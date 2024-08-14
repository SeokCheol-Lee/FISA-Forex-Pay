package com.example.fisafroexpay.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

  @Bean
  public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .formLogin((formLogin) ->
            formLogin
                .loginPage("/login.html")
                .defaultSuccessUrl("/index.html")
                //.successHandler(successHandler())
                .loginProcessingUrl("/perform_login")  // 로그인 폼 action URL
                .failureUrl("/login?error=true")
        )
        .logout((logout) ->
            logout.logoutSuccessUrl("/index.html#")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        )
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/*","/login/**").permitAll()
            .requestMatchers( "/swagger-ui/**","/api-docs/**","/api*").permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
            .authenticated());

    return http.build();
  }

  @Bean
  public AuthenticationSuccessHandler successHandler() {
    return (request, response, authentication) -> {
      HttpSession session = request.getSession();
      session.setAttribute("username", authentication.getName()); // 세션에 사용자 이름 저장
      response.sendRedirect("/index"); // 로그인 성공 후 리다이렉트
    };
  }
}
