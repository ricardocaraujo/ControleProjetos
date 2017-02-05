package tg.controleprojeto.bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import tg.controleprojeto.modelo.Usuario;

@ManagedBean
public class LoginMB {
	
	Usuario usuario;
	
	@PostConstruct
	public void init() {
		this.usuario = new Usuario();
	}
	
	/*public String efetuaLogin() {
		FacesContext contexto = FacesContext.getCurrentInstance();
		boolean existe = new UsuarioDAO().existe(this.usuario);
		
		if(existe) {
			contexto.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "adicionaProjeto?faces-redirect=true";
		}
		
		contexto.getExternalContext().getFlash().setKeepMessages(true);
		contexto.addMessage(null, new FacesMessage("Usuário não encontrado"));
		
		return "login?faces-redirect=true";
	}*/
	
	public String deslogar() {
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.getExternalContext().getSessionMap().remove(this.usuario);
		return "login?faces-redirect=true";
	}
	

}
