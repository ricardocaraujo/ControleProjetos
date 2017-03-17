package tg.controleprojeto.util;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import tg.controleprojeto.modelo.Situacao;

@FacesConverter(value="situacaoEnumConverter")
public class SituacaoEnumConverter extends EnumConverter {

	
	public SituacaoEnumConverter() {
		super(Situacao.class);
	}
}
