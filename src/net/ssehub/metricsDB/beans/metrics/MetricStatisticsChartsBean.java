package net.ssehub.metricsDB.beans.metrics;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;

import net.ssehub.metricsDB.beans.AbstractBean;
import net.ssehub.metricsDB.beans.MetricsBean;
import net.ssehub.metricsDB.dtos.MetricStatistics;

@ManagedBean
@ViewScoped
public class MetricStatisticsChartsBean extends AbstractBean {

	private static final long serialVersionUID = -7363979620289022131L;

	private BarChartModel barModel;
	private StreamedContent chart;
	
	@ManagedProperty("#{metricsBean}")
	private MetricsBean service;
	
	public CartesianChartModel getBarModel() {
        return barModel;
    }
	
	public StreamedContent getChart() {
        return chart;
    }
	
	public void setService(MetricsBean service) {
		this.service = service;
	}
	
	@PostConstruct
    public void init() {
		createBarModel();
		createBoxPlot();
    }
	
	public void createBoxPlot() {
		MetricStatistics[] statistics = service.getMetricStats();
		try {
			// Data
			DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
			BoxAndWhiskerItem item = new BoxAndWhiskerItem(
				statistics[0].getMeanValue(),
				null,
				statistics[0].getMinValue(), statistics[0].getMaxValue(),
//				statistics[0].getPercentile25Value(),
//				statistics[0].getPercentile75Value(),
				statistics[0].getMinValue(),
				statistics[0].getMaxValue(), null, null, null);
			BoxAndWhiskerItem item2 = new BoxAndWhiskerItem(
				statistics[1].getMeanValue(),
				null,
				statistics[1].getMinValue(), statistics[1].getMaxValue(),
//				statistics[1].getPercentile25Value(),
//				statistics[1].getPercentile75Value(),
				statistics[1].getMinValue(),
				statistics[1].getMaxValue(), null, null, null);
			dataset.add(item, "All Functions", "Metrics");
			dataset.add(item2, "Errors", "Metrics");
			
            //Chart
			BoxAndWhsikersRenderer renderer = new BoxAndWhsikersRenderer();
			renderer.setMeanVisible(true);
			renderer.setMedianVisible(true);
			CategoryPlot plot = new CategoryPlot(dataset, new CategoryAxis(), new NumberAxis(), renderer);
			JFreeChart chart = new JFreeChart("Statistics", new Font("SansSerif", Font.BOLD, 14), plot, true);
            File chartFile = new File("dynamichart");
            ChartUtilities.saveChartAsPNG(chartFile, chart, 375, 300);
            this.chart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private void createBarModel() {
		MetricStatistics[] statistics = service.getMetricStats();
		barModel = new BarChartModel(); 
		barModel.addSeries(createSeries("All Functions", statistics[0]));
		barModel.addSeries(createSeries("Errors", statistics[1]));
         
        barModel.setTitle("Metric Statistics");
        barModel.setLegendPosition("e");
        barModel.setZoom(true);
        barModel.setLegendPlacement(LegendPlacement.OUTSIDEGRID);

        barModel.setSeriesColors("0000FF,FF0000");
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Statistics");
        barModel.setExtender("chartExtender");
        
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Values");
        yAxis.setMin(0);
//        yAxis.setMax(200);
    }
	
	private ChartSeries createSeries(String seriesName, MetricStatistics statistics) {
		ChartSeries series = new ChartSeries();
		series.setLabel(seriesName);
//		series.set("Min", statistics.getMinValue());
		series.set("Mean", statistics.getMeanValue());
		series.set("Max", statistics.getMaxValue());
		series.set("Var", statistics.getVarianceValue());
		series.set("SD", statistics.getStandardDeviationValue());
		
		return series;
	}
}
