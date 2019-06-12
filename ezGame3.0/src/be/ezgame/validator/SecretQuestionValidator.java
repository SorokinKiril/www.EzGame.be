package be.ezgame.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.persistence.EntityManager;

import be.ezgame.emf.EMF;
import be.ezgame.model.SecretQuestion;
import be.ezgame.service.SecretQuestionService;

@Named
@RequestScoped
@FacesValidator("SecretQuestionValidator")
public class SecretQuestionValidator implements Validator {

	private String name;
	private Pattern pattern;
	private Matcher matcher;
	private EntityManager em;

	private static final String SECRET_QUESTION_PATTERN = "^[_A-ZÁ-ÿa-z0-9- ?']{1,45}+";

	public SecretQuestionValidator() {
		pattern = Pattern.compile(SECRET_QUESTION_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object submittedValue) {
		Object oldValue = ((UIInput) component).getValue();
		matcher = pattern.matcher(submittedValue.toString());
		name = submittedValue.toString();
		em = EMF.getEM();
		SecretQuestionService s = new SecretQuestionService(em);
		SecretQuestion newQuestion = s.findQuestionByName(name);
		if (oldValue != null && newQuestion != null) {
			// Mode modification

			String oldName = oldValue.toString();
			SecretQuestion oldQuestion = s.findQuestionByName(oldName);
			em.close();
			if (oldQuestion.getSecretQuestionId() != newQuestion.getSecretQuestionId()) {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Cette question existe déjà.", ""));
			}
		} else {
			// Mode ajout
			em.close();
			if (newQuestion == null) {
				if (!matcher.matches()) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Mauvais format de question. Seuls les alphanumériques sont acceptés. Longueur maximale 45 caractères.",
							""));
				}

			} else {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Cette question existe déjà.", ""));
			}
		}
	}
}