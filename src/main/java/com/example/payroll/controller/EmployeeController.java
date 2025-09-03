package com.example.payroll.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.payroll.assembler.EmployeeModelAssembler;
import com.example.payroll.dto.EmployeeDTO;
import com.example.payroll.exception.*;
import com.example.payroll.model.Employee;
import com.example.payroll.repository.EmployeeRepository;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class EmployeeController {
  
  private final EmployeeRepository repository;
  private final EmployeeModelAssembler assembler;
  private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

  EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }

  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/employees")
  public CollectionModel<EntityModel<EmployeeDTO>> all() {

    List<EntityModel<EmployeeDTO>> employees = repository.findAll().stream()
      .map(employee -> {
        EmployeeDTO employeeDTO = new EmployeeDTO(
          employee.getId(),
          employee.getFirstName(),
          employee.getLastName(),
          employee.getRole()
        );
        return assembler.toModel(employeeDTO);
      })
      .collect(Collectors.toList());

    return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
  }

  @PostMapping("/employees")
  ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

    Employee savedEmployee = repository.save(newEmployee);
    EmployeeDTO employeeDTO = new EmployeeDTO(
      savedEmployee.getId(), 
      savedEmployee.getFirstName(), 
      savedEmployee.getLastName(), 
      savedEmployee.getRole()
    );
    EntityModel<EmployeeDTO> entityModel = assembler.toModel(employeeDTO);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  // Single item

  @GetMapping("/employees/{id}")
  public EntityModel<EmployeeDTO> one(@PathVariable Long id) {

    Employee employee = repository.findById(id)
      .orElseThrow(() -> new EmployeeNotFoundException(id));

    EmployeeDTO employeeDTO = new EmployeeDTO(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getRole());

    return assembler.toModel(employeeDTO);
  }

  @PutMapping("/employees/{id}")
  ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

    log.info("Incoming employee payload: {}", newEmployee);
    log.info("newEmployee name: {}", newEmployee.getName());

    // Manual payload validation
    if (newEmployee.getName() == null || newEmployee.getName().trim().isEmpty() ||
        newEmployee.getRole() == null || newEmployee.getRole().trim().isEmpty()) {
      throw new EmployeeDataValidationException("Invalid payload: 'name' and 'role' must not be empty.");
    }

    Employee updatedEmployee = repository.findById(id)
        .map(employee -> {
          employee.setName(newEmployee.getName());
          employee.setRole(newEmployee.getRole());
          return repository.save(employee);
        })
        .orElseGet(() -> {
          return repository.save(newEmployee);
        });

    EmployeeDTO employeeDTO = new EmployeeDTO(
      updatedEmployee.getId(),
      updatedEmployee.getFirstName(),
      updatedEmployee.getLastName(),
      updatedEmployee.getRole()
    );

    EntityModel<EmployeeDTO> entityModel = assembler.toModel(employeeDTO);

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @DeleteMapping("/employees/{id}")
  ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

    repository.deleteById(id);

    return ResponseEntity.noContent().build();
  }
}