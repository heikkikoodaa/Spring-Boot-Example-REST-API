package com.example.payroll.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class EmployeeTest {

  @Test
  public void testConstructorAndGetters() {
    Employee emp = new Employee("John", "Doe", "Developer");
    assertEquals("John", emp.getFirstName());
    assertEquals("Doe", emp.getLastName());
    assertEquals("Developer", emp.getRole());
    assertEquals("John Doe", emp.getName());
    assertEquals("John Doe Developer", emp.getEverything());
  }

  @Test
  public void testSetIdAndGetId() {
    Employee emp = new Employee("Jane", "Smith", "Manager");
    emp.setId(100L);
    assertEquals(Long.valueOf(100), emp.getId());
  }

  @Test
  public void testSetName() {
    Employee emp = new Employee("A", "B", "Role");
    emp.setName("Alice Cooper");
    assertEquals("Alice", emp.getFirstName());
    assertEquals("Cooper", emp.getLastName());
    assertEquals("Alice Cooper", emp.getName());
  }

  @Test
  public void testSetRole() {
    Employee emp = new Employee("Tom", "Jones", "Singer");
    emp.setRole("Artist");
    assertEquals("Artist", emp.getRole());
  }

  @Test
  public void testEqualsAndHashCode() {
    Employee emp1 = new Employee("Sam", "Wilson", "Pilot");
    Employee emp2 = new Employee("Sam", "Wilson", "Pilot");
    emp1.setId(1L);
    emp2.setId(1L);
    assertTrue(emp1.equals(emp2));
    assertEquals(emp1.hashCode(), emp2.hashCode());

    emp2.setId(2L);
    assertFalse(emp1.equals(emp2));
  }

  @Test
  public void testToString() {
    Employee emp = new Employee("Bruce", "Wayne", "CEO");
    emp.setId(42L);
    String expected = "Employee{id=42, firstName='Bruce', lastName='Wayne', role='CEO'}";
    assertEquals(expected, emp.toString());
  }
}