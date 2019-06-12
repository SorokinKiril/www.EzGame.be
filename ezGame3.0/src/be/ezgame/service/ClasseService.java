package be.ezgame.service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import be.ezgame.model.Classe;

public class ClasseService implements Serializable {

	private static final long serialVersionUID = 1131242703617900505L;
	private EntityManager em;

	public ClasseService(EntityManager em) {
		this.em = em;
	}

	// Méthode qui trouve tout les classes présentes en base de donné.
	public List<Classe> findAll() {
		TypedQuery<Classe> query = em.createNamedQuery("Classe.findAll", Classe.class);
		return query.getResultList();
	}

	// Méthode qui trouve la classe présent en base de donné en fonction du nom qui
	// lui est passé en paramétre.
	public Classe findClasseByName(String classeName) {
		try {
			return (Classe) em.createNamedQuery("Classe.findClasseByName").setParameter("classeName", classeName)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// Méthode qui trouve la classe présent en base de donné en fonction de l'id qui
	// lui est passé en paramétre.
	public Classe findClasseById(int classeId) {
		try {
			return (Classe) em.createNamedQuery("Classe.findClasseById").setParameter("classeId", classeId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	// Méthode de création d'une nouvelle classe.
	public void create(Classe classe) {
		em.persist(classe);
	}

	// Méthode de mise à jour d'une classe existante.
	public void update(Classe classe) {
		em.merge(classe);
	}
}