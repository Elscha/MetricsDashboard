package net.ssehub.metricsDB.daos;

public class SQLStatements {
	public static final String FUNCTION = "select tbl_functions.function_id, function_path, function_name, error_count "
	    + "from tbl_functions "
	    + "left join (select count(*) as error_count, function_id from tbl_bugs where bug_severity ='ERROR' "
	    + "group by function_id) AS err_tbl "
	    + "on tbl_functions.function_id = err_tbl.function_id";
	
	
	public static final String FUNCTION_BUG = "select * from tbl_bugs";

	public static final String METRIC = "select * from tbl_metric";
}
