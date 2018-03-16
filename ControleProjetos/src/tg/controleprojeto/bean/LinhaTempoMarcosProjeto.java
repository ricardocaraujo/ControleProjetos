package tg.controleprojeto.bean;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import tg.controleprojeto.modelo.Marco;

@ManagedBean(name="linhaTempo")
@SessionScoped
public class LinhaTempoMarcosProjeto {
	
	private TimelineModel model;
	 
    private boolean selectable = true;
    private boolean zoomable = true;
    private boolean moveable = true;
    private boolean stackEvents = true;
    private String eventStyle = "box";
    private boolean axisOnTop;
    private boolean showCurrentTime = true;
    private boolean showNavigation = false;
 
    @PostConstruct
    protected void initialize() {
        model = new TimelineModel();
    }
 
    public TimelineModel getModel() {
        return model;
    }
 
    public boolean isSelectable() {
        return selectable;
    }
 
    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }
 
    public boolean isZoomable() {
        return zoomable;
    }
 
    public void setZoomable(boolean zoomable) {
        this.zoomable = zoomable;
    }
 
    public boolean isMoveable() {
        return moveable;
    }
 
    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }
 
    public boolean isStackEvents() {
        return stackEvents;
    }
 
    public void setStackEvents(boolean stackEvents) {
        this.stackEvents = stackEvents;
    }
 
    public String getEventStyle() {
        return eventStyle;
    }
 
    public void setEventStyle(String eventStyle) {
        this.eventStyle = eventStyle;
    }
 
    public boolean isAxisOnTop() {
        return axisOnTop;
    }
 
    public void setAxisOnTop(boolean axisOnTop) {
        this.axisOnTop = axisOnTop;
    }
 
    public boolean isShowCurrentTime() {
        return showCurrentTime;
    }
 
    public void setShowCurrentTime(boolean showCurrentTime) {
        this.showCurrentTime = showCurrentTime;
    }
 
    public boolean isShowNavigation() {
        return showNavigation;
    }
 
    public void setShowNavigation(boolean showNavigation) {
        this.showNavigation = showNavigation;
    }
    
    public void adicionaMarco(Marco marco) {
    	System.out.println(marco.getDescricao());
    	model.add(new TimelineEvent(marco.getDescricao(), marco.getData().getTime()));
    }

}
