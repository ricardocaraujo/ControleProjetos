package tg.controleprojeto.bean;

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
	private List<Integer> idDosCoordenadores;
	
	public List<Empregado> getEmpregados() {
		return empregadoDAO.getEmpregados();
	}

	public List<Integer> getIdDosCoordenadores() {
		return idDosCoordenadores;
	}

	public void setIdDosCoordenadores(List<Integer> idDosCoordenadores) {
		this.idDosCoordenadores = idDosCoordenadores;
	}
	
	public void adiciona() {
		List<Integer> lista = this.idDosCoordenadores;
		for(Integer idEmpregado : lista) {
			Empregado empregado = empregadoDAO.buscaPorId(idEmpregado.intValue());
			projeto.getCoordenadores().add(empregado);
		}
		this.projetoDAO.adiciona(projeto);
		this.projeto = new Projeto();
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
}
