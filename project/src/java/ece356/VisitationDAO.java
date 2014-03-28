/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author FireChemist
 */
public class VisitationDAO {

    public static void insertVisitation(
            int patient, int doctor, int procedure_id, int diagnosis_id,
            int prescription_id, Timestamp time_scheduled,
            Timestamp start_time, Timestamp end_time, int created_by,
            int surgery_id, String comments, String revision_comments
    )
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBAO.getConnection();

            String insertDoctor = "INSERT INTO Visitations "
                    + "(patient, doctor, procedure_id, diagnosis_id, prescription_id, "
                    + "time_scheduled, start_time, end_time, creation_time, created_by, "
                    + "surgery_id, comments, revision_comments) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            stmt = con.prepareStatement(insertDoctor);

            stmt.setInt(1, patient);
            stmt.setInt(2, doctor);
            stmt.setInt(3, procedure_id);
            stmt.setInt(4, diagnosis_id);
            stmt.setInt(5, prescription_id);
            stmt.setTimestamp(6, time_scheduled);   //Time scheduled
            stmt.setTimestamp(7, start_time);       //Start time
            stmt.setTimestamp(8, end_time);         //End time
            stmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));  //Creation time
            stmt.setInt(10, created_by);
            stmt.setInt(11, surgery_id);
            stmt.setString(12, comments);
            stmt.setString(13, revision_comments);

            stmt.executeUpdate();
        } 
        
        finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
