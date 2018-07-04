package tg.controleprojeto.ws;

import java.util.List;

import javax.ws.rs.GET; 
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.thoughtworks.xstream.XStream;

import tg.controleprojeto.dao.ProjetoDAO;
import tg.controleprojeto.modelo.Projeto;

@Path("projetos") 
public class ProjetoResource {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String buscaProjeto() {	
		List<Projeto> projetos = new ProjetoDAO().getProjetos();
		return new XStream().toXML(projetos);
	}
	
	
	
}
