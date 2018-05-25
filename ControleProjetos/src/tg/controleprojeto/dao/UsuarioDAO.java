package tg.controleprojeto.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import tg.controleprojeto.modelo.Usuario;

public class UsuarioDAO {
	
	public boolean existeUsuario(Usuario usuario) {
		EntityManager manager = new JPAUtil().getEntityManager();
		manager.getTransaction().begin();
		TypedQuery<Usuario> query = manager.createNamedQuery("Usuario.busca", Usuario.class);
		query.setParameter("pNome", usuario.getNome());
		query.setParameter("pSenha", usuario.getSenha());
		Usuario usuarioBD = query.getSingleResult();
		if(usuarioBD != null) {
			return true;
		}
		return false;
	}

}
