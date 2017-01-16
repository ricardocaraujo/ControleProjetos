package tg.controleprojeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import tg.controleprojeto.modelo.Empregado;

public class EmpregadoDAO {
	
	
	public void adiciona(Empregado empregado) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.persist(empregado);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public void altera(Empregado empregado) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.merge(empregado);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public void remove(int id) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		Empregado empregado = manager.find(Empregado.class, id);
		manager.remove(empregado);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public Empregado buscaPorId(int id) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		Empregado empregado = manager.find(Empregado.class, id);
		manager.close();
		return empregado;
	}
	
	public List<Empregado> getEmpregados() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetos");
		EntityManager manager = emf.createEntityManager();
		//EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Empregado> query = manager.createNamedQuery("Empregado.findAll", Empregado.class);
		List<Empregado> empregados = query.getResultList();
		manager.close();
		return empregados;
	}
	
	/*public Empregado buscaPorId(int id) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Empregado> query = manager.createNamedQuery("Empregado.buscaPorId", Empregado.class);
		Empregado empregado = query.getSingleResult();
		manager.close();
		return empregado;
	}*/
	
	

}
