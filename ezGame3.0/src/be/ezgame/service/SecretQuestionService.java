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

	// M�thode qui trouve tout les questions s�cretes pr�sentes en base de donn�.
	public List<SecretQuestion> findAll() {
		TypedQuery<SecretQuestion> query = em.createNamedQuery("SecretQuestion.findAll", SecretQuestion.class);
		return query.getResultList();
	}

	// M�thode qui trouve la question s�crete pr�sent en base de donn� en fonction
	// du nom qui
	// lui est pass� en param�tre.
	public SecretQuestion findQuestionByName(String name) {
		try {
			return (SecretQuestion) em.createNamedQuery("SecretQuestion.findQuestionByName").setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// M�thode qui trouve la question s�crete pr�sente en base de donn� en fonction
	// de l'id qui
	// lui est pass� en param�tre.
	public SecretQuestion findQuestionById(int questionId) {
		try {
			return (SecretQuestion) em.createNamedQuery("SecretQuestion.findQuestionById")
					.setParameter("secretQuestionId", questionId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// M�thode de cr�ation d'une nouvelle question s�crete.
	public void create(SecretQuestion question) {
		em.persist(question);
	}

	// M�thode de mise � jour d'une question s�crete existante.
	public void update(SecretQuestion question) {
		em.merge(question);
	}
}