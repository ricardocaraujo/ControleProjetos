package tg.controleprojeto.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;


@ManagedBean
@ViewScoped
public class ProjetoMB {
	
	private Projeto projeto;
	private ProjetoDAO projetoDAO;
	private EmpregadoDAO empregadoDAO;
	private LinhaDePesquisaDAO linhaDePesquisaDAO;
	private List<Projeto> listaProjetos;
	private List<Integer> idDosCoordenadores;
	private List<Integer> idDosResponsaveisTecnicos;
	private Integer idLinhaDePesquisa;
	private List<Situacao> situacoesSelecionadas;
	private byte[] eap;
	private List<Integer> idLinhasSelecionadas;	
 	
	@PostConstruct
	public void init() {		
		this.projetoDAO = new ProjetoDAO();
		this.empregadoDAO = new EmpregadoDAO();
		this.linhaDePesquisaDAO = new LinhaDePesquisaDAO();
		this.carregaProjeto();
	}
	
	public void carregaProjeto() {
		this.projeto = (Projeto) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("projeto");
		System.out.println("entrou aqui");
		if(this.projeto == null) {
			System.out.println("entrou aqui 2");
			this.projeto = new Projeto();
		}
	}
	
	public List<Integer> getIdDosResponsaveisTecnicos() {
		return idDosResponsaveisTecnicos;
	}

	public void setIdDosResponsaveisTecnicos(List<Integer> idDosResponsaveisTecnicos) {
		this.idDosResponsaveisTecnicos = idDosResponsaveisTecnicos;
	}

	public List<Empregado> getEmpregados() {
		return empregadoDAO.getEmpregados();
	}

	public List<Integer> getIdDosCoordenadores() {
		return idDosCoordenadores;
	}

	public List<Projeto> getListaProjetos() {
		if(situacoesSelecionadas == null) {
			return projetoDAO.getProjetos();	
		}	
		return this.listaProjetos;
	}

	public void setIdDosCoordenadores(List<Integer> idDosCoordenadores) {
		this.idDosCoordenadores = idDosCoordenadores;
	}
	
	public Situacao[] getSituacoesPossiveis() {
		return Situacao.values();
	}
	
	public List<Situacao> getSituacoesSelecionadas() {
		return situacoesSelecionadas;		
	}

	public void setSituacoesSelecionadas(List<Situacao> situacoesSelecionadas) {
		System.out.println(situacoesSelecionadas.size());
		for(Situacao s:situacoesSelecionadas) {
			System.out.println(s.getDescricao());
		}
		this.situacoesSelecionadas = situacoesSelecionadas;
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
	
	public List<LinhaDePesquisa> getLinhasDePesquisa() {
		return this.linhaDePesquisaDAO.getLinhasDePesquisa();
	}

	public List<Integer> getIdLinhasSelecionadas() {
		return idLinhasSelecionadas;
	}

	public void setIdLinhasSelecionadas(List<Integer> idLinhasSelecionadas) {
		this.idLinhasSelecionadas = idLinhasSelecionadas;
		System.out.println(idLinhasSelecionadas.size());
		for(Integer n:idLinhasSelecionadas) {
			System.out.println(n);
		}
	}

	public void exibeImagemEap(FileUploadEvent event) {
		this.setEap(event.getFile().getContents());
	}

	public void listaProjetosComFiltro() {
		this.listaProjetos = this.projetoDAO.getProjetosPorStatus(situacoesSelecionadas, idLinhasSelecionadas);		
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
		projeto.setCoordenadores(coordenadores);
		projeto.setResponsaveisTecnicos(responsaveisTecnicos);
		projeto.setEap(this.eap);
		projeto.setLinhaDePesquisa(linhaDePesquisa);
		this.projetoDAO.adiciona(projeto);
		this.projeto = new Projeto();
		return "listaProjeto2?faces-redirect=true";
	}

	public BarChartModel getGrafico() {
		BarChartModel grafico = new BarChartModel();		
		ChartSeries series = new ChartSeries();
		series.setLabel("Situação");
		int numeroProjetos = 0;
		for(Situacao s:Situacao.values()) {
			numeroProjetos = this.projetoDAO.getNumeroProjetosPorStatus(s);
			series.set(s.getDescricao(), numeroProjetos);
		}
		grafico.addSeries(series);
		return grafico;
	}
	
	public void removeMarco(Marco marco) {
		this.projeto.getMarcos().remove(marco); 
	}
	
	public void adicionaMarco(AjaxBehaviorEvent event) {
		this.projeto.getMarcos().add(new Marco("Marco2"));
	}

	public String editaProjeto(Projeto projeto) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("projeto", projeto);
		System.out.println(projeto.getId());
		return "adicionaProjeto?faces-redirect=true";
	}
	
	public void setProjetoAtual(Projeto projeto) {
		this.projeto = projeto;
	}

	public void removeProjeto() {
		this.projetoDAO.remove(this.projeto.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Projeto apagado!"));
    }
	
	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
}
