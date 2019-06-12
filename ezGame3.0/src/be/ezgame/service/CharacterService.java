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

	// M�thode qui trouve tout les personnages pr�sents en base de donn�.
	public List<Character> findAll() {
		return em.createNamedQuery("Character.findAll", Character.class).getResultList();
	}
	
	// M�thode qui trouve tout les personnages vivants pr�sents en base de donn�.
	public List<Character> findAllAlive() {
		return em.createNamedQuery("Character.findAllAlive", Character.class).getResultList();
	}
	
	// M�thode qui trouve tout les personnages pr�sents en base de donn� et �tablit le classement.
	public List<Character> findLeaderboard() {
		return em.createNamedQuery("Character.findLeaderboard", Character.class).getResultList();
	}

	// M�thode qui trouve un personnage pr�sent en base de donn� en fonction du nom qui lui est pass� en parametre.
	public Character findCharacterByName(String characterName) {
		try {
			return (Character) em.createNamedQuery("Character.findCharacterByName")
					.setParameter("characterName", characterName).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// M�thode qui trouve un personnage pr�sent en base de donn� en fonction de l'id qui lui est pass� en parametre.
	public Character findCharacterById(int characterId) {
		try {
			return (Character) em.createNamedQuery("Character.findCharacterById")
					.setParameter("characterId", characterId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// M�thode qui trouve un personnage vivant pr�sent en base de donn� en fonction de l'utilisateur qui lui est pass� en parametre.
	public Character findCharacterAliveByUser(int userId) {
		try {
	        return (Character) em.createNamedQuery("Character.findCharacterAliveByUser").setParameter("userId", userId)
		            .getSingleResult();
	      } catch (NoResultException e) {
	        return null;
	      }
	}

	// M�thode de cr�ation d'un nouveau personnage.
	public void create(Character character) {
		em.persist(character);
	}

	// M�thode de mise � jour d'un personnage.
	public void update(Character character) {
		em.merge(character);
	}
}