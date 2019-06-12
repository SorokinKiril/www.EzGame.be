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
import be.ezgame.model.Race;
import be.ezgame.model.Character;
import be.ezgame.service.RaceService;

@Named
@ViewScoped
public class RaceBean implements Serializable {

	private static final long serialVersionUID = 5392458573884617370L;

	private EntityManager em;
	private List<Race> listRace;
	private Race raceEdit;
	private Character character;

	public RaceBean() {
	}

	@PostConstruct
	public void init() {
		character = new Character();
		raceEdit = new Race();
		em = EMF.getEM();
		RaceService rService = new RaceService(em);
		listRace = rService.findAll();
		em.close();
	}

	// Méthode d'ajout et de modification d'une race.
	public void submitRace() {
		em = EMF.getEM();
		RaceService rService = new RaceService(em);
		try {
			if (raceEdit.getRaceId() != null) {

				em.getTransaction().begin();
				rService.update(raceEdit);
				em.getTransaction().commit();

			} else {

				em.getTransaction().begin();
				rService.create(raceEdit);
				em.getTransaction().commit();

			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La sauvegarde de vos modifications a été effectué.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La sauvegarde de vos modifications a échoué.", ""));
		}
		raceEdit = new Race();
		listRace = rService.findAll();
		em.close();
	}

	// Méthode de fermeture de la boite de dialogue d'édition.
	public void submitCancel() {
		raceEdit = new Race();
	}

	// Getters & Setters

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public List<Race> getListRace() {
		return listRace;
	}

	public void setListRace(List<Race> listRace) {
		this.listRace = listRace;
	}

	public Race getRaceEdit() {
		return raceEdit;
	}

	public void setRaceEdit(Race raceEdit) {
		this.raceEdit = raceEdit;
	}

}