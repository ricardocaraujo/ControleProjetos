package tg.controleprojeto.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;


@ManagedBean
public class ProjetoMB {
	
	private Projeto projeto;
	private ProjetoDAO projetoDAO;
	private EmpregadoDAO empregadoDAO;
	private List<Projeto> listaProjetos;
	private List<Integer> idDosCoordenadores;
	private List<Integer> idDosResponsaveisTecnicos;
	private List<Situacao> situacoesSelecionadas;
	private UploadedFile eap;
	
 	
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
	
	public UploadedFile getEap() {
		return eap;
	}

	public void setEap(UploadedFile eap) {
		this.eap = eap;
	}


	public void listaProjetosComFiltro() {
		for(Situacao s : situacoesSelecionadas) {
			System.out.println(s.getDescricao());
		}
		this.listaProjetos = this.projetoDAO.getProjetosPorStatus(situacoesSelecionadas);
		
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
		projeto.setEap(this.eap.getContents());
		this.projetoDAO.adiciona(projeto);
		this.projeto = new Projeto();
		return "listaProjeto";
	}
	
	public String editaProjeto(Projeto projeto) {
		this.projeto = projeto;
		return "adicionaProjeto";
	}
	
	public void apagaProjeto(Projeto projeto) {
        
    }

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
}
