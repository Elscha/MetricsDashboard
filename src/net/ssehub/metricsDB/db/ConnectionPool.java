package net.ssehub.metricsDB.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.faces.context.FacesContext;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class ConnectionPool {

	private static BasicDataSource ds = new BasicDataSource();
    
	static {
    	String user = null;
    	String pw = null;
    	String url = null;
    	Properties prop = new Properties();
    	try (InputStream input = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/db.properties")) {
    		prop.load(input);
    		user = prop.getProperty("user");
    		pw = prop.getProperty("pw");
    		url = prop.getProperty("url");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        ds.setUrl("jdbc:mariadb://" + url);
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setUsername(user);
        ds.setPassword(pw);
//        ds.setDefaultSchema("test_robot");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }
     
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
     
    private ConnectionPool(){ }
}
