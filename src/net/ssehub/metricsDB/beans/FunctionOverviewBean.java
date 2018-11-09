package net.ssehub.metricsDB.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.ssehub.metricsDB.Utils;
import net.ssehub.metricsDB.db.ConnectionPool;
import net.ssehub.metricsDB.dtos.FunctionOverview;

@ManagedBean
@ViewScoped
public class FunctionOverviewBean implements Serializable {

	private static final long serialVersionUID = -905313139229913358L;

	public List<FunctionOverview> getFunctionOverview() {
		List<FunctionOverview> result = new ArrayList<FunctionOverview>();
		
		try (Connection con = ConnectionPool.getConnection()) {
			String sqlAllFunctions = "select count(*) from tbl_functions";
			PreparedStatement stmt = con.prepareStatement(sqlAllFunctions);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			FunctionOverview overview = new FunctionOverview();
			overview.setSeverity("Total No. of Functions");
			overview.setOccurrence(Utils.BIG_INT_FORMAT.format(rs.getLong(1)));
			result.add(overview);
			
			String sqlProblems = "select bug_severity, count(DISTINCT function_id) from tbl_bugs group by tbl_bugs.bug_severity";
			stmt = con.prepareStatement(sqlProblems);
			rs = stmt.executeQuery();
			while (rs.next()) {
				overview = new FunctionOverview();
				overview.setSeverity(rs.getString(1));
				overview.setOccurrence(Utils.BIG_INT_FORMAT.format(rs.getLong(2)));
				result.add(overview);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
