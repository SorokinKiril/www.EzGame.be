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

	// M�thode qui trouve les inventaires d'un personnage pr�sent en base de donn�
	// en fonction du personnage qui
	// lui est pass� en param�tre.
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

	// M�thode qui trouve un objet equip� d'un personnage pr�sent en base de donn�
	// en fonction du type de l'objet dont le personnage veux s'�quiper.
	public CharacterInventory findCharacterInventoryByEquipedItem(CharacterInventory characterInventory) {
		try {
			return (CharacterInventory) em.createNamedQuery("CharacterInventory.findCharacterInventoryByEquipedItem")
					.setParameter("itemUse", true).setParameter("itemType", characterInventory.getItem().getItemType())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// M�thode de cr�ation d'une nouvelle entr�e dans l'inventaire d'un personnage.
	public void create(CharacterInventory characterInventory) {
		em.persist(characterInventory);
	}

	// M�thode de mise � jour d'une entr�e dans l'inventaire d'un personnage.
	public void update(CharacterInventory characterInventory) {
		em.merge(characterInventory);
	}
}