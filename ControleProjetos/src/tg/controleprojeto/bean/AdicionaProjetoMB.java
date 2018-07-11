package tg.controleprojeto.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import tg.controleprojeto.dao.EmpregadoDAO;
import tg.controleprojeto.dao.LinhaDePesquisaDAO;
import tg.controleprojeto.dao.ProjetoDAO;
import tg.controleprojeto.modelo.Projeto;
import tg.controleprojeto.modelo.Situacao;
import tg.controleprojeto.ws.client.ProjetoCliente;
import tg.controleprojeto.modelo.Empregado;
import tg.controleprojeto.modelo.LinhaDePesquisa;
import tg.controleprojeto.modelo.Marco;

import org.primefaces.event.FileUploadEvent;




@ManagedBean(name="adicionaProjetoMB")
@ViewScoped
public class AdicionaProjetoMB implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private Projeto projeto;
	private ProjetoDAO projetoDAO;
	private EmpregadoDAO empregadoDAO;
	private LinhaDePesquisaDAO linhaDePesquisaDAO;
	private List<Integer> idDosCoordenadores;
	private List<Integer> idDosResponsaveisTecnicos;
	private Integer idLinhaDePesquisa;
	private byte[] eap;
		
	@ManagedProperty(value="#{linhaTempo}")
	private LinhaTempoMarcosProjetoMB linhaTempo;

	@PostConstruct
	public void init() {		
		this.projetoDAO = new ProjetoDAO();
		this.empregadoDAO = new EmpregadoDAO();
		this.linhaDePesquisaDAO = new LinhaDePesquisaDAO();		
		this.carregaProjeto();
	}

	public LinhaTempoMarcosProjetoMB getLinhaTempo() {
		return linhaTempo;
	}

	public void setLinhaTempo(LinhaTempoMarcosProjetoMB linhaTempo) {
		this.linhaTempo = linhaTempo;
	}

	public void carregaProjeto() {
		if(FacesContext.getCurrentInstance().getExternalContext().getFlash().get("projeto") != null) {
			this.projeto = (Projeto) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("projeto");
		} else {
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
		if(projeto.getId() != null) {
			this.projetoDAO.altera(this.projeto);
		} else {
			new ProjetoCliente().adicionaProjeto(this.projeto);
		}			
		return "listaProjeto?faces-redirect=true";	
	}
	
	public void removeMarco(Marco marco) {		
		this.linhaTempo.removeMarco(marco);
		this.projeto.getMarcos().remove(marco);
	}
	
	public void adicionaMarco() {
		int indice = this.projeto.getMarcos().size();
		if(indice == 0) {
			this.projeto.getMarcos().add(new Marco());	
		} else {
			Marco marco = this.projeto.getMarcos().get(indice - 1);
			this.linhaTempo.adicionaMarco(marco);
			this.projeto.getMarcos().add(new Marco());		
		}		
	}
	
}
