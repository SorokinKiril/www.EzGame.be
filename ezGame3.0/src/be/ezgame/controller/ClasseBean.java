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
import be.ezgame.model.Classe;
import be.ezgame.service.ClasseService;

@Named
@ViewScoped
public class ClasseBean implements Serializable {

	private static final long serialVersionUID = 1276107825070111447L;

	private EntityManager em;
	private List<Classe> listClasse;
	private Classe classeEdit;

	public ClasseBean() {
	}

	@PostConstruct
	public void init() {
		classeEdit = new Classe();
		em = EMF.getEM();
		ClasseService cService = new ClasseService(em);
		listClasse = cService.findAll();
		em.close();
	}

	// Méthode d'ajout et de modification d'un succès.
	public void submitClasse() {

		em = EMF.getEM();
		ClasseService cService = new ClasseService(em);
		try {
			if (classeEdit.getClasseId() != null) {

				em.getTransaction().begin();
				cService.update(classeEdit);
				em.getTransaction().commit();

			} else {

				em.getTransaction().begin();
				cService.create(classeEdit);
				em.getTransaction().commit();

			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La sauvegarde de vos modifications a été effectué.", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "La sauvegarde de vos modifications a échoué.", ""));

		}
		classeEdit = new Classe();
		listClasse = cService.findAll();
		em.close();
	}

	// Méthode de fermeture de la boite de dialogue d'édition.
	public void submitCancel() {
		classeEdit = new Classe();
	}
	
	//Getters & Setters

	public List<Classe> getListClasse() {
		return listClasse;
	}

	public void setListClasse(List<Classe> listClasse) {
		this.listClasse = listClasse;
	}

	public Classe getClasseEdit() {
		return classeEdit;
	}

	public void setClasseEdit(Classe classeEdit) {
		this.classeEdit = classeEdit;
	}
}