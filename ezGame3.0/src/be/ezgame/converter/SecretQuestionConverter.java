package be.ezgame.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import javax.persistence.EntityManager;

import be.ezgame.emf.EMF;
import be.ezgame.model.SecretQuestion;
import be.ezgame.service.SecretQuestionService;

@Named
public class SecretQuestionConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				EntityManager em;
				em = EMF.getEM();
				return new SecretQuestionService(em).findQuestionById(Integer.parseInt(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de conversion", "Question non valide"));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((SecretQuestion) object).getSecretQuestionId());
		} else {
			return null;
		}
	}
}