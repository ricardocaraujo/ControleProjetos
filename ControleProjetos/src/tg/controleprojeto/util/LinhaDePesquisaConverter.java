package tg.controleprojeto.util;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import tg.controleprojeto.dao.EmpregadoDAO;
import tg.controleprojeto.dao.LinhaDePesquisaDAO;
import tg.controleprojeto.modelo.Empregado;
import tg.controleprojeto.modelo.LinhaDePesquisa;

@FacesConverter(value="linhaDePesquisaConverter")
public class LinhaDePesquisaConverter implements Converter {

	LinhaDePesquisaDAO linhaDePesquisaDAO = new LinhaDePesquisaDAO();
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {		
		try {
			Integer id = Integer.parseInt(arg2);
			System.out.println(String.valueOf(id));
			LinhaDePesquisa linha = linhaDePesquisaDAO.buscaPorId(id.intValue());
			//System.out.println(linha.getNome());
			return linha;
        } catch(NumberFormatException e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
        }		
	}
	
	//método é chamado no response
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		System.out.println(((LinhaDePesquisa) arg2).getNome());		
		return String.valueOf(((LinhaDePesquisa) arg2).getId());
		
	}
		
}
