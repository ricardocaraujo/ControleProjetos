package tg.controleprojeto.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import tg.controleprojeto.dao.EmpregadoDAO;
import tg.controleprojeto.dao.LinhaDePesquisaDAO;
import tg.controleprojeto.dao.ProjetoDAO;
import tg.controleprojeto.modelo.Projeto;
import tg.controleprojeto.modelo.Situacao;
import tg.controleprojeto.modelo.Empregado;
import tg.controleprojeto.modelo.LinhaDePesquisa;
import tg.controleprojeto.modelo.Marco;

import org.primefaces.event.FileUploadEvent;


@ManagedBean
@ViewScoped
public class AdicionaProjetoMB {
	
	private Projeto projeto;
	private ProjetoDAO projetoDAO;
	private EmpregadoDAO empregadoDAO;
	private LinhaDePesquisaDAO linhaDePesquisaDAO;
	private List<Integer> idDosCoordenadores;
	private List<Integer> idDosResponsaveisTecnicos;
	private Integer idLinhaDePesquisa;
	private byte[] eap;

 	
	@PostConstruct
	public void init() {		
		this.projetoDAO = new ProjetoDAO();
		this.empregadoDAO = new EmpregadoDAO();
		this.linhaDePesquisaDAO = new LinhaDePesquisaDAO();
		this.carregaProjeto();
	}
	
	public void carregaProjeto() {
		this.projeto = (Projeto) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("projeto");
		if(this.projeto == null) {
			this.projeto = new Projeto();
		} 		
	}
	
	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	
	public List<Empregado> getEmpregados() {
		return empregadoDAO.getEmpregados();
	}
	
	public List<LinhaDePesquisa> getLinhasDePesquisa() {
		return this.linhaDePesquisaDAO.getLinhasDePesquisa();
	}

	public List<Integer> getIdDosCoordenadores() {
		return idDosCoordenadores;
	}

	public void setIdDosCoordenadores(List<Integer> idDosCoordenadores) {
		this.idDosCoordenadores = idDosCoordenadores;
	}
	
	public List<Integer> getIdDosResponsaveisTecnicos() {
		return idDosResponsaveisTecnicos;
	}

	public void setIdDosResponsaveisTecnicos(List<Integer> idDosResponsaveisTecnicos) {
		this.idDosResponsaveisTecnicos = idDosResponsaveisTecnicos;
	}
	
	public Situacao[] getSituacoesPossiveis() {
		return Situacao.values();
	}
	
	public byte[] getEap() {
		return eap;
	}
	
	public void setEap(byte[] eap) {
		this.eap = eap;
	}
	
	public Integer getIdLinhaDePesquisa() {
		return idLinhaDePesquisa;
	}
	
	public void setIdLinhaDePesquisa(Integer idLinhaDePesquisa) {
		this.idLinhaDePesquisa = idLinhaDePesquisa;
	}
	
	public void exibeImagemEap(FileUploadEvent event) {
		this.setEap(event.getFile().getContents());
	}
	
	public String adiciona() {
		List<Empregado> coordenadores = new ArrayList<Empregado>();
		List<Empregado> responsaveisTecnicos = new ArrayList<Empregado>();
		LinhaDePesquisa linhaDePesquisa = linhaDePesquisaDAO.buscaPorId(idLinhaDePesquisa.intValue());
		for(Integer idCoordenador : this.idDosCoordenadores) {
			Empregado coordenador = empregadoDAO.buscaPorId(idCoordenador.intValue());
			coordenadores.add(coordenador);
		}
		for(Integer idRespTecnico : this.idDosResponsaveisTecnicos) {
			Empregado respTecnico = empregadoDAO.buscaPorId(idRespTecnico.intValue());
			responsaveisTecnicos.add(respTecnico);
		}
		for(Marco marco:this.projeto.getMarcos()) {
			marco.setProjeto(projeto);
		}
		this.projeto.setCoordenadores(coordenadores);
		this.projeto.setResponsaveisTecnicos(responsaveisTecnicos);
		this.projeto.setEap(this.eap);
		this.projeto.setLinhaDePesquisa(linhaDePesquisa);		
		this.projetoDAO.adiciona(projeto);		
		return "listaProjeto?faces-redirect=true";	
	}
	
	public void removeMarco(Marco marco) {
		this.projeto.getMarcos().remove(marco); 
	}
	
	public void adicionaMarco(AjaxBehaviorEvent event) {
		this.projeto.getMarcos().add(new Marco());
	}
	
}
