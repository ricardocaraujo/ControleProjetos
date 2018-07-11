package tg.controleprojeto.ws.client;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import tg.controleprojeto.modelo.Projeto;
import tg.controleprojeto.modelo.Situacao;

public class ProjetoCliente {
	
	
	private Client cliente;
	private WebTarget target;
	
	public ProjetoCliente() {
		this.cliente = ClientBuilder.newClient();
		this.target = cliente.target("http://localhost:8080");
	}
	
	@SuppressWarnings("unchecked")
	public List<Projeto> getProjetos() {
		String projetos = this.target.path("ControleProjetos/webapi/projetos").request().get(String.class);
		List<Projeto> lista = (ArrayList<Projeto>) new XStream().fromXML(projetos);
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<Projeto> getProjetosComFiltro(List<Situacao> listaSituacao, List<Integer> idLinhasPesquisas) {
		String situacaoXml = new XStream().toXML(listaSituacao);
		String idLinhaXml = new XStream().toXML(idLinhasPesquisas);		
		String projetos = this.target.path("ControleProjetos/webapi/projetos/buscaProjetoPorStatus")
							.queryParam("situacoesSelecionadas", situacaoXml)
							.queryParam("idLinhasDePesquisas", idLinhaXml)
							.request().get(String.class);
		List<Projeto> lista = (ArrayList<Projeto>) new XStream().fromXML(projetos);
		return lista;
	}
	
	public void adicionaProjeto(Projeto projeto) {
		String projetoXml = new XStream().toXML(projeto);
		Entity<String> projetoEntidade = Entity.entity(projetoXml, MediaType.APPLICATION_XML);
		Response response = this.target.path("ControleProjetos/webapi/projetos").request().post(projetoEntidade);
		System.out.println(response.getStatus());
	}

}
