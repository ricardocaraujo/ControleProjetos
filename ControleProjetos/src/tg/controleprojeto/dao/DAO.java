package tg.controleprojeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class DAO<T> {
	
	private final Class<T> classe;
	
	public DAO(Class<T> classe) {
		this.classe = classe;
	}
	
	public void adiciona(T t) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		manager.close();	
	}
	
	public void altera(T t) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.merge(t);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public void remove(T t) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		manager.remove(manager.merge(t));
		manager.getTransaction().commit();
		manager.close();
	}
	
	public T buscaPorId(int id) {
		EntityManager manager = new JPAUtil().getEntityManager();
		T entidade = manager.find(classe, id);
		manager.close();
		return entidade;
	}
	
	public List<T> listaTodos() {
		EntityManager manager = new JPAUtil().getEntityManager();
		CriteriaBuilder criteria = manager.getCriteriaBuilder();
		CriteriaQuery<T> query = criteria.createQuery(classe);
		query.select(query.from(classe));
		List<T> lista = manager.createQuery(query).getResultList();
		return lista;
	}	
}
