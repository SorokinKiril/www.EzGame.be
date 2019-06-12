package be.ezgame.service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import be.ezgame.model.User;

public class UserService implements Serializable {

	private static final long serialVersionUID = 7094711607039872276L;

	private EntityManager em;

	public UserService(EntityManager em) {
		this.em = em;
	}

	// M�thode qui trouve tout les utilisateurs pr�sents en base de donn�.
	public List<User> findAll() {
		TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);
		return query.getResultList();
	}

	// M�thode qui trouve l'utilisateur pr�sent en base de donn� en fonction de
	// l'adresse e-mail qui lui est pass� en param�tre.
	public User findUserByMail(String userMail) {
		try {
			User user = (User) em.createNamedQuery("User.findUserByMail").setParameter("userMail", userMail)
					.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	// M�thode qui trouve l'utilisateur pr�sent en base de donn� en fonction du nom
	// qui lui est pass� en param�tre.
	public User findUserByLogin(String userName) {
		try {
			User user = (User) em.createNamedQuery("User.findUserByName").setParameter("userName", userName)
					.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	// M�thode qui trouve l'utilisateur pr�sent en base de donn� en fonction de l'id
	// qui lui est pass� en param�tre.
	public User findUserById(Integer userId) {
		try {
			User user = (User) em.createNamedQuery("User.findUserById").setParameter("userId", userId)
					.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	// M�thode qui trouve l'utilisateur pr�sent en base de donn� en fonction du
	// login et du mot de passe qui lui sont pass�s en param�tre.
	public User loginUser(String login, String password) {
		try {
			User user = (User) em.createNamedQuery("User.findUserByLogin").setParameter("login", login)
					.setParameter("password", password).getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	// M�thode de cr�ation d'un nouvel utilisateur.
	public void create(User user) {
		em.persist(user);
	}

	// M�thode de mise � jour d'un utilisateur existant.
	public void update(User user) {
		em.merge(user);
	}
}