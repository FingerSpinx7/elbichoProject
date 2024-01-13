package model;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class ConnectionUtil {
	
	Connection conn = null;

	public static Connection connectdb(String database) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database, "root", "");
	        return conn;
	    } catch (Exception e) {
	    	System.out.println("Error: " + e.getMessage());
            return null;
	    }
	}
	
	
}
