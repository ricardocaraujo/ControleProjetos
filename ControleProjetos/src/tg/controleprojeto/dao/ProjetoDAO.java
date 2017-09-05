package tg.controleprojeto.dao;

import java.io.Serializable;
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

public class ProjetoDAO implements Serializable { 
	
		
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
	
	public List<Projeto> getProjetosPorStatus (List<Situacao> situacao, List<Integer> idLinhaDePesquisa) {
		EntityManager manager = new JPAUtil().getEntityManager();
		CriteriaBuilder criteria = manager.getCriteriaBuilder();
		CriteriaQuery<Projeto> query = criteria.createQuery(Projeto.class);
		Root<Projeto> root = query.from(Projeto.class);
		Path<Situacao> situacaoPath = root.<Situacao>get("situacao");
		Path<Integer> linhaDePesquisaPath = root.join("linhaDePesquisa").<Integer>get("id");
		List<Predicate> predicatesSituacao = new ArrayList<Predicate>();
		List<Predicate> predicatesLinhaDePesquisa = new ArrayList<Predicate>();
		if(!situacao.isEmpty()) {
			for(Situacao s:situacao) {			
				predicatesSituacao.add(criteria.equal(situacaoPath, s));
			}
		}
		if(!idLinhaDePesquisa.isEmpty()) {
			for(Integer idLinha:idLinhaDePesquisa) {
				predicatesLinhaDePesquisa.add(criteria.equal(linhaDePesquisaPath, idLinha));
			}			
		}
		if (!situacao.isEmpty() && !idLinhaDePesquisa.isEmpty()) {
			query.where(criteria.and(criteria.or(predicatesSituacao.toArray(new Predicate[0])),
					criteria.or(predicatesLinhaDePesquisa.toArray(new Predicate[0]))));
		} else if (situacao.isEmpty() && !idLinhaDePesquisa.isEmpty()) {
			query.where(criteria.or(predicatesLinhaDePesquisa.toArray(new Predicate[0])));
		} else if (!situacao.isEmpty() && idLinhaDePesquisa.isEmpty()) {
			query.where(criteria.or(predicatesSituacao.toArray(new Predicate[0])));
		}	
		TypedQuery<Projeto> typedQuery = manager.createQuery(query);	
		return typedQuery.getResultList();
	}
	
	public int getNumeroProjetosPorStatus (Situacao situacao) { 
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Projeto> query = manager.createNamedQuery("Projetos.quantidadePorSituacao", Projeto.class);
		query.setParameter("pSituacao", situacao);
		List<Projeto> projetos = query.getResultList();
		manager.close();
		return projetos.size();
	}
	
	public List<Projeto> getProjetosPorLinhaDePesquisa(int id) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Projeto> query = manager.createNamedQuery("Projetos.buscaPorLinhaDePesquisa", Projeto.class);
		query.setParameter("plinhaDePesquisa", id);
		List<Projeto> projetos = query.getResultList();
		manager.close();
		return projetos;		
	}
	
}
