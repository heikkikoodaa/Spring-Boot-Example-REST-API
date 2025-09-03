package com.example.payroll.dto;

import org.junit.Test;
import static org.junit.Assert.*;

public class EmployeeDTOTest {

  @Test
  public void testNoArgsConstructor() {
    EmployeeDTO employee = new EmployeeDTO();
    assertNull(employee.getId());
    assertNull(employee.getFirstName());
    assertNull(employee.getLastName());
    assertNull(employee.getRole());
  }

  @Test
  public void testAllArgsConstructor() {
    EmployeeDTO employee = new EmployeeDTO(1L, "John", "Doe", "Developer");
    assertEquals(Long.valueOf(1L), employee.getId());
    assertEquals("John", employee.getFirstName());
    assertEquals("Doe", employee.getLastName());
    assertEquals("Developer", employee.getRole());
  }

  @Test
  public void testSettersAndGetters() {
    EmployeeDTO employee = new EmployeeDTO();
    employee.setId(2L);
    employee.setFirstName("Jane");
    employee.setLastName("Smith");
    employee.setRole("Manager");

    assertEquals(Long.valueOf(2L), employee.getId());
    assertEquals("Jane", employee.getFirstName());
    assertEquals("Smith", employee.getLastName());
    assertEquals("Manager", employee.getRole());
  }
}