package tg.controleprojeto.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sun.org.apache.xml.internal.security.Init;

import javax.faces.bean.RequestScoped;

import tg.controleprojeto.dao.EmpregadoDAO;
import tg.controleprojeto.dao.ProjetoDAO;
import tg.controleprojeto.modelo.Projeto;
import tg.controleprojeto.modelo.Situacao;
import tg.controleprojeto.modelo.Empregado;
import tg.controleprojeto.modelo.Gerencia;


@ManagedBean
@ViewScoped
public class ProjetoMB {
	
	private Projeto projeto;
	private ProjetoDAO projetoDAO;
	private EmpregadoDAO empregadoDAO;
	private List<Projeto> listaProjetos;
	private List<Integer> idDosCoordenadores;
	private List<Integer> idDosResponsaveisTecnicos;
	private List<Situacao> situacoesSelecionadas;
	
	
	@PostConstruct
	public void init() {
		this.projeto = new Projeto();
		projetoDAO = new ProjetoDAO();
		empregadoDAO = new EmpregadoDAO();
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
			this.listaProjetos = projetoDAO.getProjetos();
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

	public String listaProjetosComFiltro() {
		this.listaProjetos = this.projetoDAO.getProjetosPorStatus(situacoesSelecionadas);
		return "listaProjeto";
	}
	
	public String adiciona() {
		List<Empregado> coordenadores = new ArrayList<Empregado>();
		List<Empregado> responsaveisTecnicos = new ArrayList<Empregado>();
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
		this.projetoDAO.adiciona(projeto);
		this.projeto = new Projeto();
		return "listaProjeto";
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
}
