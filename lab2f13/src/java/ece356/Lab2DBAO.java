
package ece356;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Wojciech Golab
 */
public class Lab2DBAO {

    public static final String url = "jdbc:mysql://eceweb.uwaterloo.ca:3306/";
    public static final String user = "user_w4tran";
    public static final String pwd = "user_w4tran";

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
            stmt.execute("USE ece356db_w4tran;");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return con;
    }

    

    public static ArrayList<Employee> query1()
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Employee> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(
                    
                    
                    "SELECT * FROM Employee WHERE job = 'engineer' AND salary >= 10000;");
            ret = new ArrayList<Employee>();
            while (resultSet.next()) {
                Employee e = new Employee(
                        resultSet.getInt("empID"),
                        resultSet.getString("empName"),
                        resultSet.getString("job"),
                        resultSet.getInt("deptID"),
                        resultSet.getInt("salary"));
                ret.add(e);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public static ArrayList<Employee> query2(int empID, String empName, String job, int deptID, long salary)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Employee> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            String query = String.format("SELECT * FROM Employee WHERE empID = %d AND empName = '%s' AND job = '%s' AND deptID = %d AND salary = %d;",
                    empID, empName, job, deptID, salary);
            ResultSet resultSet = stmt.executeQuery(query);
            ret = new ArrayList<Employee>();
            while (resultSet.next()) {
                Employee e = new Employee(
                        resultSet.getInt("empID"),
                        resultSet.getString("empName"),
                        resultSet.getString("job"),
                        resultSet.getInt("deptID"),
                        resultSet.getInt("salary"));
                ret.add(e);
            }
            return ret;
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