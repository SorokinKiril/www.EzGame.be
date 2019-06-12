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
import be.ezgame.model.Classe;
import be.ezgame.service.ClasseService;

@Named
@RequestScoped
@FacesValidator("ClasseValidator")
public class ClasseValidator implements Validator {

	private String name;
	private Pattern pattern;
	private Matcher matcher;
	private EntityManager em;

	private static final String CLASSE_PATTERN = "^[_A-Za-z0-9-]{1,45}+";

	public ClasseValidator() {
		pattern = Pattern.compile(CLASSE_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object submittedValue) {
		Object oldValue = ((UIInput) component).getValue();
		matcher = pattern.matcher(submittedValue.toString());
		name = submittedValue.toString();
		em = EMF.getEM();
		ClasseService c = new ClasseService(em);
		Classe newTitle = c.findClasseByName(name);

		if (oldValue != null && newTitle != null) {
			// Mode modification

			String oldName = oldValue.toString();
			Classe oldTitle = c.findClasseByName(oldName);
			em.close();
			if (oldTitle.getClasseId() != newTitle.getClasseId()) {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Cette classe existe déjà.", ""));
			}
		} else {
			// Mode ajout
			em.close();
			if (newTitle == null) {
				if (!matcher.matches()) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Mauvais format de classe. Seuls les alphanumériques sont acceptés. Longueur maximale 45 caractères.", ""));
				}

			} else {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Cette classe existe déjà.", ""));
			}
		}
	}
}