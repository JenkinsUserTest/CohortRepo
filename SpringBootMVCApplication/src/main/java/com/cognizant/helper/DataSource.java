package com.cognizant.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSource {
	
	private String driver;
	private String url;
	private String username;
	private String password;
	
	public DataSource(String path) throws IOException {
		
		File file=new File(path);
		InputStream is=new FileInputStream(file);
		Properties properties=new Properties();
		properties.load(is);
		this.driver=properties.getProperty("driver");
		this.url=properties.getProperty("url");
		this.username=properties.getProperty("username");
		this.password=properties.getProperty("password");		
	}

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	
	

}
