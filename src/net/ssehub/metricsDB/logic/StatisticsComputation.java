package net.ssehub.metricsDB.logic;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import net.ssehub.metricsDB.beans.ProgressBarView;
import net.ssehub.metricsDB.db.ConnectionPool;

public class StatisticsComputation extends Thread implements Serializable {
	
	private static final long serialVersionUID = -8889089249942744382L;
	private String table;
	private String column;
	private int stepSize;
	private ProgressBarView progress;
	private DescriptiveStatistics statistics;
	private boolean continueComputation;
	
	public StatisticsComputation(String table, String column, int stepSize, ProgressBarView progress) {
		this.table = table;
		this.column = column;
		this.stepSize = stepSize;
		this.progress = progress;
	}

	public DescriptiveStatistics compute() {
		continueComputation = true;
		statistics = new DescriptiveStatistics();
		start();
		try {
			join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return statistics;
	}

	@Override
	public void run() {
		try (Connection con = ConnectionPool.getConnection()) {
			
			PreparedStatement stmt = con.prepareStatement("SELECT COUNT(" + column + ") FROM " + table + ";");
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int totalRows = rs.getInt(1);
			if (null != progress) {
				progress.addMax(totalRows);
			}
			
			stmt = con.prepareStatement("SELECT " + column +" FROM " + table + " LIMIT ?,?;");
			for (int i = 0; i < totalRows; i+= stepSize) {
				stmt.setInt(1, i);
				stmt.setInt(2, stepSize);
				
				rs = stmt.executeQuery();
				while (rs.next() && continueComputation) {
					statistics.addValue(rs.getDouble(1));
				}
				
				if (null != progress) {
					progress.addAbsProgress(stepSize);
				}
			}
			
			if (null != progress) {
				progress.setAbsProgress(totalRows);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void abort() {
		continueComputation = false;
	}
}
