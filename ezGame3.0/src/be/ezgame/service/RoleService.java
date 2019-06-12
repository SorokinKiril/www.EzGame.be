package be.ezgame.service;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import be.ezgame.model.Role;

public class RoleService implements Serializable {
	
	private static final long serialVersionUID = -2893016652341409107L;
	
	private EntityManager em; 
	
	public RoleService(EntityManager em){
		this.em = em;
	}
	// Méthode qui trouve tout les roles présents en base de donné.
	public List<Role> findAll(){
		TypedQuery<Role> query = em.createNamedQuery("Role.findAll", Role.class);
        return query.getResultList();		
	}

	// Méthode qui trouve le role présent en base de donné en fonction de l'id qui
	// lui est passé en paramétre.
	public Role findRoleById(int roleId) {
		try {
	        return (Role) em.createNamedQuery("Role.findRoleById").setParameter("roleId", roleId)
	            .getSingleResult();
	      } catch (NoResultException e) {
	        return null;
	      }
	}
}