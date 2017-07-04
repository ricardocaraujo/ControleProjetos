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

import tg.controleprojeto.modelo.LinhaDePesquisa;
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
		System.out.println("projeto removido");
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
		System.out.println(situacao.size());
		System.out.println(idLinhaDePesquisa.size());
		EntityManager manager = new JPAUtil().getEntityManager();
		CriteriaBuilder criteria = manager.getCriteriaBuilder();
		CriteriaQuery<Projeto> query = criteria.createQuery(Projeto.class);
		Root<Projeto> root = query.from(Projeto.class);
		Path<Situacao> situacaoPath = root.<Situacao>get("situacao");
		Path<Integer> linhaDePesquisaPath = root.join("linhaDePesquisa").<Integer>get("id");
		List<Predicate> predicatesSituacao = new ArrayList<Predicate>();
		List<Predicate> predicatesLinhaDePesquisa = new ArrayList<Predicate>();
		Predicate criterio = null;
		if(situacao != null) {			
			for(Situacao s:situacao) {
				criterio = criteria.equal(situacaoPath, s);
				predicatesSituacao.add(criterio);
			}
			System.out.println("ProjetoDAO - situacao: " + predicatesSituacao.size());
		}
		if(idLinhaDePesquisa != null) {
			criterio = null;
			for(Integer idLinha:idLinhaDePesquisa) {
				criterio = criteria.equal(linhaDePesquisaPath, idLinha);
				predicatesLinhaDePesquisa.add(criterio);
			}			
			System.out.println("ProjetoDAO - total: " + predicatesLinhaDePesquisa.size());
		}
		query.where(criteria.or((Predicate[]) predicatesSituacao.toArray(new Predicate[0])));
		query.where(criteria.or((Predicate[]) predicatesLinhaDePesquisa.toArray(new Predicate[0])));
		TypedQuery<Projeto> typedQuery = manager.createQuery(query);	
		return typedQuery.getResultList();
	}
	
	public int getNumeroProjetosPorStatus (Situacao situacao) { 
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Projeto> query = manager.createNamedQuery("Projetos.quantidadePorSituacao", Projeto.class);
		query.setParameter("pSituacao", situacao);
		List<Projeto> projetos = query.getResultList();
		System.out.println(situacao.getDescricao());
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
