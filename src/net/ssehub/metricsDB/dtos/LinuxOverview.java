package net.ssehub.metricsDB.dtos;

import java.io.Serializable;

public class LinuxOverview implements Serializable {

	private static final long serialVersionUID = -2027738677258219660L;

	private String linux_id;
	private String version;
	private String measures;
	public String getLinux_id() {
		return linux_id;
	}
	public void setLinux_id(String linux_id) {
		this.linux_id = linux_id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMeasures() {
		return measures;
	}
	public void setMeasures(String measures) {
		this.measures = measures;
	}
	
}
