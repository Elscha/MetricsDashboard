package net.ssehub.metricsDB.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.ssehub.metricsDB.Utils;
import net.ssehub.metricsDB.dtos.LinuxOverview;

public class LinuxOverviewTable extends AbstractModel<LinuxOverview> {

	private static final long serialVersionUID = 7628849029306498452L;

	@Override
	protected String getBaseSQL() {
		return "select tbl_linux.*, measures from tbl_linux left join "
			+ "(select count(*) as measures, linux_id from tbl_linux_measure) as count_tbl "
			+ "on tbl_linux.linux_id = count_tbl.linux_id";
	}

	@Override
	protected String getID(LinuxOverview element) {
		return element.getLinux_id();
	}

	@Override
	protected LinuxOverview load(ResultSet rs) throws SQLException {
		LinuxOverview overview = new LinuxOverview();
		overview.setLinux_id(rs.getString(1));
		overview.setVersion(rs.getString(2));
		overview.setMeasures(Utils.BIG_INT_FORMAT.format(rs.getLong(3)));
		return overview;
	}

	@Override
	protected String getDefaultOrder() {
		return " order by version";
	}

}
