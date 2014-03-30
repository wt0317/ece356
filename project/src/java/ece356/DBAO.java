
package ece356;

import java.sql.*;

/**
 *
 * @author Wojciech Golab
 */
public class DBAO {

    public static final String url = "jdbc:mysql://sql3.freemysqlhosting.net:3306/";
    public static final String user = "sql332230";
    public static final String pwd = "mJ7!yB9!";

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
    
    public static User Login(int username, String password)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = getConnection();
            
            String loginQuery = "SELECT * FROM Directory WHERE username = ? AND password = ?";
            stmt = con.prepareStatement(loginQuery);
            stmt.setInt(1, username);
            stmt.setString(2, password);
            
            User user = null;
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("username"), rs.getString("name"), rs.getString("password"), rs.getString("role"), rs.getString("address"), rs.getString("phone_number"), rs.getBoolean("enabled"));
            }
            
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

    public static void updatePassword(int username, String password, String role)
    throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
                   
            //con = DriverManager.getConnection(url, user, pwd);  
            con = DBAO.getConnection();
            PreparedStatement pst = con.prepareStatement("UPDATE Directory SET password = '" + password + "' WHERE username = '" + username + "'");
            pst.executeUpdate();  

            
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