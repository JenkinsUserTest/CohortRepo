package com.cognizant.service;

import java.util.List;

import com.cognizant.dto.EmployeesModel;

public interface EmployeeService {

	List<EmployeesModel> getAllEmployees();

	EmployeesModel getEmployeeById(int empId);

	boolean persistEmployee(EmployeesModel employeeModel);

	boolean UpdateEmployeeSalary(int empId, double newSalary);

	boolean deleteEmployee(int empId);

}