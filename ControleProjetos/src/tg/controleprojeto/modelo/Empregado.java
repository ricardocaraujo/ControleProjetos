package tg.controleprojeto.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(name="Empregados.findAll", query="select e from Empregado e"),
	@NamedQuery(name="Empregados.buscaPorId", query="select e from Empregado e where e.id=:eId")
})

@Entity
public class Empregado {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;
	private String nome;
	
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

