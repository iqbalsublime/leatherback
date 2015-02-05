package com.rc.leatherback;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseContext {
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/leatherback_dev";
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "rocky.chen";

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DatabaseContext.DB_DRIVER);
		return DriverManager.getConnection(DatabaseContext.DB_URL, DatabaseContext.DB_USER, DatabaseContext.DB_PASSWORD);
	}
}
