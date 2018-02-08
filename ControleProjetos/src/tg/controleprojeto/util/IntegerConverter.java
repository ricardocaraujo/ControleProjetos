package tg.controleprojeto.util;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import tg.controleprojeto.dao.EmpregadoDAO;
import tg.controleprojeto.modelo.Empregado;

@FacesConverter(value="integerConverter")
public class IntegerConverter implements Converter {

	EmpregadoDAO empregadoDAO = new EmpregadoDAO();
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		
		try {
			Integer id = Integer.parseInt(arg2);
			System.out.println(String.valueOf(id));
			Empregado coordenador = empregadoDAO.buscaPorId(id.intValue());
			System.out.println(coordenador.getNome());
			return coordenador;
        } catch(NumberFormatException e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
        }
		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		System.out.println(((Empregado) arg2).getNome());		
		return String.valueOf(((Empregado) arg2).getId());
		
	}
		
}
