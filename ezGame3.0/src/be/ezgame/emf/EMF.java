/**
 * 
 */
package be.ezgame.emf;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class EMF {
	
	

	private static final EntityManagerFactory emfInstance =
			
	        Persistence.createEntityManagerFactory("ezGame3.0");

    private EMF() {}

    public static EntityManagerFactory getEMF() {
        return emfInstance;
    }
    
    public static EntityManager getEM() {
        return emfInstance.createEntityManager();
    }
	
}
