package be.ezgame.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.FlowEvent;

import be.ezgame.emf.EMF;
import be.ezgame.model.User;
import be.ezgame.service.UserService;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String login;
	private String password;
	private String answer;
	private Session session;
	private EntityManager em;
	private User user;

	public LoginBean() {
	}

	// Méthode de connexion.
	public String checkLogin() {
		em = EMF.getEM();

		UsernamePasswordToken token = new UsernamePasswordToken(login, password);
		try {
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			session = subject.getSession();
			int userId = (int) subject.getPrincipal();
			UserService userService = new UserService(em);
			user = userService.findUserById(userId);
			session.setAttribute("username", user.getUserName());
			session.setAttribute("role", user.getRole().getRoleId());

			return "/app/home.xhtml?faces-redirect=true";
		} catch (AuthenticationException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nom d'utilisateur ou mot de passe incorrect.", ""));
			return null;
		}
	}

	// Méthode de réinitialisation du mot de passe d'un utilisateur.
	public void resetUserPassword() {
		em = EMF.getEM();
		UserService userService = new UserService(em);
		String hashedNewPasswordBase64 = new Sha256Hash(password, user.getUserPasswordSalt(), 1024).toBase64();
		user.setUserPassword(hashedNewPasswordBase64);
		try {
			if (!user.getUserAnswer().equals(answer)) {
				throw new Exception();
			}
			em.getTransaction().begin();
			userService.update(user);
			em.getTransaction().commit();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Le mot de passe a été réinitialisé.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le mot de passe n'a pas été réinitialisé.", ""));
		}
		em.close();
		user = new User();
		answer = null;
	}

	// Méthode de défillement des differents panneaux lors de la réinitialisation du mot de passe d'un utilisateur.
	public String onFlowProcess(FlowEvent event) {
		if (!login.equals(null) && user == null) {
			em = EMF.getEM();
			UserService userService = new UserService(em);
			user = userService.findUserByLogin(login);
			em.close();
		}
		return event.getNewStep();
	}

	// Méthode de déconnexion.
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtml?faces-redirect=true";
	}

	//Getters & Setters
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
