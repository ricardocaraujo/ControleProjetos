package tg.controleprojeto.modelo;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name="Projetos.findAll", query="select p from Projeto p"),
	@NamedQuery(name="Projetos.buscaPorId", query="select p from Projeto p where p.id=:pId")
})


@Entity 
public class Projeto {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id; 
	private String justificativa;
	private String nome;
	private String objetivo;
	
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataInicio;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataFim;
	
	@Lob
	private byte[] eap;

	@ManyToMany
	@JoinTable(name="projeto_resptecnico")
	private List<Empregado> responsaveisTecnicos;
	
	@ManyToMany
	@JoinTable(name="projeto_coordenador")
	private List<Empregado> coordenadores;
	
	@ManyToMany
	@JoinTable(name="projeto_participante")
	private List<Empregado> participantes;
	
	@ManyToMany
	@JoinTable(name="projeto_gerenciassolicitantes")
	private List<Gerencia> gerenciasSolicitantes;
	
	@ManyToMany
	@JoinTable(name="projeto_gerenciasclientes")
	private List<Gerencia> gerenciasClientes;
	
	@ManyToMany
	@JoinTable(name="projeto_gerenciasexecutoras")
	private List<Gerencia> gerenciasExecutoras;
	 
	
	public Projeto(){
		dataInicio = Calendar.getInstance();
		dataFim = Calendar.getInstance();
	}

	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getJustificativa() {
		return justificativa;
	}


	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	
	public Situacao getSituacao() {
		return situacao;
	}


	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getObjetivo() {
		return objetivo;
	}


	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public byte[] getEap() {
		return eap;
	}

	public void setEap(byte[] eap) {
		this.eap = eap;
	}

	public Calendar getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(Calendar dataInicio) {
		System.out.println("chegou");
		this.dataInicio = dataInicio;
	}


	public Calendar getDataFim() {
		return dataFim;
	}


	public void setDataFim(Calendar dataFim) {
		this.dataFim = dataFim;
	}


	public List<Empregado> getResponsaveisTecnicos() {
		return responsaveisTecnicos;
	}


	public void setResponsaveisTecnicos(List<Empregado> responsaveisTecnicos) {
		this.responsaveisTecnicos = responsaveisTecnicos;
	}


	public List<Empregado> getCoordenadores() {
		return coordenadores;
	}


	public void setCoordenadores(List<Empregado> coordenadores) {
		this.coordenadores = coordenadores;
	}


	public List<Empregado> getParticipantes() {
		return participantes;
	}


	public void setParticipantes(List<Empregado> participantes) {
		this.participantes = participantes;
	}


	public List<Gerencia> getGerenciasSolicitantes() {
		return gerenciasSolicitantes;
	}


	public void setGerenciasSolicitantes(List<Gerencia> gerenciasSolicitantes) {
		this.gerenciasSolicitantes = gerenciasSolicitantes;
	}


	public List<Gerencia> getGerenciasClientes() {
		return gerenciasClientes;
	}


	public void setGerenciasClientes(List<Gerencia> gerenciasClientes) {
		this.gerenciasClientes = gerenciasClientes;
	}


	public List<Gerencia> getGerenciasExecutoras() {
		return gerenciasExecutoras;
	}
	
	public void setGerenciasExecutoras(List<Gerencia> gerenciasExecutoras) {
		this.gerenciasExecutoras = gerenciasExecutoras;
	}
}

