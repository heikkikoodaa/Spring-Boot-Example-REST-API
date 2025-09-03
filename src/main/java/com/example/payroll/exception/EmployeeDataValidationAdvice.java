package com.example.payroll.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeDataValidationAdvice {

  @ExceptionHandler(EmployeeDataValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String employeeDataValidationHandler(EmployeeDataValidationException ex) {
    return ex.getMessage();
  }
}
