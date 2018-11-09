package net.ssehub.metricsDB.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.ssehub.metricsDB.dtos.FunctionBug;

public class FunctionBugsTable extends AbstractModel<FunctionBug> {
	
	private static final long serialVersionUID = 7600934438917192825L;
	private String functionid;
	
	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	@Override
	protected FunctionBug load(ResultSet rs) throws SQLException {
		FunctionBug bug = new FunctionBug();
		bug.setBug_id(rs.getString(1));
		bug.setFunction_id(rs.getString(2));
		bug.setBug_date(rs.getString(3));
		bug.setBug_repository(rs.getString(4));
		bug.setBug_commit(rs.getString(5));
		bug.setBug_severity(rs.getString(6));
		bug.setBug_line(rs.getString(7));
		bug.setBug_source(rs.getString(8));
		return bug;
	}

	@Override
	protected String getBaseSQL() {
		return SQLStatements.FUNCTION_BUG + " where function_id = " + functionid;
	}

	@Override
	protected String getID(FunctionBug element) {
		return element.getBug_id();
	}

	@Override
	protected String getDefaultOrder() {
		return " order by bug_date asc";
	}
	
	public void sqlQuery() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SQL Query:", getSqlQuery()));
	}
}
