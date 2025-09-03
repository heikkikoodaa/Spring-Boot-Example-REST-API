package com.example.payroll.exception;

public class EmployeeDataValidationException extends RuntimeException {
    public EmployeeDataValidationException(String message) {
        super(message);
    }
}