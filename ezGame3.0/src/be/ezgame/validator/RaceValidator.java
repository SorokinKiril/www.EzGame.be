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
import be.ezgame.model.Race;
import be.ezgame.service.RaceService;

@Named
@RequestScoped
@FacesValidator("RaceValidator")
public class RaceValidator implements Validator {

	private String name;
	private Pattern pattern;
	private Matcher matcher;
	private EntityManager em;

	private static final String RACE_PATTERN = "^[_A-Za-z0-9-]{1,45}+";

	public RaceValidator() {
		pattern = Pattern.compile(RACE_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object submittedValue) {
		Object oldValue = ((UIInput) component).getValue();
		matcher = pattern.matcher(submittedValue.toString());
		name = submittedValue.toString();
		em = EMF.getEM();
		RaceService r = new RaceService(em);
		Race newRace = r.findTitleByName(name);
		if (oldValue != null && newRace != null) {
			// Mode modification

			String oldName = oldValue.toString();
			Race oldRace = r.findTitleByName(oldName);
			em.close();
			if (oldRace.getRaceId() != newRace.getRaceId()) {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Cette race existe déjà.", ""));
			}
		} else {
			// Mode ajout
			em.close();
			if (newRace == null) {
				if (!matcher.matches()) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Mauvais format de race. Seuls les alphanumériques sont acceptés. Longueur maximale 45 caractères.",
							""));
				}

			} else {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Cette race existe déjà.", ""));
			}
		}
	}
}