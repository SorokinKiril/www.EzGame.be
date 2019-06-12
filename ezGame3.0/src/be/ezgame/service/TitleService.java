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
	
	// M�thode qui va chercher tout les titres dans la base de donn�es.
	public List<Title> findAll(){
		TypedQuery<Title> query = em.createNamedQuery("Title.findAll", Title.class);
        return query.getResultList();		
	}
	
	// M�thode qui va chercher un titre dans la base de donn�es en fonction du nom du titre qui lui est pass� en param�tre.
	public Title findTitleByName(String name) {
		try {
	        return (Title) em.createNamedQuery("Title.findTitleByName").setParameter("name", name)
	            .getSingleResult();
	      } catch (NoResultException e) {
	        return null;
	      }
	}
	
	// M�thode qui va chercher un titre dans la base de donn�es en fonction de l'id qui lui est pass� en param�tre.
	public Title findTitleById(int titleId) {
		try {
	        return (Title) em.createNamedQuery("Title.findTitleById").setParameter("titleId", titleId)
	            .getSingleResult();
	      } catch (NoResultException e) {
	        return null;
	      }
		
	}
	
	// M�thode de cr�ation d'un nouveau titre.
	public void create(Title title) {
		em.persist(title);
	}
	
	// M�thode de mise � jour d'un titre existant.
	public void update(Title title) {
		em.merge(title);
	}

}