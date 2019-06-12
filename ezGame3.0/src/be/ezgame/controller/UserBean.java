package be.ezgame.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.FlowEvent;

import java.util.List;

import be.ezgame.emf.EMF;
import be.ezgame.model.User;
import be.ezgame.service.RoleService;
import be.ezgame.service.UserService;
import be.ezgame.controller.CharacterBean;

@Named
@ViewScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1990140200423768742L;

	private EntityManager em;
	private List<User> listUser;
	private User userEdit;
	private User userSession;
	private String oldPassword;
	private String newPassword;

	@Inject
	private CharacterBean characterBean;

	public UserBean() {
	}

	@PostConstruct
	public void init() {

		userEdit = new User();
		em = EMF.getEM();
		UserService uService = new UserService(em);
		listUser = uService.findAll();
		Subject subject = SecurityUtils.getSubject();
		Integer userId = (Integer) subject.getPrincipal();
		userSession = uService.findUserById(userId);
		em.close();
	}

	public void createNewUser() {
		em = EMF.getEM();
		UserService uService = new UserService(em);
		RoleService rService = new RoleService(em);
		userEdit.setRole(rService.findRoleById(3));

		try {

			String salt = generateSalt().toString();
			userEdit.setUserPasswordSalt(salt);
			String hashedPasswordBase64 = new Sha256Hash(userEdit.getUserPassword(), salt, 1024).toBase64();
			userEdit.setUserPassword(hashedPasswordBase64);

			em.getTransaction().begin();
			uService.create(userEdit);
			em.getTransaction().commit();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Votre inscription a été effectué.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Votre inscription a été échoué.", ""));
		}

		User userCreated = uService.findUserByLogin(userEdit.getUserName());
		characterBean.submitNewCharacter(userCreated);

		userEdit = new User();
		em.close();

	}

	// Méthode de mise à jour d'un utilisateur par l'adminnistatreur.
	public void submitUser() {
		em = EMF.getEM();
		UserService uService = new UserService(em);

		try {
			em.getTransaction().begin();
			uService.update(userEdit);
			em.getTransaction().commit();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La mise à jour de l'utilisateur a été effectué.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La mise à jour de l'utilisateur a été échoué.", ""));
		}
		userEdit = new User();
		listUser = uService.findAll();
		em.close();
	}

	// Méthode de mise & jour des parametres d'un utilisateur.
	public void updateUserSession() {
		em = EMF.getEM();
		UserService uService = new UserService(em);

		try {
			String hashedOldPasswordBase64 = new Sha256Hash(oldPassword, userSession.getUserPasswordSalt(), 1024)
					.toBase64();

			if (!userSession.getUserPassword().equals(hashedOldPasswordBase64)) {
				throw new Exception();
			} else {

				String hashedNewPasswordBase64 = new Sha256Hash(newPassword,
						userSession.getUserPasswordSalt(), 1024).toBase64();
				userSession.setUserPassword(hashedNewPasswordBase64);

				em.getTransaction().begin();
				uService.update(userSession);
				em.getTransaction().commit();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"La mise à jour de vos informations a été effectuée.", ""));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Votre ancien mot de passe est incorrect.", ""));
		}
		em.close();
	}

	// Génere le salt pour l'encodage du mot de passe.
	public Object generateSalt() {

		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		Object salt = rng.nextBytes();
		return salt;

	}

	// Méthode de défilement des different panneaux lors de l'inscription.
	public String onFlowProcess(FlowEvent event) {
		return event.getNewStep();
	}

	// Méthode de fermeture de la boite de dialogue d'édition.
	public void submitCancel() {
		userEdit = new User();
	}

	// Getters & Setters

	public List<User> getListUser() {
		return listUser;
	}

	public void setListUser(List<User> listUser) {
		this.listUser = listUser;
	}

	public User getUserEdit() {
		return userEdit;
	}

	public void setUserEdit(User userEdit) {
		this.userEdit = userEdit;
	}

	public User getUserSession() {
		return userSession;
	}

	public void setUserSession(User userSession) {
		this.userSession = userSession;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}