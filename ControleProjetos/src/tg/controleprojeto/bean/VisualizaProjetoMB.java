package tg.controleprojeto.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import tg.controleprojeto.modelo.Projeto;

@ManagedBean
@ViewScoped
public class VisualizaProjetoMB implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private Projeto projeto;
	
	@ManagedProperty(value="#{listaProjetoMB}")
	private ListaProjetoMB listaProjetoMB;
	
	@PostConstruct
	public void init() {
		this.projeto = this.listaProjetoMB.getProjeto();
	}
	
	public Projeto getProjeto() {
		return this.projeto;
	}

	public void setListaProjetoMB(ListaProjetoMB listaProjetoMB) {
		this.listaProjetoMB = listaProjetoMB;
	}
	
}
