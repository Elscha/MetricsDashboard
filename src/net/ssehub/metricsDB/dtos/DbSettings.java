package net.ssehub.metricsDB.dtos;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name="db")
@ApplicationScoped
public class DbSettings {

	private String user;
	private String pw;
	private String url;
	
	public DbSettings () {
		Properties prop = new Properties();
    	try (InputStream input = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/db.properties")) {
    		prop.load(input);
    		user = prop.getProperty("user");
    		pw = prop.getProperty("pw");
    		url = prop.getProperty("url");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	setPw(pw);
    	setUser(user);
    	setUrl(url);
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
