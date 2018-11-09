package net.ssehub.metricsDB.dtos;

public class FunctionBug {
	
	private String bug_id;
	private String function_id;
	private String bug_date;
	private String bug_repository;
	private String bug_commit;
	private String bug_severity;
	private String bug_line;
	private String bug_source;
	
	public String getBug_id() {
		return bug_id;
	}
	public void setBug_id(String bug_id) {
		this.bug_id = bug_id;
	}
	public String getFunction_id() {
		return function_id;
	}
	public void setFunction_id(String function_id) {
		this.function_id = function_id;
	}
	public String getBug_date() {
		return bug_date;
	}
	public void setBug_date(String bug_date) {
		this.bug_date = bug_date;
	}
	public String getBug_repository() {
		return bug_repository;
	}
	public void setBug_repository(String bug_repository) {
		this.bug_repository = bug_repository;
	}
	public String getBug_commit() {
		return bug_commit;
	}
	public void setBug_commit(String bug_commit) {
		this.bug_commit = bug_commit;
	}
	public String getBug_severity() {
		return bug_severity;
	}
	public void setBug_severity(String bug_severity) {
		this.bug_severity = bug_severity;
	}
	public String getBug_line() {
		return bug_line;
	}
	public void setBug_line(String bug_line) {
		this.bug_line = bug_line;
	}
	public String getBug_source() {
		return bug_source;
	}
	public void setBug_source(String bug_source) {
		this.bug_source = bug_source;
	}

}
