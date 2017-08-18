package tg.controleprojeto.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import tg.controleprojeto.dao.LinhaDePesquisaDAO;
import tg.controleprojeto.dao.ProjetoDAO;
import tg.controleprojeto.modelo.Projeto;
import tg.controleprojeto.modelo.Situacao;
import tg.controleprojeto.modelo.LinhaDePesquisa;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;


@ManagedBean
@ViewScoped
public class ListaProjetoMB {
	
	private Projeto projeto;
	private ProjetoDAO projetoDAO;
	private LinhaDePesquisaDAO linhaDePesquisaDAO;
	private List<Projeto> listaProjetos;
	private List<Situacao> situacoesSelecionadas;
	private List<Integer> idLinhasSelecionadas;	

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	
	public List<Projeto> getListaProjetos() {
		if(situacoesSelecionadas == null) {
			return projetoDAO.getProjetos();	
		}	
		return this.listaProjetos;
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
	
	public List<LinhaDePesquisa> getLinhasDePesquisa() {
		return this.linhaDePesquisaDAO.getLinhasDePesquisa();
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
	
	public void listaProjetosComFiltro() {
		this.listaProjetos = this.projetoDAO.getProjetosPorStatus(situacoesSelecionadas, idLinhasSelecionadas);		
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
}
