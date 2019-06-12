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

	// M�thode qui trouve tout les classes pr�sentes en base de donn�.
	public List<Classe> findAll() {
		TypedQuery<Classe> query = em.createNamedQuery("Classe.findAll", Classe.class);
		return query.getResultList();
	}

	// M�thode qui trouve la classe pr�sent en base de donn� en fonction du nom qui
	// lui est pass� en param�tre.
	public Classe findClasseByName(String classeName) {
		try {
			return (Classe) em.createNamedQuery("Classe.findClasseByName").setParameter("classeName", classeName)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// M�thode qui trouve la classe pr�sent en base de donn� en fonction de l'id qui
	// lui est pass� en param�tre.
	public Classe findClasseById(int classeId) {
		try {
			return (Classe) em.createNamedQuery("Classe.findClasseById").setParameter("classeId", classeId)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	// M�thode de cr�ation d'une nouvelle classe.
	public void create(Classe classe) {
		em.persist(classe);
	}

	// M�thode de mise � jour d'une classe existante.
	public void update(Classe classe) {
		em.merge(classe);
	}
}