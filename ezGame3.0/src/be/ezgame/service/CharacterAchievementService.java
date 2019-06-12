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

	// M�thode qui trouve le succ�s d'un personnage pr�sent en base de donn� en
	// fonction du succ�s et du personnage qui
	// lui est pass� en param�tre.
	public List<CharacterAchievement> findByAchievementAndCharacter(Achievement achievement, Character character) {

		TypedQuery<CharacterAchievement> query = em
				.createNamedQuery("CharacterAchievement.findByAchievementAndCharacter", CharacterAchievement.class)
				.setParameter("achievement", achievement).setParameter("character", character);
		return query.getResultList();
	}

	// M�thode qui trouve le succ�s d'un personnage pr�sent en base de donn� en
	// fonction du personnage qui
	// lui est pass� en param�tre.
	public List<CharacterAchievement> findAchievementByCharacter(Character characterSession) {

		TypedQuery<CharacterAchievement> query = em
				.createNamedQuery("CharacterAchievement.findAchievementByCharacter", CharacterAchievement.class)
				.setParameter("character", characterSession);
		return query.getResultList();
	}

	// M�thode de cr�ation d'un succ�s pour un personnage.
	public void create(CharacterAchievement characterAchievement) {
		System.out.println("Service char name "+ characterAchievement.getCharacter().getCharacterName());
		System.out.println("Service achiev name "+ characterAchievement.getAchievement().getAchievementName());
		em.persist(characterAchievement);
	}

	// M�thode de mise � jour d'un succ�s por un personnage.
	public void update(CharacterAchievement characterAchievement) {
		em.merge(characterAchievement);
	}

}