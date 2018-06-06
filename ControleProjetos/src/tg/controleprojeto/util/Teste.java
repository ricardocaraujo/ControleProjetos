package tg.controleprojeto.util;

import org.junit.Test;

import junit.framework.Assert;
import tg.controleprojeto.dao.UsuarioDAO;
import tg.controleprojeto.modelo.Situacao;
import tg.controleprojeto.modelo.Usuario;

public class Teste {

	@Test
	public void testeUsuario()  {
		Usuario usuario = new Usuario();
		usuario.setNome("ricardo");
		usuario.setSenha("ricardo");
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Assert.assertTrue(usuarioDAO.existeUsuario(usuario));
	}

}
