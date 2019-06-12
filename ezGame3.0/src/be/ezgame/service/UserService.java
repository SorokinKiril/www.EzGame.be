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

	// Méthode qui trouve tout les utilisateurs présents en base de donné.
	public List<User> findAll() {
		TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);
		return query.getResultList();
	}

	// Méthode qui trouve l'utilisateur présent en base de donné en fonction de
	// l'adresse e-mail qui lui est passé en paramétre.
	public User findUserByMail(String userMail) {
		try {
			User user = (User) em.createNamedQuery("User.findUserByMail").setParameter("userMail", userMail)
					.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	// Méthode qui trouve l'utilisateur présent en base de donné en fonction du nom
	// qui lui est passé en paramétre.
	public User findUserByLogin(String userName) {
		try {
			User user = (User) em.createNamedQuery("User.findUserByName").setParameter("userName", userName)
					.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	// Méthode qui trouve l'utilisateur présent en base de donné en fonction de l'id
	// qui lui est passé en paramétre.
	public User findUserById(Integer userId) {
		try {
			User user = (User) em.createNamedQuery("User.findUserById").setParameter("userId", userId)
					.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	// Méthode qui trouve l'utilisateur présent en base de donné en fonction du
	// login et du mot de passe qui lui sont passés en paramétre.
	public User loginUser(String login, String password) {
		try {
			User user = (User) em.createNamedQuery("User.findUserByLogin").setParameter("login", login)
					.setParameter("password", password).getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	// Méthode de création d'un nouvel utilisateur.
	public void create(User user) {
		em.persist(user);
	}

	// Méthode de mise à jour d'un utilisateur existant.
	public void update(User user) {
		em.merge(user);
	}
}