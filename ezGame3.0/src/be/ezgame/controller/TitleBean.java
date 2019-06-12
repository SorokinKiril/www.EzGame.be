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
import be.ezgame.model.Title;
import be.ezgame.service.TitleService;

@Named
@ViewScoped
public class TitleBean implements Serializable {
	private static final long serialVersionUID = -2407587737048830980L;

	private EntityManager em;
	private List<Title> listTitle;
	private Title titleEdit;
	private Achievement achievement;

	public TitleBean() {
	}

	@PostConstruct
	public void init() {
		titleEdit = new Title();
		achievement = new Achievement();
		// titleEdit.setTitleName("name");
		em = EMF.getEM();
		TitleService tService = new TitleService(em);
		listTitle = tService.findAll();
		em.close();
	}

	// M�thode de mise � jour/ cr�ation d'un titre.
	public void submitTitle() {
		em = EMF.getEM();
		TitleService tService = new TitleService(em);
		try {
			if (titleEdit.getTitleId() != null) {

				em.getTransaction().begin();
				tService.update(titleEdit);
				em.getTransaction().commit();

			} else {

				em.getTransaction().begin();
				tService.create(titleEdit);
				em.getTransaction().commit();

			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La sauvegarde de vos modifications a �t� effectu�.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La sauvegarde de vos modifications a �chou�.", ""));

		}
		titleEdit = new Title();
		listTitle = tService.findAll();
		em.close();
	}

	// M�thode de fermeture de la boite de dialogue d'�dition.
	public void submitCancel() {
		titleEdit = new Title();
	}

	// Getters & Setters

	public Achievement getAchievement() {
		return achievement;
	}

	public void setAchievement(Achievement achievement) {
		this.achievement = achievement;
	}

	public Title getTitleEdit() {
		return titleEdit;
	}

	public void setTitleEdit(Title titleEdit) {
		this.titleEdit = titleEdit;
	}

	public List<Title> getListTitle() {
		return listTitle;
	}

	public void setListTitle(List<Title> listTitle) {
		this.listTitle = listTitle;
	}
}