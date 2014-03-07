
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
       
     

}