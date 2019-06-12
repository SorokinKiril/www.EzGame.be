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

	// Méthode qui trouve tout les succès présents en base de donné.
	public List<Achievement> findAll() {
		TypedQuery<Achievement> query = em.createNamedQuery("Achievement.findAll", Achievement.class);
		return query.getResultList();
	}

	// Méthode qui trouve le succès présent en base de donné en fonction du nom qui
	// lui est passé en paramétre.
	public Achievement findAchievementByName(String achievementName) {
		try {
			return (Achievement) em.createNamedQuery("Achievement.findAchievementByName")
					.setParameter("achievementName", achievementName).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// Méthode qui trouve le succès présent en base de donné en fonction de l'id qui
	// lui est passé en paramétre.
	public Achievement findAchievementById(int achievementId) {
		try {
			return (Achievement) em.createNamedQuery("Achievement.findAchievementById")
					.setParameter("achievementId", achievementId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	// Méthode de création d'un nouveau succès.
	public void create(Achievement achievement) {
		em.persist(achievement);
	}

	// Méthode de mise à jour d'un succès existant.
	public void update(Achievement achievement) {
		em.merge(achievement);
	}

}