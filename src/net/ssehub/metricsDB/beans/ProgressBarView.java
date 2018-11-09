package net.ssehub.metricsDB.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "progress")
@SessionScoped
public class ProgressBarView implements Serializable {
     
	private static final long serialVersionUID = -6178771216833848438L;
	private Integer absProgress = 0;
	private int progress = 0;
	private Float max = 0f;
	private boolean hasState;
	
	public boolean isHasState() {
		return hasState;
	}

	public void setHasState(boolean hasState) {
		this.hasState = hasState;
	}

 
    public int getProgress() {
    	return progress;
    }
    
    private void compute() {
    	if (absProgress != null && max != null && max != null) {
    		progress = (int) ((absProgress / max) * 100);
    		
    		if (progress > 100) {
    			progress = 100;
    		}
    	}
    }
    
    public void setMax(float max) {
    	hasState = true;
    	this.max = max;
    }
    public void addMax(float max) {
    	hasState = true;
    	this.max += max;
    }
 
    public void setAbsProgress(Integer absProgress) {
    	hasState = true;
        this.absProgress = absProgress;
        compute();
    }
    
    public void addAbsProgress(Integer absProgress) {
    	hasState = true;
    	this.absProgress += absProgress;
    	compute();
    }
     
    public void reset() {
    	hasState = false;
    	absProgress = 0;
    	progress = 0;
    }
}
