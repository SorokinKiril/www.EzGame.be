package be.ezgame.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import be.ezgame.model.Race;

public class RaceService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8578017374117003189L;
	private EntityManager em;

	public RaceService(EntityManager em) {
		this.em = em;
	}

	// Méthode qui trouve tout les races présentes en base de donné.
	public List<Race> findAll() {
		TypedQuery<Race> query = em.createNamedQuery("Race.findAll", Race.class);
		return query.getResultList();
	}

	// Méthode qui trouve la race présente en base de donné en fonction du nom qui
	// lui est passé en paramétre.
	public Race findTitleByName(String raceName) {
		try {
			return (Race) em.createNamedQuery("Race.findRaceByName").setParameter("raceName", raceName)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	// Méthode qui trouve la race présent en base de donné en fonction de l'id qui
	// lui est passé en paramétre.
	public Race findTitleById(int raceId) {
		try {
			return (Race) em.createNamedQuery("Race.findRaceById").setParameter("raceId", raceId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// Méthode de création d'une nouvelle race.
	public void create(Race race) {
		em.persist(race);
	}

	// Méthode de mise à jour d'un race existant.
	public void update(Race race) {
		em.merge(race);
	}
}