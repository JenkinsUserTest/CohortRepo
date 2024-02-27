package com.cognizant.dao;

import java.util.List;

import com.cognizant.entity.Employees;
import com.cognizant.exception.EmployeesDAOException;

public interface EmployeesDAO {
	List<Employees> getAllEmployees() throws EmployeesDAOException;
	Employees getEmployeeById(int empId) throws EmployeesDAOException;
	boolean storeEmployees(Employees Employees)  throws EmployeesDAOException;
	boolean updateEmployees(int empId,double newSalary) throws EmployeesDAOException;
	boolean deleteEmployees(int empId) throws EmployeesDAOException;

}
