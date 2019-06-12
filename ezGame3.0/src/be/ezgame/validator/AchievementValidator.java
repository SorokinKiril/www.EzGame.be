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
import be.ezgame.model.Achievement;
import be.ezgame.service.AchievementService;

@Named
@RequestScoped
@FacesValidator("AchievementValidator")
public class AchievementValidator implements Validator {

	private String name;
	private Pattern pattern;
	private Matcher matcher;
	private EntityManager em;

	private static final String ACHIEVEMENT_PATTERN = "^[_A-ZÁ-ÿa-z0-9- ']{1,45}+";

	public AchievementValidator() {
		pattern = Pattern.compile(ACHIEVEMENT_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object submittedValue) {
		Object oldValue = ((UIInput) component).getValue();
		matcher = pattern.matcher(submittedValue.toString());
		name = submittedValue.toString();
		em = EMF.getEM();
		AchievementService a = new AchievementService(em);
		Achievement newAchievement = a.findAchievementByName(name);
		if (oldValue != null && newAchievement != null) {
			// Mode modification
			String oldName = oldValue.toString();
			Achievement oldAchievement = a.findAchievementByName(oldName);
			em.close();
			if (oldAchievement.getAchievementId() != newAchievement.getAchievementId()) {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Ce succès existe déjà.", ""));
			}
		} else {
			// Mode ajout
			em.close();
			if (newAchievement == null) {
				if (!matcher.matches()) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Mauvais format de succès. Seuls les alphanumériques sont acceptés. Longueur maximale 45 caractères.",
							""));
				}

			} else {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Ce succès existe déjà.", ""));
			}
		}
	}
}