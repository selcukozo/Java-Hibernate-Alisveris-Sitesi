package bean;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;

public class Database {

	private static OracleDataSource dataSource;
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			dataSource = new OracleDataSource();
			dataSource.setURL("jdbc:oracle:thin:java16/java16@db.k8j.net:1521:ORCL");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
}