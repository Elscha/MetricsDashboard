package net.ssehub.metricsDB.dtos;

import java.io.Serializable;
import java.sql.Date;

public class DatabaseInfo implements Serializable {
	
	private static final long serialVersionUID = -3562051294845485488L;
	private String table_schema;
	private String table_name;
	private String table_rows;
	private String size;
	private String table_collation;
	private Date create_time;
	private String auto_increment;
	
	public String getTable_schema() {
		return table_schema;
	}
	public void setTable_schema(String table_schema) {
		this.table_schema = table_schema;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getTable_rows() {
		return table_rows;
	}
	public void setTable_rows(String table_rows) {
		this.table_rows = table_rows;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getTable_collation() {
		return table_collation;
	}
	public void setTable_collation(String table_collation) {
		this.table_collation = table_collation;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getAuto_increment() {
		return auto_increment;
	}
	public void setAuto_increment(String auto_increment) {
		this.auto_increment = auto_increment;
	}
}
