package net.ssehub.metricsDB.dtos;

import java.io.Serializable;

public class Metric implements Serializable {
	
	private static final long serialVersionUID = 4539530274352394391L;
	private String metric_id;
	private String metric_name;
	
	public String getMetric_id() {
		return metric_id;
	}
	public void setMetric_id(String metric_id) {
		this.metric_id = metric_id;
	}
	public String getMetric_name() {
		return metric_name;
	}
	public void setMetric_name(String metric_name) {
		this.metric_name = metric_name;
	}

	
}
