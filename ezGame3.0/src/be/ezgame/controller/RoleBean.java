package be.ezgame.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;

import java.util.List;


import be.ezgame.emf.EMF;
import be.ezgame.model.Role;
import be.ezgame.service.RoleService;



@Named
@ViewScoped
public class RoleBean implements Serializable {

	private static final long serialVersionUID = -4116814159004917789L;

	private EntityManager em;
	private List<Role> listRole;

	public RoleBean() {
	}

	@PostConstruct
	public void init() {
		em = EMF.getEM();
		RoleService iService = new RoleService(em);
		listRole = iService.findAll();
		em.close();
		
	}

	//Getter & Setter
	
	public List<Role> getListRole() {
		return listRole;
	}

	public void setListRole(List<Role> listRole) {
		this.listRole = listRole;
	}

}