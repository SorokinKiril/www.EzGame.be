package be.ezgame.service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import be.ezgame.model.ItemType;

public class ItemTypeService implements Serializable {

	private static final long serialVersionUID = 6178091128771574716L;
	private EntityManager em;

	public ItemTypeService(EntityManager em) {
		this.em = em;
	}

	// M�thode qui trouve tout les types d'objet pr�sents en base de donn�.
	public List<ItemType> findAll() {
		TypedQuery<ItemType> query = em.createNamedQuery("ItemType.findAll", ItemType.class);
		return query.getResultList();
	}

	// M�thode qui trouve le types d'objet pr�sent en base de donn� en fonction de l'id qui
	// lui est pass� en param�tre.
	public ItemType findItemTypeById(int itemTypeId) {
		try {
			return (ItemType) em.createNamedQuery("ItemType.findItemTypeById").setParameter("itemTypeId", itemTypeId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}