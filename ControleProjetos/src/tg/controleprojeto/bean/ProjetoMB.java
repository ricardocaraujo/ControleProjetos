package tg.controleprojeto.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import tg.controleprojeto.dao.EmpregadoDAO;
import tg.controleprojeto.dao.ProjetoDAO;
import tg.controleprojeto.modelo.Projeto;
import tg.controleprojeto.modelo.Empregado;


@ManagedBean
public class ProjetoMB {
	
	private Projeto projeto = new Projeto() ;
	private ProjetoDAO projetoDAO = new ProjetoDAO();
	private EmpregadoDAO empregadoDAO = new EmpregadoDAO();
	private List<Projeto> listaProjetos;
	private List<Integer> idDosCoordenadores;
	private List<Integer> idDosResponsaveisTecnicos;
	
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
		return projetoDAO.getProjetos();
	}

	public void setListaProjetos(List<Projeto> listaProjetos) {
		this.listaProjetos = listaProjetos;
	}

	public void setIdDosCoordenadores(List<Integer> idDosCoordenadores) {
		this.idDosCoordenadores = idDosCoordenadores;
	}
	
	public String adiciona() {
		List<Empregado> coordenadores = new ArrayList<Empregado>();
		for(Integer idEmpregado : this.idDosCoordenadores) {
			Empregado empregado = empregadoDAO.buscaPorId(idEmpregado.intValue());
			coordenadores.add(empregado);
		}
		projeto.setCoordenadores(coordenadores);
		this.projetoDAO.adiciona(projeto);
		this.projeto = new Projeto();
		return "adicionaProjeto";
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
}
