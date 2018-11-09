package net.ssehub.metricsDB.daos;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.ssehub.metricsDB.dtos.Metric;

public class MetricsTable extends AbstractModel<Metric> {
	
	private static final long serialVersionUID = 4784792820408206081L;
	
	@Override
	protected Metric load(ResultSet rs) throws SQLException {
		Metric metric = new Metric();
		metric.setMetric_id(rs.getString(1));
		metric.setMetric_name(rs.getString(2));
		return metric;
	}

	@Override
	protected String getBaseSQL() {
		return SQLStatements.METRIC;
	}
	
	@Override
	protected String getID(Metric element) {
		return element.getMetric_id();
	}
	
	@Override
	protected String getDefaultOrder() {
		return " order by metric_name";
	}
}
