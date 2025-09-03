package com.example.payroll.exception;

import org.junit.Test;
import static org.junit.Assert.*;

public class EmployeeNotFoundAdviceTest {

  @Test
  public void employeeNotFoundHandler_returnsExceptionMessage() {
    EmployeeNotFoundAdvice advice = new EmployeeNotFoundAdvice();
    String message = "Could not find employee 2";
    EmployeeNotFoundException ex = new EmployeeNotFoundException(2L);

    String result = advice.employeeNotFoundHandler(ex);

    assertEquals(message, result);
  }
}