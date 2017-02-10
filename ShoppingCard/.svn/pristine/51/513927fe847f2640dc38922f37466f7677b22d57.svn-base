package com.ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

public class UserIDdata {

	public HashSet<Integer> getIDs(){
		HashSet<Integer> ids= new HashSet<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			try(Connection con= DriverManager.getConnection("jdbc:oracle:thin:@db.k8j.net:1521:orcl","java16","java16")){
				Statement stmt= con.createStatement();
				ResultSet rs= stmt.executeQuery("select ID from users");
				while(rs.next()){
					ids.add(rs.getInt(1));
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ids;
	}
}
