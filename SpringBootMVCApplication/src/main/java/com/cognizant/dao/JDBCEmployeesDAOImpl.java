package com.cognizant.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cognizant.entity.Employees;
import com.cognizant.exception.EmployeesDAOException;
import com.cognizant.helper.ConnectionManager;
@Repository
public class JDBCEmployeesDAOImpl implements EmployeesDAO {
	
	@Autowired
	private ConnectionManager manager;
	public JDBCEmployeesDAOImpl(){
	}
	public List<Employees> getAllEmployees() throws EmployeesDAOException {
		List<Employees> employeesList=new ArrayList<Employees>();

		try {
			//1.opening database connection
			Connection connection=manager.openConnection();
			//2.Statement represents SQL statements
			Statement statement=connection.createStatement();
			//3.We can embed SQL statement in Statement object
			//4. Record set is represented by ResultSet
			ResultSet resultSet=statement.executeQuery("select * from EMPLOYEES");
			while(resultSet.next()) {
				Employees employees=new Employees();
				employees.setEmpId(resultSet.getInt("EMPLOYEE_ID"));
				employees.setEmpName(resultSet.getString("EMPLOYEE_NAME"));
				employees.setEmpSalary(resultSet.getDouble("EMPLOYEE_SALARY"));
				employees.setEmpDesignation(resultSet.getString("EMPLOYEE_DESIGNATION"));
				employeesList.add(employees);
				
			}
		} catch (ClassNotFoundException e) {
		
			throw new EmployeesDAOException("DAO Exception",e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeesDAOException("DAO Exception",e);		}
		try {
			manager.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeesDAOException("DAO Exception",e);		
		}
		return employeesList;
	}

	public Employees getEmployeeById(int empId) throws EmployeesDAOException {
		Employees employees=new Employees();

		try {
			Connection connection=manager.openConnection();
			PreparedStatement statement=
					connection.prepareStatement("select * from EMPLOYEES where EMPLOYEE_ID=?");
			statement.setInt(1, empId);
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()) {
				employees.setEmpId(resultSet.getInt("EMPLOYEE_ID"));
				employees.setEmpName(resultSet.getString("EMPLOYEE_NAME"));
				employees.setEmpSalary(resultSet.getDouble("EMPLOYEE_SALARY"));
				employees.setEmpDesignation(resultSet.getString("EMPLOYEE_DESIGNATION"));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new EmployeesDAOException("DAO Exception",e);		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeesDAOException("DAO Exception",e);		
		}
		try {
			manager.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeesDAOException("DAO Exception",e);		
		}
		return employees;
	}

	
	public boolean storeEmployees(Employees Employees) throws EmployeesDAOException {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			Connection connection=manager.openConnection();
			PreparedStatement statement=
					connection.prepareStatement("insert into EMPLOYEES values(?,?,?,?)");
			statement.setInt(1, Employees.getEmpId());
			statement.setString(2, Employees.getEmpName());
			statement.setDouble(3,Employees.getEmpSalary());
			statement.setString(4, Employees.getEmpDesignation());
			rows=statement.executeUpdate();
		} catch (ClassNotFoundException  e) {
			throw new EmployeesDAOException("DAO Exception",e);		
		}
		 catch ( SQLException e) {
				// TODO Auto-generated catch block
				throw new EmployeesDAOException("DAO Exception",e);		
			}
		try {
			manager.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeesDAOException("DAO Exception",e);		
		}
		if(rows>0)
			return true;
		else
		return false;
	}

	public boolean updateEmployees(int empId, double newSalary) throws EmployeesDAOException {
        int rows=0;
		try {
			Connection connection=manager.openConnection();
			PreparedStatement statement=
					connection.prepareStatement("update EMPLOYEES set EMPLOYEE_SALARY=? where EMPLOYEE_ID=?");
			statement.setDouble(1,newSalary);
			statement.setInt(2, empId);
			rows=statement.executeUpdate();
			
		} catch (ClassNotFoundException  e) {
			// TODO Auto-generated catch block
			throw new EmployeesDAOException("DAO Exception",e);		
		}
		 catch ( SQLException e) {
				// TODO Auto-generated catch block
				throw new EmployeesDAOException("DAO Exception",e);		
			}
		
		try {
			manager.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeesDAOException("DAO Exception",e);		
		}
		
		if(rows>0)
			return true;
		else
		return false;
	}

	public boolean deleteEmployees(int empId) throws EmployeesDAOException {
		// TODO Auto-generated method stub
		int rows=0;
		try {
			Connection connection=manager.openConnection();
			PreparedStatement statement=
					connection.prepareStatement("delete from EMPLOYEES where EMPLOYEE_ID=?");
			statement.setInt(1, empId);
			rows=statement.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new EmployeesDAOException("DAO Exception",e);		
		}catch(SQLException e) {
			throw new EmployeesDAOException("DAO Exception",e);		
		}
		try {
			manager.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EmployeesDAOException("DAO Exception",e);		
		}
		if(rows>0)
			return true;
		else
		return false;
	}

	
}
