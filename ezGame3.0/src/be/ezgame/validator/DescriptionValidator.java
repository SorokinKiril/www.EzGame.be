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


@Named
@RequestScoped
@FacesValidator("DescriptionValidator")
public class DescriptionValidator implements Validator {


	private Pattern pattern;
	private Matcher matcher;

	private static final String DESCRIPTION_PATTERN ="^[_A-ZÁ-ÿa-z0-9- '.,]{1,255}+";

	public DescriptionValidator(){
		pattern = Pattern.compile(DESCRIPTION_PATTERN);
	}
	@Override
	public void validate(FacesContext context, UIComponent component, Object submittedValue){ 
		matcher = pattern.matcher(submittedValue.toString());
		if(!matcher.matches()){
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Mauvais format de description. Les caractères spéciaux ne sont pas permis. Seul sont autorisés -',.",
					""));	
		}
	}
}