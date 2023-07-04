package com.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.entity.Employee;
import com.employee.exception.ResourceNotFoundException;
import com.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);

	}

	public List<Employee> findAllEmployees() {
		
		List<Employee> employeeList = employeeRepository.findAll();
		employeeList.stream().forEach(employee -> {
			taxCalculation(employee);
		});;
		return employeeList;
	}

	public Employee findEmployeeBasedOnTaxDeduction(Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id ---> " + employeeId));

		taxCalculation(employee);

		return employee;
	}

	private void taxCalculation(Employee employee) {

		Double employeeSalary = employee.getSalary();

		Double yearlySalary = employeeSalary * 12;

		System.out.println("Yearly Salary " + yearlySalary);

		Double tax = null;
		Double cess = null;
		if (yearlySalary <= 250000) {
			System.out.println("No tax applicapble");
		} else if (yearlySalary > 250000 || yearlySalary <= 500000) {
			tax = .05 * yearlySalary;
			System.out.println("5 percent  tax " + tax);

		} else if (yearlySalary > 500000 || yearlySalary <= 1000000) {
			tax = .10 * yearlySalary;
			System.out.println("10 percent  tax " + tax);

		} else if (yearlySalary > 1000000) {
			tax = .20 * yearlySalary;
			System.out.println("20 percent  tax " + tax);
		} else if (yearlySalary > 2500000) {
			cess = 2 / 100 * yearlySalary;
			System.out.println("cess amount " + cess);
		}

		employee.setTaxAmount(tax);
		employee.setCessAmount(cess);

	}

}
