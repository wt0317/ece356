
package ece356;

import java.sql.*;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

/**
 *
 * @author Wojciech Golab
 */
public class DBAO {

    public static final String url = "jdbc:mysql://sql3.freemysqlhosting.net:3306/";
    public static final String user = "sql332230";
    public static final String pwd = "mJ7!yB9!";
    
    @PersistenceUnit(unitName = "projectPU")
    private static EntityManagerFactory emf;
    @PersistenceContext(unitName = "projectPU")
    private static EntityManager em;
    
    public static void testConnection()
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            con = getConnection();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public static Connection getConnection()
            throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, pwd);
        Statement stmt = null;
        try {
            con.createStatement();
            stmt = con.createStatement();
            stmt.execute("USE sql332230;");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return con;
    }
    
    public static Directory Login(int username, String password)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        
        try {
            emf = Persistence.createEntityManagerFactory( "projectPU" );
            EntityManager em = emf.createEntityManager();
            
            Query query = em.createQuery("SELECT e FROM Directory e WHERE e.username = :username AND e.password = :password");
            query.setParameter("username", username);
            query.setParameter("password", password);
            Directory user = (Directory)query.getSingleResult();
            
            return user;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

}