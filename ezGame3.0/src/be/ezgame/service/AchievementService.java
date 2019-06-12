package be.ezgame.service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import be.ezgame.model.Achievement;

public class AchievementService implements Serializable {

	private static final long serialVersionUID = -4277417060854525652L;

	private EntityManager em;

	public AchievementService(EntityManager em) {
		this.em = em;
	}

	// M�thode qui trouve tout les succ�s pr�sents en base de donn�.
	public List<Achievement> findAll() {
		TypedQuery<Achievement> query = em.createNamedQuery("Achievement.findAll", Achievement.class);
		return query.getResultList();
	}

	// M�thode qui trouve le succ�s pr�sent en base de donn� en fonction du nom qui
	// lui est pass� en param�tre.
	public Achievement findAchievementByName(String achievementName) {
		try {
			return (Achievement) em.createNamedQuery("Achievement.findAchievementByName")
					.setParameter("achievementName", achievementName).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// M�thode qui trouve le succ�s pr�sent en base de donn� en fonction de l'id qui
	// lui est pass� en param�tre.
	public Achievement findAchievementById(int achievementId) {
		try {
			return (Achievement) em.createNamedQuery("Achievement.findAchievementById")
					.setParameter("achievementId", achievementId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	// M�thode de cr�ation d'un nouveau succ�s.
	public void create(Achievement achievement) {
		em.persist(achievement);
	}

	// M�thode de mise � jour d'un succ�s existant.
	public void update(Achievement achievement) {
		em.merge(achievement);
	}

}