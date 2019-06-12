package be.ezgame.i18n;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class I18nBean implements Serializable{

	private static final long serialVersionUID = 1352673498569935471L;
	private  Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
   
    public Locale getLocale() {
        return locale;
    }
	public void setLocale(Locale locale) {
			this.locale = locale;
		}
	
    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
    	locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}