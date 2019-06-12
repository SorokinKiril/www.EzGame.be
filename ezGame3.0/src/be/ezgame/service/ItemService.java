package be.ezgame.service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import be.ezgame.model.Item;

public class ItemService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3087621205542820449L;
	private EntityManager em;

	public ItemService(EntityManager em) {
		this.em = em;
	}

	// Méthode qui trouve tout les objets présents en base de donné.
	public List<Item> findAll() {
		TypedQuery<Item> query = em.createNamedQuery("Item.findAll", Item.class);
		return query.getResultList();
	}

	// Méthode qui trouve l'objet présent en base de donné en fonction du nom qui
	// lui est passé en paramétre.
	public Item findItemByName(String itemName) {
		try {
			return (Item) em.createNamedQuery("Item.findItemByName").setParameter("itemName", itemName)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	// Méthode qui trouve l'objet présent en base de donné en fonction de l'id qui
	// lui est passé en paramétre.
	public Item findItemById(int itemId) {
		try {
			return (Item) em.createNamedQuery("Item.findItemById").setParameter("itemId", itemId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	// Méthode de création d'un nouveau succès.
	public void create(Item item) {
		em.persist(item);
	}

	// Méthode de mise à jour d'un succès existant.
	public void update(Item item) {
		em.merge(item);
	}
}