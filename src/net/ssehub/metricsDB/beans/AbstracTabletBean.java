package net.ssehub.metricsDB.beans;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;

import net.ssehub.metricsDB.daos.AbstractModel;

@SuppressWarnings("serial")
public abstract class AbstracTabletBean<E, T extends AbstractModel<E>>  extends AbstractBean {

	private T table = createTable();
	private E selectedElement;
	
	public T getTable() {
		return table;
	}

	protected abstract T createTable();

	public E getSelectedElement() {
        return selectedElement;
    }
 
    public void setSelectedElement(E selectedElement) {
        this.selectedElement = selectedElement;
    }
    
    @SuppressWarnings("unchecked")
	public void onRowSelect(SelectEvent event) {
    	setSelectedElement((E) (event.getObject()));
    }
    
    public void sqlQuery(ActionEvent actionEvent) {
    	sqlQuery();
    }
    
    
	public void sqlQuery() {
		showMessage(FacesMessage.SEVERITY_INFO, "SQL Query:", getTable().getSqlQuery());
	}
}
