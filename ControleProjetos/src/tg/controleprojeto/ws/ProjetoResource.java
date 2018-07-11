package tg.controleprojeto.ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import tg.controleprojeto.dao.ProjetoDAO;
import tg.controleprojeto.modelo.Projeto;
import tg.controleprojeto.modelo.Situacao;

@Path("projetos") 
public class ProjetoResource {

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String buscaProjetos() {	
		List<Projeto> projetos = new ProjetoDAO().getProjetos();
		return new XStream().toXML(projetos);
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adicionaProjeto(String projeto) {
		Projeto novoProjeto = (Projeto) new XStream().fromXML(projeto);
		new ProjetoDAO().adiciona(novoProjeto);
		return Response.ok().build();
	}
	
	@SuppressWarnings("unchecked")
	@Path("/buscaProjetoPorStatus")
	@GET
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public String buscaProjetoPorStatus(@QueryParam("situacoesSelecionadas") String situacoesSelecionadas, @QueryParam("idLinhasDePesquisas") String idLinhasDePesquisas) {
		List<Situacao> listaSituacoes = (List<Situacao>) new XStream().fromXML(situacoesSelecionadas);
		List<Integer> listaIdLinhasDePesquisas = (List<Integer>) new XStream().fromXML(idLinhasDePesquisas);
		List<Projeto> projetos = new ProjetoDAO().getProjetosPorStatus(listaSituacoes, listaIdLinhasDePesquisas);
		return new XStream().toXML(projetos);
	}	
	
}
