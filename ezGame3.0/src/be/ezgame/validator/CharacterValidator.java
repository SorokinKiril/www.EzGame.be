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
import be.ezgame.model.Character;
import be.ezgame.service.CharacterService;

@Named
@RequestScoped
@FacesValidator("CharacterValidator")
public class CharacterValidator implements Validator {

	private String name;
	private Pattern pattern;
	private Matcher matcher;
	private EntityManager em;

	private static final String CHARACTER_PATTERN = "^[_A-ZÁ-ÿa-z0-9- '.]{1,45}+";

	public CharacterValidator() {
		pattern = Pattern.compile(CHARACTER_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object submittedValue) {
		Object oldValue = ((UIInput) component).getValue();
		matcher = pattern.matcher(submittedValue.toString());
		name = submittedValue.toString();
		em = EMF.getEM();
		CharacterService a = new CharacterService(em);
		Character newCharacter = a.findCharacterByName(name);
		if (oldValue != null && newCharacter != null) {
			// Mode modification

			String oldName = oldValue.toString();
			Character oldCharacter = a.findCharacterByName(oldName);
			em.close();
			if (oldCharacter.getCharacterId() != newCharacter.getCharacterId()) {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Ce personnage existe déjà.", ""));
			}
		} else {
			// Mode ajout
			em.close();
			if (newCharacter == null) {
				if (!matcher.matches()) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Mauvais format de personnage. Seuls les alphanumériques sont acceptés. Longueur maximale 45 caractères.",
							""));
				}

			} else {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Ce personnage existe déjà.", ""));
			}
		}
	}
}