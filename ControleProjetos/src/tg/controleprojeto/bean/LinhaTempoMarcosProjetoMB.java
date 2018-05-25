package tg.controleprojeto.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.timeline.TimelineUpdater;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import tg.controleprojeto.modelo.Marco;

@ManagedBean(name="linhaTempo")
@ViewScoped
public class LinhaTempoMarcosProjetoMB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TimelineModel model;
	

	 
    private boolean selectable = true;
    private boolean zoomable = true;
    private boolean moveable = true;
    private boolean stackEvents = true;
    private String eventStyle = "box";
    private boolean axisOnTop;
    private boolean showCurrentTime = true;
    private boolean showNavigation = false;
	private Calendar dataInicio;
    private Calendar dataFim;
 
    @PostConstruct
    protected void initialize() {
        this.model = new TimelineModel();
    	
        this.inicializaDatas();
    }
 
    private void inicializaDatas() {
    	dataInicio = Calendar.getInstance();
    	int mesInicio = dataInicio.get(2) - 2;
		dataInicio.set(2, mesInicio);
    	dataFim = Calendar.getInstance();
    	int anoFim = dataFim.get(1) + 2;
    	dataFim.set(1, anoFim);
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
    
    public Calendar getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Calendar getDataFim() {
		return dataFim;
	}

	public void setDataFim(Calendar dataFim) {
		this.dataFim = dataFim;
	}
    
    public void adicionaMarco(Marco marco) {
    	TimelineEvent evento = new TimelineEvent(marco.getDescricao(), marco.getData().getTime());
    	TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":formulario:timeline");
    	model.add(evento, timelineUpdater);
    }

	public void removeMarco(Marco marco) {
		TimelineEvent evento = new TimelineEvent(marco.getDescricao(), marco.getData().getTime());
		TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":formulario:timeline");
		model.delete(evento, timelineUpdater);
	}



}
