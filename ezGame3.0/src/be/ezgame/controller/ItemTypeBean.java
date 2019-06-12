package be.ezgame.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;

import java.util.List;

import be.ezgame.emf.EMF;
import be.ezgame.model.ItemType;
import be.ezgame.service.ItemTypeService;


@Named
@ViewScoped
public class ItemTypeBean implements Serializable {

	private static final long serialVersionUID = 8233335193533806413L;

	private EntityManager em;
	private List<ItemType> listItemType;
	
	public ItemTypeBean(){	
	}
	
	@PostConstruct
	public void init(){
		em=EMF.getEM();
		ItemTypeService iService = new ItemTypeService(em);
		listItemType = iService.findAll();
		em.close();
	}
	
	//Getter & Setter

	public List<ItemType> getListItemType() {
		return listItemType;
	}

	public void setListItemType(List<ItemType> listItemType) {
		this.listItemType = listItemType;
	}
}