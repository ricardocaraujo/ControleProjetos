package tg.controleprojeto.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import tg.controleprojeto.dao.EmpregadoDAO;
import tg.controleprojeto.dao.LinhaDePesquisaDAO;
import tg.controleprojeto.dao.ProjetoDAO;
import tg.controleprojeto.modelo.Projeto;
import tg.controleprojeto.modelo.Situacao;
import tg.controleprojeto.modelo.Empregado;
import tg.controleprojeto.modelo.LinhaDePesquisa;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
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
	private BarChartModel grafico;
	//private StreamedContent imagem;
	
 	
	@PostConstruct
	public void init() {
		this.projeto = new Projeto();
		projetoDAO = new ProjetoDAO();
		empregadoDAO = new EmpregadoDAO();
		linhaDePesquisaDAO = new LinhaDePesquisaDAO();
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
		this.situacoesSelecionadas = situacoesSelecionadas;
	}
	
	public byte[] getEap() {
		return eap;
	}
	
	public void setEap(byte[] eap) {
		this.eap = eap;
	}
	
	public List<LinhaDePesquisa> getLinhasDePesquisa() {
		List<LinhaDePesquisa> lista = this.linhaDePesquisaDAO.getLinhasDePesquisa();
		return lista;
	}
	
	public Integer getIdLinhaDePesquisa() {
		return idLinhaDePesquisa;
	}
	
	public void setIdLinhaDePesquisa(Integer idLinhaDePesquisa) {
		this.idLinhaDePesquisa = idLinhaDePesquisa;
	}

	public void exibeImagemEap(FileUploadEvent event) {
		this.setEap(event.getFile().getContents());
//		try {
//			this.imagem = new DefaultStreamedContent(event.getFile().getInputstream());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//        //this.imagem = new DefaultStreamedContent(arquivo, "image/png");
	}

//	public StreamedContent getImagem() {
//		System.out.println("entrou no getimagem");
//		return imagem;
//	}
//
//	public void setImagem(StreamedContent imagem) {
//		System.out.println("entrou no setimagem");
//		this.imagem = imagem;
//	}

	public void listaProjetosComFiltro() {
		this.listaProjetos = this.projetoDAO.getProjetosPorStatus(situacoesSelecionadas);		
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
		return "listaProjeto?faces-redirect=true";
	}

	public BarChartModel getGrafico() {
		this.graficoSituacaoProjetos();
		System.out.println("entrou no getGrafico");
		return grafico;
	}
	
	private void graficoSituacaoProjetos() {
		grafico = new BarChartModel();		
		ChartSeries series = new ChartSeries();
		series.setLabel("Situação");
		int numeroProjetos = 0;
		for(Situacao s:Situacao.values()) {
			numeroProjetos = this.projetoDAO.getNumeroProjetosPorStatus(s);
			series.set(s.getDescricao(), numeroProjetos);
		}
		grafico.addSeries(series);
	}

	public String editaProjeto(Projeto projeto) {
		this.projeto = projeto;
		return "adicionaProjeto";
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
