package net.ssehub.metricsDB.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import net.ssehub.metricsDB.Utils;
import net.ssehub.metricsDB.dtos.DatabaseInfo;
import net.ssehub.metricsDB.dtos.DbSettings;

/**
 * <a href="https://stackoverflow.com/q/41575998">https://stackoverflow.com/q/41575998</a>
 * @author el-sharkawy
 *
 */
public class DatabaseInfoTable extends AbstractModel<DatabaseInfo> {
	private static final long serialVersionUID = 8026673496141274798L;
	private String schemaName;
	
	public DatabaseInfoTable() {
		DbSettings settings = new DbSettings();
		String url = settings.getUrl();
		int pos = url.lastIndexOf('/');
		schemaName = url.substring(pos + 1, url.length());
	}

	@Override
	protected String getBaseSQL() {
		return "SELECT \r\n" + 
			"     table_schema, table_name, \r\n" + 
//			"     round(((data_length + index_length) / 1024 / 1024), 2) `size`,\r\n" + 
			"     (data_length + index_length) `size`,\r\n" + 
			"     TABLE_ROWS as table_rows,\r\n" + 
			"     TABLE_COLLATION as table_collation,\r\n" + 
			"     CREATE_TIME as create_time,\r\n" + 
			"     AUTO_INCREMENT as auto_increment\r\n" + 
			"FROM information_schema.TABLES";
	}
	
	@Override
	public List<DatabaseInfo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		filters = new HashMap<>();
		filters.put("table_schema", schemaName);
		return super.load(first, pageSize, sortField, sortOrder, filters);
	}

	@Override
	protected String getID(DatabaseInfo element) {
		return element.getTable_name();
	}

	@Override
	protected DatabaseInfo load(ResultSet rs) throws SQLException {
		DatabaseInfo info = new DatabaseInfo();
		info.setTable_schema(rs.getString(1));
		info.setTable_name(rs.getString(2));
		
		// Size of Table
		double size = rs.getDouble(3);
		int sizeIndex = 0;
		while (size > 1024) {
			sizeIndex++;
			size /= 1024;
		}
		String sizeStr = Utils.FLOAT_FORMAT.format(size);
		info.setSize(sizeStr + " " + Utils.SIZES[sizeIndex]);
		
		String rowStr = Utils.BIG_INT_FORMAT.format(rs.getLong(4));
		info.setTable_rows(rowStr);
		info.setTable_collation(rs.getString(5));
		info.setCreate_time(rs.getDate(6));
		info.setAuto_increment(Utils.BIG_INT_FORMAT.format(rs.getLong(7)));

		return info;
	}

	@Override
	protected String getDefaultOrder() {
		return " order by table_schema ASC, table_name asc";
	}

}
