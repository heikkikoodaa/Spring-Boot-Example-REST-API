package com.example.payroll.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.payroll.controller.EmployeeController;
import com.example.payroll.dto.EmployeeDTO;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<EmployeeDTO, EntityModel<EmployeeDTO>> {

  @Override
  public EntityModel<EmployeeDTO> toModel(EmployeeDTO employee) {

    return EntityModel.of(employee, //
        linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
        linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
  }
}