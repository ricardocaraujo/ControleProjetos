package tg.controleprojeto.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import tg.controleprojeto.modelo.Usuario;

public class Autorizador implements PhaseListener {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext contexto = event.getFacesContext();
		String pagina = contexto.getViewRoot().getViewId();
		if("/login.xhtml".equals(pagina)) {
			return;
		}
		Usuario usuario = (Usuario) contexto.getExternalContext().getSessionMap().get("usuarioLogado");
		if(usuario != null) {
			return;
		}		
		NavigationHandler handler = contexto.getApplication().getNavigationHandler();
		handler.handleNavigation(contexto, null, "/login?faces-redirect=true");
		contexto.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RESTORE_VIEW;
	}
	

}
