package be.ezgame.service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import be.ezgame.model.Title;

public class TitleService implements Serializable{

	private static final long serialVersionUID = -7804660371718734982L;

	private EntityManager em; 
	
	public TitleService(EntityManager em){
		this.em = em;
	}
	
	// Méthode qui va chercher tout les titres dans la base de données.
	public List<Title> findAll(){
		TypedQuery<Title> query = em.createNamedQuery("Title.findAll", Title.class);
        return query.getResultList();		
	}
	
	// Méthode qui va chercher un titre dans la base de données en fonction du nom du titre qui lui est passé en paramétre.
	public Title findTitleByName(String name) {
		try {
	        return (Title) em.createNamedQuery("Title.findTitleByName").setParameter("name", name)
	            .getSingleResult();
	      } catch (NoResultException e) {
	        return null;
	      }
	}
	
	// Méthode qui va chercher un titre dans la base de données en fonction de l'id qui lui est passé en paramétre.
	public Title findTitleById(int titleId) {
		try {
	        return (Title) em.createNamedQuery("Title.findTitleById").setParameter("titleId", titleId)
	            .getSingleResult();
	      } catch (NoResultException e) {
	        return null;
	      }
		
	}
	
	// Méthode de création d'un nouveau titre.
	public void create(Title title) {
		em.persist(title);
	}
	
	// Méthode de mise à jour d'un titre existant.
	public void update(Title title) {
		em.merge(title);
	}

}