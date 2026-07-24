package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresConnector {
	private static final String URL;
	private static final String USER;
	private static final String PASSWORD;
	
	static {
		try (InputStream input = PostgresConnector.class.getClassLoader().getResourceAsStream("db.properties")) {
			Properties props = new Properties();
			props.load(input);
            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");
		}  catch (IOException e) {
            throw new RuntimeException("Failed to load db.properties", e);
        }
	}
	
	public static Connection getConn() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD); 	
	} 

}
