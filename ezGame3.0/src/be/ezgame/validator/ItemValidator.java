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
import be.ezgame.model.Item;
import be.ezgame.service.ItemService;

@Named
@RequestScoped
@FacesValidator("ItemValidator")
public class ItemValidator implements Validator {

	private String name;
	private Pattern pattern;
	private Matcher matcher;
	private EntityManager em;

	private static final String ITEM_PATTERN = "^[_A-ZÁ-ÿa-z0-9- ']{1,45}+";

	public ItemValidator() {
		pattern = Pattern.compile(ITEM_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object submittedValue) {
		Object oldValue = ((UIInput) component).getValue();
		matcher = pattern.matcher(submittedValue.toString());
		name = submittedValue.toString();
		em = EMF.getEM();
		ItemService i = new ItemService(em);
		Item newItem = i.findItemByName(name);
		if (oldValue != null && newItem != null) {
			// Mode modification

			String oldName = oldValue.toString();
			Item oldItem = i.findItemByName(oldName);
			em.close();
			if (oldItem.getItemId() != newItem.getItemId()) {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Cet objet existe déjà.", ""));
			}
		} else {
			// Mode ajout
			em.close();
			if (newItem == null) {
				if (!matcher.matches()) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Mauvais format d'objet. Seuls les alphanumériques sont acceptés. Longueur maximale 45 caractères.",
							""));
				}

			} else {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Cet objet existe déjà.", ""));
			}
		}
	}
}