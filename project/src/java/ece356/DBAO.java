
package ece356;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Wojciech Golab
 */
public class DBAO {
    
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory( "projectPU" );;
    private static EntityManager em = emf.createEntityManager();
    
    public static Directory Login(int username, String password)
            throws ClassNotFoundException, SQLException {
         
        Query query = em.createQuery("SELECT e FROM Directory e WHERE e.username = :username AND e.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        Directory user = (Directory)query.getSingleResult();

        return user;
    }
    
    public static String createPatient(Directory directory, Patients patient)
            throws ClassNotFoundException, SQLException {
        em.getTransaction().begin();
        
        em.persist(directory);
        em.persist(patient);
        
        em.getTransaction().commit();
        
        return null;
    }
    
    public static String createAccount(String name, String role, String password)
            throws ClassNotFoundException, SQLException {
        Query query = em.createQuery("INSERT INTO Directory (name, password, role) VALUES (:name, :password, :role)");
        query.setParameter("name", name);
        query.setParameter("password", password);
        query.setParameter("role", role);
                
        if (query.executeUpdate() == 0) {
            return "No rows updated";
        }
        return null;
    }
    
    public static List<Doctors> getAllDoctors()
            throws ClassNotFoundException, SQLException {         
        TypedQuery<Doctors> query = em.createNamedQuery("Doctors.findAll", Doctors.class);
        return query.getResultList();
    }

}