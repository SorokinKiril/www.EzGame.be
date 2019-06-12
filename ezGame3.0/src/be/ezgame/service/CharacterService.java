package be.ezgame.service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import be.ezgame.model.Character;

public class CharacterService implements Serializable {

	private static final long serialVersionUID = 5854371002849990191L;
	private EntityManager em;

	public CharacterService(EntityManager em) {
		this.em = em;
	}

	// Méthode qui trouve tout les personnages présents en base de donné.
	public List<Character> findAll() {
		return em.createNamedQuery("Character.findAll", Character.class).getResultList();
	}
	
	// Méthode qui trouve tout les personnages vivants présents en base de donné.
	public List<Character> findAllAlive() {
		return em.createNamedQuery("Character.findAllAlive", Character.class).getResultList();
	}
	
	// Méthode qui trouve tout les personnages présents en base de donné et établit le classement.
	public List<Character> findLeaderboard() {
		return em.createNamedQuery("Character.findLeaderboard", Character.class).getResultList();
	}

	// Méthode qui trouve un personnage présent en base de donné en fonction du nom qui lui est passé en parametre.
	public Character findCharacterByName(String characterName) {
		try {
			return (Character) em.createNamedQuery("Character.findCharacterByName")
					.setParameter("characterName", characterName).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// Méthode qui trouve un personnage présent en base de donné en fonction de l'id qui lui est passé en parametre.
	public Character findCharacterById(int characterId) {
		try {
			return (Character) em.createNamedQuery("Character.findCharacterById")
					.setParameter("characterId", characterId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// Méthode qui trouve un personnage vivant présent en base de donné en fonction de l'utilisateur qui lui est passé en parametre.
	public Character findCharacterAliveByUser(int userId) {
		try {
	        return (Character) em.createNamedQuery("Character.findCharacterAliveByUser").setParameter("userId", userId)
		            .getSingleResult();
	      } catch (NoResultException e) {
	        return null;
	      }
	}

	// Méthode de création d'un nouveau personnage.
	public void create(Character character) {
		em.persist(character);
	}

	// Méthode de mise à jour d'un personnage.
	public void update(Character character) {
		em.merge(character);
	}
}