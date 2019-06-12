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
@FacesValidator("ImageLinkValidator")
public class ImageLinkValidator implements Validator {


	private Pattern pattern;
	private Matcher matcher;
	
	private static final String DESCRIPTION_PATTERN ="^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|].(?:jpg|jpeg|gif|png|bmp){1,1000}+";

	public ImageLinkValidator(){
		pattern = Pattern.compile(DESCRIPTION_PATTERN);
	}
	@Override
	public void validate(FacesContext context, UIComponent component, Object submittedValue){ 
	    
		matcher = pattern.matcher(submittedValue.toString());
		if(!matcher.matches()){
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Mauvais format d'URL. L'URL de l'image doit se terminer par l'extension de l'image. (Jpg, jpeg, gif, Png, bmp)",
					""));	
		}
	}
}