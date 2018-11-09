package net.ssehub.metricsDB.beans;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import net.ssehub.metricsDB.daos.FunctionBugsTable;
import net.ssehub.metricsDB.daos.FunctionTable;
import net.ssehub.metricsDB.dtos.Function;

@ManagedBean
@ViewScoped
public class FunctionBean extends AbstracTabletBean<Function, FunctionTable>{

	private static final long serialVersionUID = 4280386590290624517L;
	private FunctionBugsTable bug;
	
	public FunctionBugsTable getBug() {
		return bug;
	}
 
    public void setSelectedElement(Function selectedElement) {
    	super.setSelectedElement(selectedElement);
        if (null != selectedElement) {
	        bug = new FunctionBugsTable();
			bug.setFunctionid(selectedElement.getId());
        }
    }
    
    public void onRowSelect() {
		 try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("FunctionDetailsView.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

	@Override
	protected FunctionTable createTable() {
		return new FunctionTable();
	}
}
