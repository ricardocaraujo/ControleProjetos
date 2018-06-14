package tg.controleprojeto.bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import tg.controleprojeto.dao.UsuarioDAO;
import tg.controleprojeto.modelo.Usuario;

@ManagedBean
public class LoginMB {
	
	private Usuario usuario;
	
	@PostConstruct
	public void init() {
		this.setUsuario(new Usuario());
	}
	
	public String efetuaLogin() {
		FacesContext contexto = FacesContext.getCurrentInstance();
		boolean existe = new UsuarioDAO().existeUsuario(this.usuario);		
		if(existe) {
			contexto.getExternalContext().getSessionMap().put("usuarioLogado", this.getUsuario());
			return "listaProjeto?faces-redirect=true";
		}		
		contexto.getExternalContext().getFlash().setKeepMessages(true);
		contexto.addMessage(null, new FacesMessage("Usuário não encontrado"));		
		return "login?faces-redirect=true";
	}
	
	
	public String deslogar() {
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.getExternalContext().getSessionMap().remove("usuarioLogado");
		return "login?faces-redirect=true";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

}
