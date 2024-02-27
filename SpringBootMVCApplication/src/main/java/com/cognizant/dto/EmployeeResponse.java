package com.cognizant.dto;

public class EmployeeResponse {

	private int empId;
	private String empName;
	private double empSalary;
	private String empDesignation;
	
	public EmployeeResponse() {}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public double getEmpSalary() {
		return empSalary;
	}

	public void setEmpSalary(double empSalary) {
		this.empSalary = empSalary;
	}

	public String getEmpDesignation() {
		return empDesignation;
	}

	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}

	@Override
	public String toString() {
		return "EmployeeResponse [empId=" + empId + ", empName=" + empName + ", empSalary=" + empSalary
				+ ", empDesignation=" + empDesignation + "]";
	}
	
	
}
