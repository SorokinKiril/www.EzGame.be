package be.ezgame.service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import be.ezgame.model.CharacterAchievement;
import be.ezgame.model.Achievement;
import be.ezgame.model.Character;

public class CharacterAchievementService implements Serializable {

	private static final long serialVersionUID = -1693690894525685901L;
	private EntityManager em;

	public CharacterAchievementService(EntityManager em) {
		this.em = em;
	}

	// Méthode qui trouve le succés d'un personnage présent en base de donné en
	// fonction du succés et du personnage qui
	// lui est passé en paramétre.
	public List<CharacterAchievement> findByAchievementAndCharacter(Achievement achievement, Character character) {

		TypedQuery<CharacterAchievement> query = em
				.createNamedQuery("CharacterAchievement.findByAchievementAndCharacter", CharacterAchievement.class)
				.setParameter("achievement", achievement).setParameter("character", character);
		return query.getResultList();
	}

	// Méthode qui trouve le succés d'un personnage présent en base de donné en
	// fonction du personnage qui
	// lui est passé en paramétre.
	public List<CharacterAchievement> findAchievementByCharacter(Character characterSession) {

		TypedQuery<CharacterAchievement> query = em
				.createNamedQuery("CharacterAchievement.findAchievementByCharacter", CharacterAchievement.class)
				.setParameter("character", characterSession);
		return query.getResultList();
	}

	// Méthode de création d'un succés pour un personnage.
	public void create(CharacterAchievement characterAchievement) {
		System.out.println("Service char name "+ characterAchievement.getCharacter().getCharacterName());
		System.out.println("Service achiev name "+ characterAchievement.getAchievement().getAchievementName());
		em.persist(characterAchievement);
	}

	// Méthode de mise à jour d'un succés por un personnage.
	public void update(CharacterAchievement characterAchievement) {
		em.merge(characterAchievement);
	}

}