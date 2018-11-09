package net.ssehub.metricsDB.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.ssehub.metricsDB.dtos.Function;

public class FunctionTable extends AbstractModel<Function> {
	
	private static final long serialVersionUID = -7162739711023264715L;

	@Override
	protected String getBaseSQL() {
		return SQLStatements.FUNCTION;
	}

	@Override
	protected String getID(Function element) {
		return element.getId();
	}

	@Override
	protected Function load(ResultSet rs) throws SQLException {
		Function func = new Function();
		func.setId(rs.getString(1));
		func.setPath(rs.getString(2));
		func.setName(rs.getString(3));
		func.setErrors(rs.getInt(4));
		return func;
	}

	@Override
	protected String getDefaultOrder() {
		return " order by function_path asc, function_name asc";
	}
	
	@Override
	protected String mapColumnName(String columnName) {
		if (columnName.equals("errors")) {
			columnName = "error_count";
		} else {
			columnName = "function_" + columnName;
		}
		return columnName;
	}
}
