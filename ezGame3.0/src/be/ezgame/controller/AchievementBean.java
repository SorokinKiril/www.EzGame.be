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
import be.ezgame.model.Achievement;
import be.ezgame.service.AchievementService;

@Named
@ViewScoped
public class AchievementBean implements Serializable {

	private static final long serialVersionUID = -2190162846650813466L;
	private EntityManager em;
	private List<Achievement> listAchievement;
	private Achievement achievementEdit;

	public AchievementBean() {
	}

	@PostConstruct
	public void init() {
		em = EMF.getEM();
		AchievementService aService = new AchievementService(em);
		achievementEdit = new Achievement();
		listAchievement = aService.findAll();
		em.close();
	}

	// M�thode d'ajout et de modification d'un succ�s.
	public void submitAchievement() {

		em = EMF.getEM();
		AchievementService aService = new AchievementService(em);
		try {

			if (achievementEdit.getAchievementId() != null) {

				em.getTransaction().begin();
				aService.update(achievementEdit);
				em.getTransaction().commit();

			} else {

				em.getTransaction().begin();
				aService.create(achievementEdit);
				em.getTransaction().commit();

			}

			achievementEdit = new Achievement();
			listAchievement = aService.findAll();
			em.close();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La sauvegarde de vos modifications a �t� effectu�.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La sauvegarde de vos modifications a �chou�.", ""));

		}
	}

	// M�thode de fermeture de la boite de dialogue d'�dition.
	public void submitCancel() {
		achievementEdit = new Achievement();
	}

	// Getters et Setters
	public List<Achievement> getListAchievement() {
		return listAchievement;
	}

	public void setListAchievement(List<Achievement> listAchievement) {
		this.listAchievement = listAchievement;
	}

	public Achievement getAchievementEdit() {
		return achievementEdit;
	}

	public void setAchievementEdit(Achievement achievementEdit) {
		this.achievementEdit = achievementEdit;
	}
}