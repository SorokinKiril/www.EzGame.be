package be.ezgame.service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import be.ezgame.model.CharacterInventory;
import be.ezgame.model.Character;

public class CharacterInventoryService implements Serializable {

	private static final long serialVersionUID = 8777823355765625269L;
	private EntityManager em;

	public CharacterInventoryService(EntityManager em) {
		this.em = em;
	}

	// Méthode qui trouve les inventaires d'un personnage présent en base de donné
	// en fonction du personnage qui
	// lui est passé en paramétre.
	@SuppressWarnings("unchecked")
	public List<CharacterInventory> findCharacterInventoryByCharacter(Character character) {
		try {
			return (List<CharacterInventory>) em
					.createNamedQuery("CharacterInventory.findCharacterInventoryByCharacter")
					.setParameter("character", character).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	// Méthode qui trouve un objet equipé d'un personnage présent en base de donné
	// en fonction du type de l'objet dont le personnage veux s'équiper.
	public CharacterInventory findCharacterInventoryByEquipedItem(CharacterInventory characterInventory) {
		try {
			return (CharacterInventory) em.createNamedQuery("CharacterInventory.findCharacterInventoryByEquipedItem")
					.setParameter("itemUse", true).setParameter("itemType", characterInventory.getItem().getItemType())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// Méthode de création d'une nouvelle entrée dans l'inventaire d'un personnage.
	public void create(CharacterInventory characterInventory) {
		em.persist(characterInventory);
	}

	// Méthode de mise à jour d'une entrée dans l'inventaire d'un personnage.
	public void update(CharacterInventory characterInventory) {
		em.merge(characterInventory);
	}
}