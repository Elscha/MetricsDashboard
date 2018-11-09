package net.ssehub.metricsDB.db;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import net.ssehub.metricsDB.dtos.DbSettings;

@ManagedBean(name="dbSettingsService")
@ApplicationScoped
public class DbSettingsBean {

	@ManagedProperty(value = "#{db}")
	private DbSettings db;
	
	public DbSettings getDb() {
		return db;
	}
}
