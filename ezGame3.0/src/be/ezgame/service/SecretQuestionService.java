package be.ezgame.service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import be.ezgame.model.SecretQuestion;

public class SecretQuestionService implements Serializable {

	private static final long serialVersionUID = -7804660371718734982L;

	private EntityManager em;

	public SecretQuestionService(EntityManager em) {
		this.em = em;
	}

	// Méthode qui trouve tout les questions sécretes présentes en base de donné.
	public List<SecretQuestion> findAll() {
		TypedQuery<SecretQuestion> query = em.createNamedQuery("SecretQuestion.findAll", SecretQuestion.class);
		return query.getResultList();
	}

	// Méthode qui trouve la question sécrete présent en base de donné en fonction
	// du nom qui
	// lui est passé en paramétre.
	public SecretQuestion findQuestionByName(String name) {
		try {
			return (SecretQuestion) em.createNamedQuery("SecretQuestion.findQuestionByName").setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// Méthode qui trouve la question sécrete présente en base de donné en fonction
	// de l'id qui
	// lui est passé en paramétre.
	public SecretQuestion findQuestionById(int questionId) {
		try {
			return (SecretQuestion) em.createNamedQuery("SecretQuestion.findQuestionById")
					.setParameter("secretQuestionId", questionId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// Méthode de création d'une nouvelle question sécrete.
	public void create(SecretQuestion question) {
		em.persist(question);
	}

	// Méthode de mise à jour d'une question sécrete existante.
	public void update(SecretQuestion question) {
		em.merge(question);
	}
}