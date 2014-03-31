
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
        
    public static List<Appointment> getFutureAppointments(int username) 
        throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        List<Appointment> appointments = new ArrayList<Appointment>();

        try {
            con = DBAO.getConnection();
            String selectAppointments = "SELECT * FROM Appointments WHERE patient = '"+ username +"' AND start_time > NOW()";
            stmt = con.prepareStatement(selectAppointments);
            ResultSet resultAppointments = stmt.executeQuery();

            while(resultAppointments.next()) {                
                appointments.add(new Appointment(
                    resultAppointments.getString("title"),
                    resultAppointments.getString("start_time"),
                    resultAppointments.getString("end_time"),
                    resultAppointments.getInt("doctor"),
                    resultAppointments.getInt("patient"),
                    resultAppointments.getInt("created_by")
                ));
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return appointments;
    }
}