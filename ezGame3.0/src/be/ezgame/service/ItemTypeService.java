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

	// Méthode qui trouve tout les types d'objet présents en base de donné.
	public List<ItemType> findAll() {
		TypedQuery<ItemType> query = em.createNamedQuery("ItemType.findAll", ItemType.class);
		return query.getResultList();
	}

	// Méthode qui trouve le types d'objet présent en base de donné en fonction de l'id qui
	// lui est passé en paramétre.
	public ItemType findItemTypeById(int itemTypeId) {
		try {
			return (ItemType) em.createNamedQuery("ItemType.findItemTypeById").setParameter("itemTypeId", itemTypeId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}