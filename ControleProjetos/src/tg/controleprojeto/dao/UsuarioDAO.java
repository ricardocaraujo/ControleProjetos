package tg.controleprojeto.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import tg.controleprojeto.modelo.Usuario;

public class UsuarioDAO {
	
	
	
	public boolean existeUsuario(Usuario usuario) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Usuario> query = manager.createNamedQuery("Usuario.existeUsuario", Usuario.class);
		query.setParameter("pNome", usuario.getNome());
		query.setParameter("pSenha", usuario.getSenha());
		try {
			Usuario resultado = query.getSingleResult();
		} catch (NoResultException nre) {
			return false;
		} finally {
			manager.close();
		}
		return true;		
	}	
	
}
