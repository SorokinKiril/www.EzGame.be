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

	// M�thode qui trouve tout les objets pr�sents en base de donn�.
	public List<Item> findAll() {
		TypedQuery<Item> query = em.createNamedQuery("Item.findAll", Item.class);
		return query.getResultList();
	}

	// M�thode qui trouve l'objet pr�sent en base de donn� en fonction du nom qui
	// lui est pass� en param�tre.
	public Item findItemByName(String itemName) {
		try {
			return (Item) em.createNamedQuery("Item.findItemByName").setParameter("itemName", itemName)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	// M�thode qui trouve l'objet pr�sent en base de donn� en fonction de l'id qui
	// lui est pass� en param�tre.
	public Item findItemById(int itemId) {
		try {
			return (Item) em.createNamedQuery("Item.findItemById").setParameter("itemId", itemId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	// M�thode de cr�ation d'un nouveau succ�s.
	public void create(Item item) {
		em.persist(item);
	}

	// M�thode de mise � jour d'un succ�s existant.
	public void update(Item item) {
		em.merge(item);
	}
}