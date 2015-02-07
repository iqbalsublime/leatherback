package com.rc.leatherback.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.rc.core.util.PropertyUtil;

public class DatabaseContext {
	private static String databaseUser;
	private static String databasePassword;
	private static String databaseUrl;
	private static String databaseDriver;

	static {
		try {
			Properties properties = PropertyUtil.getProperties("META-INF/database.properties");
			databaseUser = properties.getProperty("database.username");
			databasePassword = properties.getProperty("database.password");
			databaseUrl = properties.getProperty("database.url");
			databaseDriver = properties.getProperty("database.driverClassName");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(databaseDriver);
		return DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
	}
}
