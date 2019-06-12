package be.ezgame.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.persistence.EntityManager;

import be.ezgame.emf.EMF;
import be.ezgame.model.User;
import be.ezgame.service.UserService;

@Named
@RequestScoped
@FacesValidator("userValidator")
public class UserValidator implements Validator {

	private String name;
	private Pattern pattern;
	private Matcher matcher;
	private EntityManager em;

	private static final String USER_PATTERN = "^[_A-ZÁ-ÿa-z0-9-_ '.]{1,45}+";

	public UserValidator() {
		pattern = Pattern.compile(USER_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object submittedValue) {
		matcher = pattern.matcher(submittedValue.toString());
		name = submittedValue.toString();
		em = EMF.getEM();
		UserService a = new UserService(em);
		User existingUser = a.findUserByLogin(name);
		em.close();

		if (existingUser == null) {
			if (!matcher.matches()) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Mauvais format de utilisateur. Seuls les alphanumériques sont acceptés. Longueur maximale 45 caractères.",
						""));
			}

		} else {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Ce utilisateur existe déjà.", ""));
		}
	}
}
