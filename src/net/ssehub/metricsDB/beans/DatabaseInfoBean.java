package net.ssehub.metricsDB.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.ssehub.metricsDB.daos.DatabaseInfoTable;
import net.ssehub.metricsDB.dtos.DatabaseInfo;

@ManagedBean
@ViewScoped
public class DatabaseInfoBean extends AbstracTabletBean<DatabaseInfo, DatabaseInfoTable> {

	private static final long serialVersionUID = 1971682330797173475L;

	@Override
	protected DatabaseInfoTable createTable() {
		return new DatabaseInfoTable();
	}

}
