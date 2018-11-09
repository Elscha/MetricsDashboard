package net.ssehub.metricsDB.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.ssehub.metricsDB.dtos.Function;

public class IssuedFunctionTable extends AbstractModel<Function> {
	
	private static final long serialVersionUID = -7162739711023264715L;

	@Override
	protected String getBaseSQL() {
		return "select tbl_functions.*, measures from (select function_id, count(distinct bug_commit) as measures from tbl_bugs group by function_id) tbl_measures left join tbl_functions on tbl_measures.function_id = tbl_functions.function_id";
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
			columnName = "measures";
		} else {
			columnName = "function_" + columnName;
		}
		return columnName;
	}
}
