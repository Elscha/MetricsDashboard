package net.ssehub.metricsDB.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import net.ssehub.metricsDB.db.ConnectionPool;

@SuppressWarnings("serial")
public abstract class AbstractModel<T> extends LazyDataModel<T> {

	private Map<String, T> cachedElements = new HashMap<>();
	private T selectedElement;
	private boolean debug = false;
	
	private String sqlQuery;
	
	public String getSqlQuery() {
        return sqlQuery;
    }

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		cachedElements.clear();
		List<T> result = new ArrayList<>();
		
		try (Connection con = ConnectionPool.getConnection()) {
			StringBuffer sql = new StringBuffer(getBaseSQL());
			
			if (null != filters && !filters.isEmpty()) {
				sql.append(" WHERE ");
				boolean firstFilter = true;
				for (Entry<String, Object> filter : filters.entrySet()) {
					if (firstFilter) {
						firstFilter = false;
					} else {
						sql.append(" AND ");
					}
					sql.append(mapColumnName(filter.getKey()));
					sql.append(" LIKE ?");
				}
			}
			if (null == sortField) {
				sql.append(getDefaultOrder());
			} else {
				sql.append(" ORDER BY ");
				sql.append(mapColumnName(sortField));
				String ordering = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
				sql.append(" ");
				sql.append(ordering);
			}
			
			// Compute no of pages
			int nFilters = (null != filters) ? filters.size() : 0;
			String[] filterValues = new String[nFilters];
			if (nFilters > 0) {
				int filterIndex = 0;
				for (Entry<String, Object> filter : filters.entrySet()) {
					filterValues[filterIndex++] = "%" + filter.getValue().toString() + "%";
				}
			}
			countResultSize(sql.toString(), (Object[]) filterValues);
			
			sql.append(" LIMIT ?,?;");
			
			PreparedStatement stmt = con.prepareStatement(sql.toString());
			int paramIndex = 1;
			for (String filterValue : filterValues) {
				stmt.setString(paramIndex++, filterValue);
			}
			stmt.setInt(paramIndex++, first);
			stmt.setInt(paramIndex++, pageSize);
			
			if (debug) {
				System.out.println(stmt);
			}
			
			Object[] params = new Object[nFilters + 2];
			System.arraycopy(filterValues, 0, params, 0, nFilters);
			params[params.length - 2] = first;
			params[params.length - 1] = pageSize;
			setSQLQuery(sql, (Object[]) params);
			load(result, stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (T element : result) {
			cachedElements.put(getID(element), element);
		}
		
		return result;
	}
	
	private void setSQLQuery(StringBuffer sql, Object... values) {
		StringBuffer instanciatedSQL = new StringBuffer();
		int index = 0;
		for (int i = 0; i < sql.length(); i++) {
			char c = sql.charAt(i);
			switch (c) {
				case '?':
					Object value = values[index++];
					if (value instanceof String) {
						value = "'" + (String) value + "'";
					}
					instanciatedSQL.append(value);
					break;
				default:
					instanciatedSQL.append(c);
					break;
			}
		}
		sqlQuery = instanciatedSQL.toString();
	}
	
	protected boolean isDebug() {
		return debug;
	}

	protected void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void countResultSize(String sql, Object... param) {
		try (Connection con = ConnectionPool.getConnection()) {
			StringBuffer countSql = new StringBuffer("SELECT COUNT(*) FROM (");
			countSql.append(sql);
			countSql.append(") AS row_count_tbl");
			PreparedStatement stmt = con.prepareStatement(countSql.toString());
			
			int paramIndex = 1;
			if (null != param && param.length > 0) {
				for (Object object : param) {
					if (object instanceof Integer) {
						stmt.setInt(paramIndex++, (Integer) object);
					} else {
						stmt.setString(paramIndex++, object.toString());
					}
				}
			}
			
			ResultSet rs = stmt.executeQuery();
			rs.next();
			setRowCount(rs.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
    public T getRowData(String rowKey) {
		return cachedElements.get(rowKey);
    }
	
	@Override
    public Object getRowKey(T element) {
        return getID(element);
    }
	
	public T getSelectedElement() {
		return selectedElement;
	}

	public void setSelectedElement(T selectedElement) {
		this.selectedElement = selectedElement;
	}
	
	protected abstract String getBaseSQL();
	
	protected abstract String getID(T element);
	
	private void load(List<T> result, PreparedStatement stmt) throws SQLException {
		try (ResultSet rs = stmt.executeQuery();) {
			while(rs.next()) {
				result.add(load(rs));
			}
		}
	}
	
	protected abstract T load(ResultSet rs) throws SQLException;
	
	protected abstract String getDefaultOrder();
	
	protected String mapColumnName(String columnName) {
		return columnName;
	}
}
