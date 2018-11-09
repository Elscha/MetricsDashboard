package net.ssehub.metricsDB.dtos;

import java.io.Serializable;

public class Function implements Serializable {
	
	private static final long serialVersionUID = 1678463415477772180L;
	private String id;
	private String path;
	private String name;
	private int errors;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getErrors() {
		return errors;
	}
	public void setErrors(int errors) {
		this.errors = errors;
	}
	
	@Override
	public String toString() {
		return path + ":" + name;
	}

}
