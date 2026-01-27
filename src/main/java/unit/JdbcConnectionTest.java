package unit;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import persistence.jdbc.*;
import org.junit.Test;

public class JdbcConnectionTest {
	
	@Test
	public void testConnection() {
	    Properties props = new Properties();
	    System.out.println(getClass().getClassLoader().getResource("config.properties"));

	    try (InputStream is = getClass()
	            .getClassLoader()
	            .getResourceAsStream("config.properties")) {

	        assertNotNull("config.properties introuvable dans le classpath", is);

	        props.load(is);

	        String url = props.getProperty("db.url");
	        String user = props.getProperty("db.user");
	        String password = props.getProperty("db.password");

	        assertNotNull(url, "db.url est null");

	        Connection conn = DriverManager.getConnection(url, user, password);
	        assertNotNull(conn);
	        conn.close();

	    } catch (Exception e) {
	        fail(e.getMessage());
	    }
	}


}
