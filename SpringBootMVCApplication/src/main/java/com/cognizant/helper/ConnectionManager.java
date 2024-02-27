package com.cognizant.helper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
@Component
public class ConnectionManager {
	
	private DataSource dataSource;
	private static Connection connection=null;
	public ConnectionManager() throws IOException {
		this.dataSource=new DataSource("D:\\yashio\\db.properties");
	}
	
	public Connection openConnection() throws ClassNotFoundException, SQLException{
		
		Class.forName(dataSource.getDriver());
		connection=DriverManager.getConnection(dataSource.getUrl()
				,dataSource.getUsername(),dataSource.getPassword());
		
		return connection;
	}
	
	public void closeConnection() throws SQLException {
		connection.close();
	}

}
