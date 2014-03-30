
package ece356;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public static final String url = "jdbc:mysql://sql3.freemysqlhosting.net:3306/";
    public static final String user = "sql332230";
    public static final String pwd = "mJ7!yB9!";

     public static void insertPatient(int username, String healthCard, String sin, String doctor, String health, String comments)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBAO.getConnection();
            
            String insertPatient = "INSERT INTO Patients"
                    + "(username, health_card, social_insurance_number, default_doctor, current_health, comment) VALUES"
                    + "(?,?,?,?,?,?)";                
            stmt = con.prepareStatement(insertPatient);                
            stmt.setInt(1, username);
            stmt.setString(2, healthCard);
            stmt.setString(3, sin);
            stmt.setString(4, doctor);
            stmt.setString(5, health);
            stmt.setString(6, comments);
            
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public static List<String> getAllPatients()
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<String> patientList = new ArrayList<String>();
        try {
                   
            //con = DriverManager.getConnection(url, user, pwd);  
            con = DBAO.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT username FROM Patients;");
            rs = pst.executeQuery();  
            
            while (rs.next()) {
                patientList.add(rs.getString("username"));   
            } 
            return patientList;

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        
    }
      public static String getName(int userid)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String name = null;
        try {
            
            //con = DriverManager.getConnection(url, user, pwd);  
            con = DBAO.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT d.name FROM Directory as d WHERE d.username = '" + userid + "'");
            rs = pst.executeQuery();  
            if (rs.next()){
                name = rs.getString("name");   
            }
            return name;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        
        
    }
      
    public static void updateUser(int username, String name, String address, String phoneNum, String hin, String sin)
    throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
                   
            //con = DriverManager.getConnection(url, user, pwd);  
            con = DBAO.getConnection();
            PreparedStatement pst = con.prepareStatement("UPDATE Patients SET health_card = '" + hin + "', social_insurance_number = '" + sin + "' WHERE username = '" + username + "'");
            pst.executeUpdate();  
            
            
            pst = con.prepareStatement("UPDATE Directory SET name='" + name + "', address= '" + address + "', phone_number='" + phoneNum+ "' WHERE username= '" + username + "'");
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