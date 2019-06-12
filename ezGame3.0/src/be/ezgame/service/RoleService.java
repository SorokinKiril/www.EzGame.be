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
	// M�thode qui trouve tout les roles pr�sents en base de donn�.
	public List<Role> findAll(){
		TypedQuery<Role> query = em.createNamedQuery("Role.findAll", Role.class);
        return query.getResultList();		
	}

	// M�thode qui trouve le role pr�sent en base de donn� en fonction de l'id qui
	// lui est pass� en param�tre.
	public Role findRoleById(int roleId) {
		try {
	        return (Role) em.createNamedQuery("Role.findRoleById").setParameter("roleId", roleId)
	            .getSingleResult();
	      } catch (NoResultException e) {
	        return null;
	      }
	}
}