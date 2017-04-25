package tg.controleprojeto.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import tg.controleprojeto.modelo.Projeto;
import tg.controleprojeto.modelo.Situacao;

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
		manager.close();
		return projetos;
	}
	
	public Projeto buscaPorId(int id) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Projeto> query = manager.createNamedQuery("Projetos.buscaPorId", Projeto.class);
		query.setParameter("pId", id);
		Projeto projeto = query.getSingleResult();
		manager.close();
		return projeto;
	}
	
	public List<Projeto> getProjetosPorStatus (List<Situacao> situacao) {
		EntityManager manager = new JPAUtil().getEntityManager();
		CriteriaBuilder criteria = manager.getCriteriaBuilder();
		CriteriaQuery<Projeto> query = criteria.createQuery(Projeto.class);
		Root<Projeto> root = query.from(Projeto.class);
		Path<Situacao> enumPath = root.<Situacao>get("situacao");
		Predicate situacaoIgual = null;
		if(situacao != null) {
			List<Predicate> predicates = new ArrayList<Predicate>();
			for(Situacao s:situacao) {
				situacaoIgual = criteria.equal(enumPath, s);
				predicates.add(situacaoIgual);
			}
			query.where(criteria.or((Predicate[]) predicates.toArray(new Predicate[0])));
		}		
		TypedQuery<Projeto> typedQuery = manager.createQuery(query);	
		return typedQuery.getResultList();
	}	
}
