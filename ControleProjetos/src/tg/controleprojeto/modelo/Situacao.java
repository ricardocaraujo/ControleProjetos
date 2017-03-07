package tg.controleprojeto.modelo;

public enum Situacao {
	
	CANCELADO("Cancelado"),
	EMANDAMENTO("Em andamento"),
	EMCARTEIRA("Em carteira"),
	FINALIZADO("Finalizado"),
	PARADO("Parado");
	
	private final String descricao;
	
	Situacao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
}
