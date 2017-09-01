package tg.controleprojeto.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import tg.controleprojeto.modelo.Projeto;

@ManagedBean
@ViewScoped
public class VisualizaProjetoMB {

	private Projeto projeto;
	
	@ManagedProperty("#{ListaProjetoMB}")
	private ListaProjetoMB listaProjetoMB;
	
	@PostConstruct
	public void init() {
		System.out.println("entrou aqui 1");
		this.projeto = this.listaProjetoMB.getProjeto();
	}
	
	public Projeto getProjeto() {
		System.out.println("entrou aqui 2");
		return this.projeto;
	}
	
	
}
