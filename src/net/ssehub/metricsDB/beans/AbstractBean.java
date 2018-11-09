package net.ssehub.metricsDB.beans;

import java.io.Serializable;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

@SuppressWarnings("serial")
public abstract class AbstractBean implements Serializable {

	protected void showMessage(Severity severity, String title, String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (null != context) {
			context.addMessage(null, new FacesMessage(severity, title, message));
		} else {
			System.out.println("Context is null");
		}
	}
	
	@SuppressWarnings("unchecked")
	protected <B> B getBean(String elExpression, Class<B> beanType) {
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext elContext = context.getELContext();
		Application app = context.getApplication();
		ExpressionFactory factory = app.getExpressionFactory();
		ValueExpression ve = factory.createValueExpression(elContext, elExpression, beanType);
		return (B) ve.getValue(elContext);
	}
}
