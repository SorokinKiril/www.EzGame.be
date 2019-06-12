package be.ezgame.validator;

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
@FacesValidator("userExistValidator")
public class UserExistValidator implements Validator {

	private String name;
	private EntityManager em;

	public UserExistValidator() {
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object submittedValue) {
		name = submittedValue.toString();
		em = EMF.getEM();
		UserService uService = new UserService(em);
		User existingUser = uService.findUserByLogin(name);

		em.close();
		if (existingUser == null) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Cet utilisateur n'existe pas.", ""));
		}
	}
}
