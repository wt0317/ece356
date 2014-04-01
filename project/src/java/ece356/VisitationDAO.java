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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.text.WordUtils;

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

            //Update patient's number of visits in Patients table
            PatientDAO.updateNumberVisits(patient);

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public static VisitationDAOResult getPatientVisitationRecordsForPatient(int username) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        List<Visitation> visitation = new ArrayList<Visitation>();
        List<String> columnNames = new ArrayList<String>();
        int count = 0;

        try {
            con = DBAO.getConnection();

            String query = "SELECT dir.name as doctorName, time_scheduled, start_time, end_time, creation_time, dir2.name as createdName, procedure_name, diagnosis_name, prescription_name, surgery_name "
                    + "FROM Visitations v, Procedures p, Diagnoses d, Prescriptions pr, Surgeries s, Directory dir, Directory dir2 "
                    + "WHERE (v.doctor = dir.username "
                    + "AND v.procedure_id = p.procedure_id "
                    + "AND v.diagnosis_id = d.diagnosis_id "
                    + "AND v.prescription_id = pr.prescription_id "
                    + "AND v.surgery_id = s.surgery_id AND v.created_by = dir2.username AND "
                    + "v.patient = ?)";

            stmt = con.prepareStatement(query);
            stmt.setInt(1, username);
            rs = stmt.executeQuery();
            rsmd = rs.getMetaData(); //getColumnName(int column)

            //Assign column names to output table        
            for (int index = 1; index <= rsmd.getColumnCount(); index++) {

                //Custom column name name
                if (index == 1) {
                    columnNames.add("Doctor");
                } //Custom column name
                else if (index == 6) {
                    columnNames.add("Created By");
                } //Default column name
                else {
                    String columnName = rsmd.getColumnName(index);
                    columnName = columnName.replace("_", " ");
                    columnName = WordUtils.capitalize(columnName);
                    columnNames.add(columnName);
                }
            }

            while (rs.next()) {
                Visitation record = new Visitation();
                record.setDoctorName(rs.getString("doctorName"));
                record.setProcedureName(rs.getString("procedure_name"));
                record.setDiagnosisName(rs.getString("diagnosis_name"));
                record.setPrescriptionName(rs.getString("prescription_name"));
                record.setTimeScheduled(rs.getTimestamp("time_scheduled"));
                record.setStartTime(rs.getTimestamp("start_time"));
                record.setEndTime(rs.getTimestamp("end_time"));
                record.setCreationTime(rs.getTimestamp("creation_time"));
                record.setCreatedName(rs.getString("createdName"));
                record.setSurgeryName(rs.getString("surgery_name"));
                visitation.add(record);
                count++;
            }

            VisitationDAOResult visitationDAOResult = new VisitationDAOResult(visitation, columnNames, count);
            return visitationDAOResult;

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public static VisitationDAOResult getPatientVisitationRecordsForDoctor(int username) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        List<Visitation> visitation = new ArrayList<Visitation>();
        List<String> columnNames = new ArrayList<String>();
        int count = 0;

        try {
            con = DBAO.getConnection();

            String query = "SELECT dir.name as patientName, time_scheduled, start_time, end_time, creation_time, dir2.name as createdName, procedure_name, diagnosis_name, prescription_name, surgery_name, comments, revision_comments "
                    + "FROM Visitations as v, Procedures p, Diagnoses d, Prescriptions pr, Surgeries s, Directory dir, Directory dir2, Permissions perm "
                    + "WHERE (v.procedure_id = p.procedure_id "
                    + "AND v.diagnosis_id = d.diagnosis_id "
                    + "AND v.prescription_id = pr.prescription_id "
                    + "AND v.surgery_id = s.surgery_id "
                    + "AND v.created_by = dir2.username "
                    + "AND v.patient = dir.username "
                    + "AND perm.employee = v.doctor "
                    + "AND perm.patient = v.patient "
                    + "AND perm.accessibility = 1 "
                    + "AND perm.enabled = 1 "
                    + "AND v.doctor = ?) "
                    + "ORDER BY v.time_scheduled DESC";

            stmt = con.prepareStatement(query);
            stmt.setInt(1, username);
            rs = stmt.executeQuery();
            rsmd = rs.getMetaData();

            //Assign column names to output table        
            for (int index = 1; index <= rsmd.getColumnCount(); index++) {

                //Custom column name name
                if (index == 1) {
                    columnNames.add("Patient");
                } //Custom column name
                else if (index == 6) {
                    columnNames.add("Created By");
                } //Default column name
                else {
                    String columnName = rsmd.getColumnName(index);
                    columnName = columnName.replace("_", " ");
                    columnName = WordUtils.capitalize(columnName);
                    columnNames.add(columnName);
                }
            }

            while (rs.next()) {
                Visitation record = new Visitation();
                record.setPatientName(rs.getString("patientName"));
                record.setProcedureName(rs.getString("procedure_name"));
                record.setDiagnosisName(rs.getString("diagnosis_name"));
                record.setPrescriptionName(rs.getString("prescription_name"));
                record.setTimeScheduled(rs.getTimestamp("time_scheduled"));
                record.setStartTime(rs.getTimestamp("start_time"));
                record.setEndTime(rs.getTimestamp("end_time"));
                record.setCreationTime(rs.getTimestamp("creation_time"));
                record.setCreatedName(rs.getString("createdName"));
                record.setSurgeryName(rs.getString("surgery_name"));
                record.setComments(rs.getString("comments"));
                record.setRevisionComments(rs.getString("revision_comments"));
                visitation.add(record);
                count++;
            }

            VisitationDAOResult visitationDAOResult = new VisitationDAOResult(visitation, columnNames, count);
            return visitationDAOResult;

        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

    }

    public static VisitationDAOResult getPatientVisitationRecordsForDoctorSEARCH(
            int username,
            String patientName,
            int patientUsername,
            int diagnosisID,
            int procedureID,
            int prescriptionID,
            int surgeryID,
            String comments,
            String revisionComments,
            Timestamp timeScheduled1,
            Timestamp timeScheduled2,
            Timestamp timeStart1,
            Timestamp timeStart2,
            Timestamp timeEnd1,
            Timestamp timeEnd2,
            Timestamp timeCreation1,
            Timestamp timeCreation2,
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws ClassNotFoundException, SQLException, ServletException, IOException {

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        List<Visitation> visitation = new ArrayList<Visitation>();
        List<String> columnNames = new ArrayList<String>();
        int count = 0;
        

        try {
            con = DBAO.getConnection();
            String query = "SELECT dir.name as patientName, time_scheduled, start_time, end_time, creation_time, dir2.name as createdName, procedure_name, diagnosis_name, prescription_name, surgery_name, comments, revision_comments "
                    + "FROM Visitations as v, Procedures p, Diagnoses d, Prescriptions pr, Surgeries s, Directory dir, Directory dir2, Permissions perm "
                    + "WHERE (v.procedure_id = p.procedure_id "
                    + "AND v.diagnosis_id = d.diagnosis_id "
                    + "AND v.prescription_id = pr.prescription_id "
                    + "AND v.surgery_id = s.surgery_id "
                    + "AND v.created_by = dir2.username "
                    + "AND v.patient = dir.username "
                    + "AND perm.employee = v.doctor "
                    + "AND perm.patient = v.patient "
                    + "AND perm.accessibility = 1 "
                    + "AND perm.enabled = 1 "
                    + "AND v.doctor = ? ";
            //New additions
            // "AND v.patient = ? ";  //Caution this should be an int
            //"AND v.diagnosis_id = ? "; //Caution this should be an int
            //+ "AND v.procedure_id = ? " //Caution this should be an int
            //+ "AND v.prescription_id = ? " //Caution this should be an int
            //+ "AND v.surgery_id = ? " //Caution this should be an int
            //+ "AND v.comments LIKE ? "
            //+ "AND v.revision_comments LIKE ? )";

            if (!patientName.equals("")) {
                query = query.concat(" AND dir.name LIKE '%" + patientName + "%'");
            }

            if (patientUsername != -1) {
                query = query.concat(" AND v.patient = " + patientUsername);
            }

            if (diagnosisID != -1) {
                query = query.concat(" AND v.diagnosis_id = " + diagnosisID);
            }

            if (procedureID != -1) {
                query = query.concat(" AND v.procedure_id = " + procedureID);
            }

            if (prescriptionID != -1) {
                query = query.concat(" AND v.prescription_id = " + prescriptionID);
            }

            if (surgeryID != -1) {
                query = query.concat(" AND v.surgery_id = " + surgeryID);
            }

            if (!comments.equals("")) {
                query = query.concat(" AND v.comments LIKE " + "'%" + comments + "%'");
            }

            if (!revisionComments.equals("")) {
                query = query.concat(" AND v.revision_comments LIKE " + "'%" + revisionComments + "%'");
            }

            if (timeScheduled1 != null && timeScheduled2 != null) {
                query = query.concat(" AND '" + timeScheduled1 + "' <= v.time_scheduled" + " AND  v.time_scheduled <= '" + timeScheduled2 + "'");
            }

            if (timeStart1 != null && timeStart2 != null) {
                query = query.concat(" AND '" + timeStart1 + "' <= v.start_time" + " AND  v.start_time <= '" + timeStart2 + "'");
            }

            if (timeEnd1 != null && timeEnd2 != null) {
                query = query.concat(" AND '" + timeEnd1 + "' <= v.end_time" + " AND  v.end_time <= '" + timeEnd2 + "'");
            }

            if (timeCreation1 != null && timeCreation2 != null) {
                query = query.concat(" AND '" + timeCreation1 + "' <= v.creation_time" + " AND  v.creation_time <= '" + timeCreation2 + "'");
            }

            query = query.concat(" ) ORDER BY time_scheduled DESC");
            stmt = con.prepareStatement(query);
            stmt.setInt(1, username);
            //stmt.setInt(2, diagnosisID);
            //Get and set diagnosis_id

            //Get procedure_id
            //Get prescription_id
            //Get surgery_id
            rs = stmt.executeQuery();
            rsmd = rs.getMetaData();

            //Assign column names to output table        
            for (int index = 1; index <= rsmd.getColumnCount(); index++) {

                //Custom column name name
                if (index == 1) {
                    columnNames.add("Patient");
                } //Custom column name
                else if (index == 6) {
                    columnNames.add("Created By");
                } //Default column name
                else {
                    String columnName = rsmd.getColumnName(index);
                    columnName = columnName.replace("_", " ");
                    columnName = WordUtils.capitalize(columnName);
                    columnNames.add(columnName);
                }
            }

            while (rs.next()) {
                Visitation record = new Visitation();
                record.setPatientName(rs.getString("patientName"));
                record.setProcedureName(rs.getString("procedure_name"));
                record.setDiagnosisName(rs.getString("diagnosis_name"));
                record.setPrescriptionName(rs.getString("prescription_name"));
                record.setTimeScheduled(rs.getTimestamp("time_scheduled"));
                record.setStartTime(rs.getTimestamp("start_time"));
                record.setEndTime(rs.getTimestamp("end_time"));
                record.setCreationTime(rs.getTimestamp("creation_time"));
                record.setCreatedName(rs.getString("createdName"));
                record.setSurgeryName(rs.getString("surgery_name"));
                record.setComments(rs.getString("comments"));
                record.setRevisionComments(rs.getString("revision_comments"));
                visitation.add(record);
                count++;
                System.out.println("Result!!!");
                
            }

            VisitationDAOResult visitationDAOResult = new VisitationDAOResult(visitation, columnNames, count);
            return visitationDAOResult;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public static VisitationDAOResult getPatientVisitationRecordsForStaff(int username) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        List<Visitation> visitation = new ArrayList<Visitation>();
        List<String> columnNames = new ArrayList<String>();
        int count = 0;

        try {
            con = DBAO.getConnection();

            //Check staff-patient relationship in Permissions table (1)
            //Check staff-doctor relationship in Staff Assignment table (2)
            //For those doctors in (2), check doctor-patient relationship in Permissions table
            String query = "SELECT dir.name as patientName, time_scheduled, start_time, end_time, creation_time, dir2.name as createdName, procedure_name, diagnosis_name, prescription_name, surgery_name, comments, revision_comments "
                    + "FROM Visitations as v, Procedures p, Diagnoses d, Prescriptions pr, Surgeries s, Directory dir, Directory dir2, Permissions perm "
                    + "WHERE (v.procedure_id = p.procedure_id "
                    + "AND v.diagnosis_id = d.diagnosis_id "
                    + "AND v.prescription_id = pr.prescription_id "
                    + "AND v.surgery_id = s.surgery_id "
                    + "AND v.created_by = dir2.username "
                    + "AND v.patient = dir.username "
                    + "AND perm.patient = v.patient "
                    + "AND perm.accessibility = 1 "
                    + "AND perm.enabled = 1 "
                    + "AND perm.employee = ? " //Select visitation records where staff has direct access to patient in Permissions
                    + ") "
                    + "UNION " //Select visitation records where staff has access to via doctor
                    + "SELECT dir.name as patientName, time_scheduled, start_time, end_time, creation_time, dir2.name as createdName, procedure_name, diagnosis_name, prescription_name, surgery_name, comments, revision_comments "
                    + "FROM Visitations as v, Procedures p, Diagnoses d, Prescriptions pr, Surgeries s, Directory dir, Directory dir2, Permissions perm "
                    + "WHERE (v.procedure_id = p.procedure_id "
                    + "AND v.diagnosis_id = d.diagnosis_id "
                    + "AND v.prescription_id = pr.prescription_id "
                    + "AND v.surgery_id = s.surgery_id "
                    + "AND v.created_by = dir2.username "
                    + "AND v.patient = dir.username "
                    + "AND perm.patient = v.patient "
                    + "AND perm.accessibility = 1 "
                    + "AND perm.enabled = 1 "
                    + "AND perm.employee = v.doctor "
                    + "AND v.doctor IN (" //list of doctors in staff->doctor relationship in Staff Assignment
                    + "SELECT " //Select list of doctors that are related to this staff member 
                    + "doctor from Staff_Assignments WHERE staff = ? AND enabled = 1)) ORDER BY time_scheduled DESC";

            stmt = con.prepareStatement(query);
            stmt.setInt(1, username);   //Set perm.employee to staff username
            stmt.setInt(2, username);   //Set staff to staff username

            rs = stmt.executeQuery();
            rsmd = rs.getMetaData();

            //Assign column names to output table        
            for (int index = 1; index <= rsmd.getColumnCount(); index++) {

                //Custom column name name
                if (index == 1) {
                    columnNames.add("Patient");
                } //Custom column name
                else if (index == 6) {
                    columnNames.add("Created By");
                } //Default column name
                else {
                    String columnName = rsmd.getColumnName(index);
                    columnName = columnName.replace("_", " ");
                    columnName = WordUtils.capitalize(columnName);
                    columnNames.add(columnName);
                }
            }

            while (rs.next()) {
                Visitation record = new Visitation();
                record.setPatientName(rs.getString("patientName"));
                record.setProcedureName(rs.getString("procedure_name"));
                record.setDiagnosisName(rs.getString("diagnosis_name"));
                record.setPrescriptionName(rs.getString("prescription_name"));
                record.setTimeScheduled(rs.getTimestamp("time_scheduled"));
                record.setStartTime(rs.getTimestamp("start_time"));
                record.setEndTime(rs.getTimestamp("end_time"));
                record.setCreationTime(rs.getTimestamp("creation_time"));
                record.setCreatedName(rs.getString("createdName"));
                record.setSurgeryName(rs.getString("surgery_name"));
                record.setComments(rs.getString("comments"));
                record.setRevisionComments(rs.getString("revision_comments"));
                visitation.add(record);
                count++;
            }
            VisitationDAOResult visitationDAOResult = new VisitationDAOResult(visitation, columnNames, count);
            return visitationDAOResult;

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
