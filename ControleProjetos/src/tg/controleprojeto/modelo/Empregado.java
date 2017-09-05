package tg.controleprojeto.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQueries({
	@NamedQuery(name="Empregado.findAll", query="select e from Empregado e"),
	@NamedQuery(name="Empregado.buscaPorId", query="select e from Empregado e where e.id=:eId")
})

@Entity
public class Empregado implements Serializable  {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	@OneToMany(mappedBy="responsavel")
	private List<Marco> marcos;
	
	public Empregado(String nome) {
		this.nome = nome;
	}
	
	public Empregado(){}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this. nome = nome;
	}
}

