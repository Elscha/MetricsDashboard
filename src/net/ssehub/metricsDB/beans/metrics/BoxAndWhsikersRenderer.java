package net.ssehub.metricsDB.beans.metrics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.ui.RectangleEdge;

public class BoxAndWhsikersRenderer extends org.jfree.chart.renderer.category.BoxAndWhiskerRenderer {

	private static final long serialVersionUID = 7889339262627845848L;

	
	@Override
	public void drawVerticalItem(Graphics2D g2, CategoryItemRendererState state,
	        Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis,
	        ValueAxis rangeAxis, CategoryDataset dataset, int row, int column) {
		
		boolean meanVisible = isMeanVisible();
		if (meanVisible) {
			setMeanVisible(false);
		}
		super.drawVerticalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column);
		
        if (meanVisible) {
        	double categoryEnd = domainAxis.getCategoryEnd(column, getColumnCount(), dataArea, plot.getDomainAxisEdge());
            double categoryStart = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, plot.getDomainAxisEdge());
            double categoryWidth = categoryEnd - categoryStart;

            double xx = categoryStart;
            int seriesCount = getRowCount();
            int categoryCount = getColumnCount();
        	RectangleEdge location = plot.getRangeAxisEdge();
        	BoxAndWhiskerCategoryDataset bawDataset = (BoxAndWhiskerCategoryDataset) dataset;
        	Number yMean = bawDataset.getMeanValue(row, column);
        	if (seriesCount > 1) {
                double seriesGap = dataArea.getWidth() * getItemMargin()
                                   / (categoryCount * (seriesCount - 1));
                double usedWidth = (state.getBarWidth() * seriesCount)
                                   + (seriesGap * (seriesCount - 1));
                // offset the start of the boxes if the total width used is smaller
                // than the category width
                double offset = (categoryWidth - usedWidth) / 2;
                xx = xx + offset + (row * (state.getBarWidth() + seriesGap));
            }
            else {
                // offset the start of the box if the box width is smaller than the
                // category width
                double offset = (categoryWidth - state.getBarWidth()) / 2;
                xx = xx + offset;
            }
        	
            if (yMean != null) {
            	g2.setColor(Color.BLACK);
            	double yyAverage = rangeAxis.valueToJava2D(yMean.doubleValue(), dataArea, location);
            	int sizeFactor = 25;
            	double aRadius = state.getBarWidth() / sizeFactor;
            	
                // here we check that the average marker will in fact be
                // visible before drawing it...
                if ((yyAverage > (dataArea.getMinY() - aRadius)) && (yyAverage < (dataArea.getMaxY() + aRadius))) {
                    Ellipse2D.Double avgEllipse = new Ellipse2D.Double(xx + (state.getBarWidth() / 2) - aRadius, yyAverage - aRadius, aRadius * 2, aRadius * 2);
                    g2.fill(avgEllipse);
                    g2.draw(avgEllipse);
                }
            }
            
            
            setMeanVisible(meanVisible);
        }
	}
}
