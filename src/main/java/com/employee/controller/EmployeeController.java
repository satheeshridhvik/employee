package com.employee.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.entity.Employee;
import com.employee.exception.ResourceNotFoundException;
import com.employee.service.EmployeeService;

@RestController
@Validated
@RequestMapping("/employee/v1")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {

		return ResponseEntity.ok().body(employeeService.findEmployeeBasedOnTaxDeduction(employeeId));
	}
	
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployee() {

		return ResponseEntity.ok().body(employeeService.findAllEmployees());
	}


	@PostMapping("/employees")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {

		return employeeService.saveEmployee(employee);
	}

}
