package tg.controleprojeto.ws;

import javax.ws.rs.GET; 
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import tg.controleprojeto.dao.ProjetoDAO;
import tg.controleprojeto.modelo.Projeto;

@Path("projetos") 
public class ProjetoResource {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String buscaProjeto() {
		Projeto projeto = new ProjetoDAO().buscaPorId(8);
		return projeto.toXML();
	}
	
}
