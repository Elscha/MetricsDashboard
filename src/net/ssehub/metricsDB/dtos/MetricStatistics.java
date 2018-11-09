package net.ssehub.metricsDB.dtos;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import net.ssehub.metricsDB.Utils;

public class MetricStatistics implements Serializable {

	private static final long serialVersionUID = 8091405040952374035L;
	
	private DescriptiveStatistics delegate;
	private String time;
	private String id;
	
	public MetricStatistics(DescriptiveStatistics delegate, long millis, String id) {
		this.delegate = delegate;
		this.id = id;
		
		//hh:mm:ss
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		long min = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours);
		long sec = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(min);
//		this.time = String.format("%02d:%02d:%02d", hours, min, sec);
		this.time = String.format("%02d:%02d Min", min, sec);
	}
	
	public String getMean() {
		return toString(delegate.getMean());
	}
	
	
	public String getVariance() {
		return toString(delegate.getVariance());
	}
	
	public String getStandardDeviation() {
		return toString(delegate.getStandardDeviation());
	}
	
	public String getMax() {
		return toString(delegate.getMax());
	}
	
	public String getMin() {
		return toString(delegate.getMin());
	}
	
	public String getN() {
		return toString(delegate.getN());
	}
	
	public String getSum() {
		return toString(delegate.getSum());
	}
	
	public String getPercentile25() {
		return toString(getPercentile25Value());
	}
	
	public String getPercentile50() {
		return toString(getPercentile50Value());
	}
	public String getPercentile75() {
		return toString(getPercentile50Value());
	}
	
	public double getMeanValue() {
		return delegate.getMean();
	}
	
	public double getVarianceValue() {
		return delegate.getVariance();
	}
	
	public double getStandardDeviationValue() {
		return delegate.getStandardDeviation();
	}
	
	public double getMaxValue() {
		return delegate.getMax();
	}
	
	public double getMinValue() {
		return delegate.getMin();
	}
	
	public double getNValue() {
		return delegate.getN();
	}
	
	public double getSumValue() {
		return delegate.getSum();
	}
	
	public double getPercentile25Value() {
		return delegate.getPercentile(25);
	}
	
	public double getPercentile50Value() {
		return delegate.getPercentile(50);
	}
	
	public double getPercentile75Value() {
		return delegate.getPercentile(75);
	}
	
	public String getTime() {
		return time;
	}
	
	private String toString(double value) {
		String result;
		if ((value == Math.floor(value)) && !Double.isInfinite(value)) {
			result = Utils.BIG_INT_FORMAT.format(value);
		} else {
			result = Utils.FLOAT_FORMAT.format(value);
		}
		
		return result;
	}

	public String getId() {
		return id;
	}
}
