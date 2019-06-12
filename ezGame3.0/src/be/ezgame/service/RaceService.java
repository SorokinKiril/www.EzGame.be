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

	// M�thode qui trouve tout les races pr�sentes en base de donn�.
	public List<Race> findAll() {
		TypedQuery<Race> query = em.createNamedQuery("Race.findAll", Race.class);
		return query.getResultList();
	}

	// M�thode qui trouve la race pr�sente en base de donn� en fonction du nom qui
	// lui est pass� en param�tre.
	public Race findTitleByName(String raceName) {
		try {
			return (Race) em.createNamedQuery("Race.findRaceByName").setParameter("raceName", raceName)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	// M�thode qui trouve la race pr�sent en base de donn� en fonction de l'id qui
	// lui est pass� en param�tre.
	public Race findTitleById(int raceId) {
		try {
			return (Race) em.createNamedQuery("Race.findRaceById").setParameter("raceId", raceId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// M�thode de cr�ation d'une nouvelle race.
	public void create(Race race) {
		em.persist(race);
	}

	// M�thode de mise � jour d'un race existant.
	public void update(Race race) {
		em.merge(race);
	}
}