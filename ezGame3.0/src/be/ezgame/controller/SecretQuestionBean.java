package be.ezgame.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;

import be.ezgame.emf.EMF;
import be.ezgame.model.SecretQuestion;
import be.ezgame.model.User;
import be.ezgame.service.SecretQuestionService;

@Named
@ViewScoped
public class SecretQuestionBean implements Serializable {
	private static final long serialVersionUID = -2407587737048830980L;

	private EntityManager em;
	private List<SecretQuestion> listQuestion;
	private SecretQuestion questionEdit;
	private User user;

	public SecretQuestionBean() {
	}

	@PostConstruct
	public void init() {
		questionEdit = new SecretQuestion();
		user = new User();
		em = EMF.getEM();
		SecretQuestionService sService = new SecretQuestionService(em);
		listQuestion = sService.findAll();
		System.out.println(listQuestion);
		em.close();
	}

	// Méthode d'ajout et de modification d'une question sécrete.
	public void submitQuestion() {
		em = EMF.getEM();
		SecretQuestionService sService = new SecretQuestionService(em);
		try {
			if (questionEdit.getSecretQuestionId() != null) {

				em.getTransaction().begin();
				sService.update(questionEdit);
				em.getTransaction().commit();

			} else {

				em.getTransaction().begin();
				sService.create(questionEdit);
				em.getTransaction().commit();

			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La sauvegarde de vos modifications a été effectué.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La sauvegarde de vos modifications a échoué.", ""));

		}
		questionEdit = new SecretQuestion();
		listQuestion = sService.findAll();
		em.close();
	}

	// Méthode de fermeture de la boite de dialogue d'édition.
	public void submitCancel() {
		questionEdit = new SecretQuestion();
	}

	//Getters & Setters

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<SecretQuestion> getListQuestion() {
		return listQuestion;
	}

	public void setListQuestion(List<SecretQuestion> listQuestion) {
		this.listQuestion = listQuestion;
	}

	public SecretQuestion getQuestionEdit() {
		return questionEdit;
	}

	public void setQuestionEdit(SecretQuestion questionEdit) {
		this.questionEdit = questionEdit;
	}
}