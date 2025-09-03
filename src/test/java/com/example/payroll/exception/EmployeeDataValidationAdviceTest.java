package com.example.payroll.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeDataValidationAdviceTest {

  @Test
  void employeeDataValidationHandler_returnsExceptionMessage() {
    EmployeeDataValidationAdvice advice = new EmployeeDataValidationAdvice();
    String errorMessage = "Invalid employee data";
    EmployeeDataValidationException exception = new EmployeeDataValidationException(errorMessage);

    String result = advice.employeeDataValidationHandler(exception);

    assertEquals(errorMessage, result);
  }
}