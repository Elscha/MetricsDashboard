package net.ssehub.metricsDB.beans;

import java.io.IOException;

import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.primefaces.event.SelectEvent;

import net.ssehub.metricsDB.daos.MetricsTable;
import net.ssehub.metricsDB.dtos.Metric;
import net.ssehub.metricsDB.dtos.MetricStatistics;
import net.ssehub.metricsDB.logic.StatisticsComputation;

@ManagedBean
@SessionScoped
public class MetricsBean extends AbstracTabletBean<Metric, MetricsTable> {

	private static final long serialVersionUID = -809546240290666740L;
	private StatisticsComputation metricStatisticsComputation;
	private MetricStatistics[] metricStats;
	private boolean metricStatsAvailable = false;

	@ManagedProperty("#{progress}")
    private ProgressBarView progress;
	
	public void setProgress(ProgressBarView progress) {
		this.progress = progress;
	}

	public ProgressBarView getProgress() {
		return progress;
	}

	public void onRowSelect() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("MetricDetailsView.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

	@Override
	protected MetricsTable createTable() {
		return new MetricsTable();
	}
	
	public void onRowSelect(SelectEvent event) {
		destroy();
		super.onRowSelect(event);
	}
	
	@PreDestroy
    public void destroy() {
		if (null != metricStatisticsComputation) {
			metricStatisticsComputation.abort();
		}
	}
	
	public void statistics() {
		String id = getSelectedElement().getMetric_id();
		setMetricStats(null);
		
		// Over all functions
		long t0 = System.currentTimeMillis();
		MetricStatistics[] stats = new MetricStatistics[2];
		
		progress.reset();
		metricStatisticsComputation = new StatisticsComputation(
			"tbl_linux_measure_values where metric_id = " + id,
			"value",
			25000,
			progress);
		
		DescriptiveStatistics statistics = metricStatisticsComputation.compute();
		t0 = System.currentTimeMillis() - t0;
		stats[0] = new MetricStatistics(statistics, t0, id);
		progress.reset();
		
		// Over bugs
		t0 = System.currentTimeMillis();
		metricStatisticsComputation = new StatisticsComputation(
			"(SELECT bug_id FROM tbl_bugs WHERE bug_severity = 'ERROR' GROUP BY bug_commit) "
			+ "AS tbl_filtered_bugs LEFT JOIN (SELECT * FROM tbl_error_measures WHERE metric_id = "
			+ id + ") AS tbl_filtered_values ON tbl_filtered_bugs.bug_id = tbl_filtered_values.bug_id",
			"value",
			25000,
			null);
		statistics = metricStatisticsComputation.compute();
		t0 = System.currentTimeMillis() - t0;
		stats[1] = new MetricStatistics(statistics, t0, id);
		setMetricStats(stats);
	}
	
	public MetricStatistics[] getMetricStats() {
		if (!isMetricStatsAvailable()) {
			setMetricStats(null);
		}
		return metricStats;
	}

	public void setMetricStats(MetricStatistics[] metricStats) {
		this.metricStats = metricStats;
		checkMetricStats();
	}
	
	public boolean isMetricStatsAvailable() {
		return metricStatsAvailable;
	}

	public void setMetricStatsAvailable(boolean metricStatsAvailable) {
		this.metricStatsAvailable = metricStatsAvailable;
	}
	
	private void checkMetricStats() {
		boolean allOK = metricStats != null && null != getSelectedElement();
		
		if (allOK) {
			String id = getSelectedElement().getMetric_id();
			
			for (MetricStatistics metricStatistics : metricStats) {
				allOK &= id.equals(metricStatistics.getId());
				
				if (!allOK) {
					break;
				}
			}
		}
		
		setMetricStatsAvailable(allOK);
	}
}
