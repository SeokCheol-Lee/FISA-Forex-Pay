package com.example.fisafroexpay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .formLogin((formLogin) ->
            formLogin
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/login/login-processing")
                .defaultSuccessUrl("/",true)
        )
        .logout((logout) ->
            logout.logoutSuccessUrl("/")
        )
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/","/login/**").permitAll()
            .requestMatchers( "/swagger-ui/**","/api-docs/**","/api*").permitAll()
            .anyRequest().authenticated());
    return http.build();
  }
}
