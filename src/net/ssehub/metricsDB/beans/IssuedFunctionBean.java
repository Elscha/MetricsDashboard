package net.ssehub.metricsDB.beans;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import net.ssehub.metricsDB.daos.IssuedFunctionTable;
import net.ssehub.metricsDB.dtos.Function;

@ManagedBean
@ViewScoped
public class IssuedFunctionBean extends AbstracTabletBean<Function, IssuedFunctionTable> {

	private static final long serialVersionUID = -3438955474594612689L;

	@Override
	protected IssuedFunctionTable createTable() {
		return new IssuedFunctionTable();
	}
	
	public void onRowSelect() {
		 try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("FunctionDetailsView.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

}
