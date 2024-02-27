package com.cognizant.exception;

public class EmployeesDAOException extends Exception{
	
	private String message;

	public EmployeesDAOException(String message,Throwable throwable) {
		super(throwable);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	

}
