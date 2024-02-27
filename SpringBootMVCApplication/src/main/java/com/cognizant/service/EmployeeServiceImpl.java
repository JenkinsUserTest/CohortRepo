package com.cognizant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.dao.EmployeesDAO;
import com.cognizant.dto.EmployeesModel;
import com.cognizant.entity.Employees;
import com.cognizant.exception.EmployeesDAOException;
@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeesDAO employeeDAO;
	public EmployeeServiceImpl() {
		
	}

	public List<EmployeesModel> getAllEmployees() {
		List<EmployeesModel> employeesModelList=new ArrayList<EmployeesModel>();
		List<Employees> employeesList=new ArrayList<Employees>();
		try {
			employeesList=employeeDAO.getAllEmployees();
			for(Employees employees:employeesList) {
				EmployeesModel model=new EmployeesModel();
				model.setEmpId(employees.getEmpId());
				model.setEmpName(employees.getEmpName());
				model.setEmpSalary(employees.getEmpSalary());
				model.setEmpDesignation(employees.getEmpDesignation());
				employeesModelList.add(model);
			}
			
		} catch (EmployeesDAOException e) {
			e.printStackTrace();
		}
		return employeesModelList;
	}

	public EmployeesModel getEmployeeById(int empId) {
		// TODO Auto-generated method stub
		Employees employees=new Employees();
		EmployeesModel model=new EmployeesModel();

		try {
			employees=employeeDAO.getEmployeeById(empId);
			model.setEmpId(employees.getEmpId());
			model.setEmpName(employees.getEmpName());
			model.setEmpSalary(employees.getEmpSalary());
			model.setEmpDesignation(employees.getEmpDesignation());
		} catch (EmployeesDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	public boolean persistEmployee(EmployeesModel employeeModel) {
		boolean result=false;
		try {
			Employees employee=new Employees();
			employee.setEmpId(employeeModel.getEmpId());
			employee.setEmpName(employeeModel.getEmpName());
			employee.setEmpSalary(employeeModel.getEmpSalary());
			employee.setEmpDesignation(employeeModel.getEmpDesignation());
			result=employeeDAO.storeEmployees(employee);
		} catch (EmployeesDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	
	public boolean UpdateEmployeeSalary(int empId, double newSalary) {
		boolean result=false;
		try {
			result=employeeDAO.updateEmployees(empId, newSalary);
		} catch (EmployeesDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	
	public boolean deleteEmployee(int empId) {
		// TODO Auto-generated method stub
		boolean result=false;
		try {
			result=employeeDAO.deleteEmployees(empId);
		} catch (EmployeesDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
