package net.ssehub.metricsDB.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.ssehub.metricsDB.daos.LinuxOverviewTable;
import net.ssehub.metricsDB.dtos.LinuxOverview;

@ManagedBean
@ViewScoped
public class LinuxOverviewBean extends AbstracTabletBean<LinuxOverview, LinuxOverviewTable> {
	private static final long serialVersionUID = 6076558409042310731L;

	@Override
	protected LinuxOverviewTable createTable() {
		return new LinuxOverviewTable();
	}
}
