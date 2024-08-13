package com.example.fisafroexpay.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

  private final ObjectMapper objectMapper;
  private static final Logger logger = LoggerFactory.getLogger("AOPLogger");

  @Before("execution(* com.example.fisafroexpay.service..*(..))")  // 패키지 경로에 맞게 수정
  public void logBeforeMethod(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    String arguments = Arrays.toString(joinPoint.getArgs());
    String logMessage = "Before method: " + methodName + " - Arguments: " + arguments;
    logger.info(logMessage);
  }

  @AfterReturning(pointcut = "execution(* com.example.fisafroexpay.service..*(..))", returning = "result")
  public void logAfterMethod(JoinPoint joinPoint, Object result) throws JsonProcessingException {
    String methodName = joinPoint.getSignature().getName();
    String returnValue = objectMapper.writeValueAsString(result);
    String logMessage = "After method: " + methodName + " - Return value: " + returnValue;
    logger.info(logMessage);
  }

  @AfterThrowing(pointcut = "execution(* com.example.fisafroexpay.service..*(..))", throwing = "exception")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
    String methodName = joinPoint.getSignature().getName();
    String logMessage = "Exception in method: " + methodName + " - Exception: " + exception.getMessage();
    logger.info(logMessage);
  }
}
