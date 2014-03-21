
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
    
    private static final String url = "jdbc:mysql://sql3.freemysqlhosting.net:3306/";
    private static final String user = "sql332230";
    private static final String pwd = "mJ7!yB9!";
    
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

}