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

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import be.ezgame.emf.EMF;
import be.ezgame.model.User;
import be.ezgame.service.UserService;

@Named
@RequestScoped
@FacesValidator("mailValidator")
public class MailValidator implements Validator {

	private String mail;
	private Pattern pattern;
	private Matcher matcher;
	private EntityManager em;

	private static final String MAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\]){1,45}+";

	public MailValidator() {
		pattern = Pattern.compile(MAIL_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object submittedValue) {
		matcher = pattern.matcher(submittedValue.toString());
		mail = submittedValue.toString();

		em = EMF.getEM();
		UserService uService = new UserService(em);
		User existingUser = uService.findUserByMail(mail);
		Subject currentUser = SecurityUtils.getSubject();

		em.close();
		if (existingUser != null) {
			if (currentUser.getPrincipal() != null) {
				Integer userId = (int) currentUser.getPrincipal();
				if (existingUser.getUserId() != userId) {
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Choisissez un autre mail, celui-ci est déjà associé à un autre compte.", ""));
				}
			} else {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Choisissez un autre mail, celui-ci est déjà associé à un autre compte.", ""));
			}
		} else {
			if (!matcher.matches()) {
				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Mauvais format de mail.", ""));
			}
		}
	}
}
