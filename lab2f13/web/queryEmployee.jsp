<%-- 
    Document   : queryEmployee
    Created on : Feb 4, 2014, 2:11:57 PM
    Author     : Wilson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Query Employees</title>
    </head>
    <body>
        <h1>Query Employees</h1>
        Enter selection criteria:
        <form action="QueryServlet" method="get">
            <input type="hidden" name="qnum" value="2">
            Employee ID: <input name="empID"/><br>
            Employee Name: <input name="empName"/><br>
            Job: <input name="job"/><br>
            Dept ID: <input name="deptID"/><br>
            Salary: <input name="salary"/><br>
            <input type="submit" value="Submit Query"/>
        </form>
    </body>
</html>
