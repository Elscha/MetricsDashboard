package net.ssehub.metricsDB.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.themeswitcher.ThemeSwitcher;

@ManagedBean
@SessionScoped
public class ThemeSwitcherView {
 
    private List<Theme> themes;
    private String theme = "vader";
 
    @ManagedProperty("#{themeService}")
    private ThemeService service = new ThemeService();
 
    @PostConstruct
    public void init() {
    	service.init();
        themes = service.getThemes();
    }
 
    public List<Theme> getThemes() {
        return themes;
    }
 
    public void setService(ThemeService service) {
        this.service = service;
    }

    public String getTheme() {
		return theme;
	}

    public void setTheme(String theme) {
		this.theme = theme;
	}
    
    public void saveTheme(AjaxBehaviorEvent ajax) {
    	setTheme((String) ((ThemeSwitcher)ajax.getSource()).getValue());
    }
}