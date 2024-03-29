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
import be.ezgame.model.Title;
import be.ezgame.service.TitleService;

@Named
@RequestScoped
@FacesValidator("TitleValidator")
public class TitleValidator implements Validator {

	private String name;
	private Pattern pattern;
	private Matcher matcher;
	private EntityManager em;

	private static final String TITLE_PATTERN = "^[_A-Z�-�a-z0-9-]{1,45}+";

	public TitleValidator() {
		pattern = Pattern.compile(TITLE_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object submittedValue) {
		Object oldValue = ((UIInput) component).getValue();
		matcher = pattern.matcher(submittedValue.toString());
		name = submittedValue.toString();
		em = EMF.getEM();
		TitleService t = new TitleService(em);
		Title newTitle = t.findTitleByName(name);

		if (oldValue != null && newTitle != null) {
			// Mode modification

			String oldName = oldValue.toString();
			Title oldTitle = t.findTitleByName(oldName);
			em.close();
			if (oldTitle.getTitleId() != newTitle.getTitleId()) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Ce titre existe d�j�", ""));
			}
		} else {
			// Mode ajout
			em.close();
			if (newTitle == null) {
				if (!matcher.matches()) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Mauvais format de titre. Seuls les alphanum�riques sont accept�s. Longueur maximale 45 caract�res.",
							""));
				}
			} else {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Ce titre existe d�j�.", ""));
			}
		}
	}
}