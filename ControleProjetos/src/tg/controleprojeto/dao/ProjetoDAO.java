package tg.controleprojeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import tg.controleprojeto.modelo.Projeto;

public class ProjetoDAO {
	
		
	public void adiciona(Projeto projeto) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.persist(projeto);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public void altera(Projeto projeto) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.merge(projeto);
		manager.getTransaction().commit();	
		manager.close();
	}
	
	public void remove(int id) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		Projeto projeto = manager.find(Projeto.class, id);
		manager.remove(projeto);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public List<Projeto> getProjetos() {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Projeto> query = manager.createNamedQuery("Projetos.findAll", Projeto.class);
		List<Projeto> projetos = query.getResultList();
		manager.getTransaction().commit();
		manager.close();
		return projetos;
	}
	
	public Projeto buscaPorId(int id) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Projeto> query = manager.createNamedQuery("Projetos.buscaPorId", Projeto.class);
		query.setParameter("pId", id);
		Projeto projeto = query.getSingleResult();
		manager.getTransaction().commit();
		manager.close();
		return projeto;
	}
	
	

}
