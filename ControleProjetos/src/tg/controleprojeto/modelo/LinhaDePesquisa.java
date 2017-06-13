package tg.controleprojeto.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQueries({
	@NamedQuery(name="LinhasDePesquisa.findAll", query="select l from LinhasDePesquisa l"),
})

@Entity
public class LinhaDePesquisa {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String nome;
	
	@OneToMany
	private List<Projeto> projetos;
	
	private LinhaDePesquisa() {}	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
