
package ece356;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Wojciech Golab
 */
public class DBAO {

    public static final String url = "jdbc:mysql://sql3.freemysqlhosting.net:3306/";
    public static final String user = "sql332230";
    public static final String pwd = "mJ7!yB9!";

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
            con = getConnection();
            stmt = con.createStatement();
            String query = String.format("SELECT * FROM Directory WHERE username = '%d' AND password = '%s'", username, password);
            ResultSet resultSet = stmt.executeQuery(query);
            resultSet.next();
            Directory user = new Directory(resultSet.getInt("username"));
            user.setName(resultSet.getString("name"));
            user.setRole(resultSet.getString("role"));
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
    
    public static String createAccount(String name, String role, String password)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = getConnection();
            
            String insertTableSQL = "INSERT INTO Directory"
				+ "(name, password, role) VALUES"
				+ "(?,?,?)";
            
            stmt = con.prepareStatement(insertTableSQL);
 
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.setString(3, role);

            if (stmt.executeUpdate() == 0) {
                return "No rows updated";
            }
            return null;
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