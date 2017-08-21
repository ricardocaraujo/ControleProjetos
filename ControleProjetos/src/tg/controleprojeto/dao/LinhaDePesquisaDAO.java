package tg.controleprojeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import tg.controleprojeto.modelo.LinhaDePesquisa;


public class LinhaDePesquisaDAO {
	
	public void adiciona(LinhaDePesquisa linhaDePesquisa) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.persist(linhaDePesquisa);
		manager.getTransaction().commit();
		manager.close(); 
	}
	
	public void altera(LinhaDePesquisa linhaDePesquisa) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.merge(linhaDePesquisa);
		manager.getTransaction().commit();	
		manager.close();
	}
	
	public void remove(int id) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		LinhaDePesquisa linhaDePesquisa = manager.find(LinhaDePesquisa.class, id);
		manager.remove(linhaDePesquisa);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public List<LinhaDePesquisa> getLinhasDePesquisa() {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<LinhaDePesquisa> query = manager.createNamedQuery("LinhasDePesquisa.findAll", LinhaDePesquisa.class);
		List<LinhaDePesquisa> linhasDePesquisa = query.getResultList();
		manager.close();
		return linhasDePesquisa;
	}
	
	public LinhaDePesquisa buscaPorId(int id) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		LinhaDePesquisa linhaDePesquisa = manager.find(LinhaDePesquisa.class, id);
		manager.close();
		return linhaDePesquisa;
	}	
}
