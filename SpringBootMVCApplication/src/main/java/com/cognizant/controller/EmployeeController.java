package com.cognizant.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.dto.EmployeeRequest;
import com.cognizant.dto.EmployeeResponse;
import com.cognizant.dto.EmployeesModel;
import com.cognizant.service.EmployeeService;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class EmployeeController {
	
	public EmployeeController() {}
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("employees")
	public ResponseEntity<List<EmployeeResponse>> handleGetAllEmployees(){

		List<EmployeesModel> empList=employeeService.getAllEmployees();
	
		List<EmployeeResponse> employeeResponseList=new ArrayList<EmployeeResponse>();
		for(EmployeesModel employee:empList) {
			EmployeeResponse response=new EmployeeResponse();
			response.setEmpId(employee.getEmpId());
			response.setEmpName(employee.getEmpName());
			response.setEmpSalary(employee.getEmpSalary());
			response.setEmpDesignation(employee.getEmpDesignation());
			employeeResponseList.add(response);
			
		}
	return new ResponseEntity<List<EmployeeResponse>>(employeeResponseList,HttpStatus.OK);
	}
	
	@GetMapping("employees/{empId}")
	public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable("empId") int empId) {
		ResponseEntity<EmployeeResponse> response=null;

		EmployeesModel employeesModel=employeeService.getEmployeeById(empId);
		if(employeesModel!=null && employeesModel.getEmpId()==empId) {
		EmployeeResponse employeeResponse=new EmployeeResponse();
		employeeResponse.setEmpId(employeesModel.getEmpId());
		employeeResponse.setEmpName(employeesModel.getEmpName());
		employeeResponse.setEmpSalary(employeesModel.getEmpSalary());
		employeeResponse.setEmpDesignation(employeesModel.getEmpDesignation());
		response=new ResponseEntity<EmployeeResponse>(employeeResponse,HttpStatus.FOUND);
		}
	   else{
			response=new ResponseEntity<EmployeeResponse>(HttpStatus.NOT_FOUND);
		}
		return response;
		
	}
	@GetMapping("employeesbyid")
	public ResponseEntity<EmployeeResponse> getEmployeeById(@RequestParam("empId") int empId) {
		ResponseEntity<EmployeeResponse> response=null;
		EmployeesModel employeesModel=employeeService.getEmployeeById(empId);
		if(employeesModel!=null && employeesModel.getEmpId()==empId) {
		EmployeeResponse employeeResponse=new EmployeeResponse();
		employeeResponse.setEmpId(employeesModel.getEmpId());
		employeeResponse.setEmpName(employeesModel.getEmpName());
		employeeResponse.setEmpSalary(employeesModel.getEmpSalary());
		employeeResponse.setEmpDesignation(employeesModel.getEmpDesignation());
		response=new ResponseEntity<EmployeeResponse>(employeeResponse,HttpStatus.OK);
		}
	   else{
			response=new ResponseEntity<EmployeeResponse>(HttpStatus.NOT_FOUND);
		}
		return response;
		
	}
	
	@PostMapping("employees")
	public ResponseEntity<String[]> persistEmployee(@RequestBody EmployeeRequest employeeRequest) {
		EmployeesModel employee=new EmployeesModel();
		employee.setEmpId(employeeRequest.getEmpId());
		employee.setEmpName(employeeRequest.getEmpName());
		employee.setEmpSalary(employeeRequest.getEmpSalary());
		employee.setEmpDesignation(employeeRequest.getEmpDesignation());
		
		boolean result=employeeService.persistEmployee(employee);
		ResponseEntity<String[]> response=null;
		if(result) {
			response=new ResponseEntity<String[]>(new String[]{"Registration successful"},HttpStatus.CREATED);
			
		}else {
			response=new ResponseEntity<String[]>(new String[]{"Registration failed"},HttpStatus.CONFLICT);

		}
		return response;
		
	}
	
	@PatchMapping("employees/{empId}/{empSalary}")
	public ResponseEntity<Void> UpdateEmployeeSalary(@PathVariable("empId") int empId,@PathVariable("empSalary")double empSalary) {
		boolean result=employeeService.UpdateEmployeeSalary(empId, empSalary);
		ResponseEntity<Void> response=null;
		if(result) {
			response=new ResponseEntity<Void>(HttpStatus.ACCEPTED);
			
		}else {
			response=new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);

		}
		
		return response;
		
	}
	
	@DeleteMapping("employees/{empId}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable("empId") int empId) {
		
		boolean result=employeeService.deleteEmployee(empId);
		ResponseEntity<Void> response=null;
		if(result) {
			response=new ResponseEntity<Void>(HttpStatus.OK);
			
		}else {
			response=new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

		}
		
		return response;
		
	}
	
}
