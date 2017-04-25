package tg.controleprojeto.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("projeto");
	
	public EntityManager getEntityManager() { 
		return emf.createEntityManager();
	}

}
