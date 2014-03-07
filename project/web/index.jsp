<%-- 
    Document   : index
    Created on : Mar 7, 2014, 2:37:29 PM
    Author     : Wilson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Welcome! Please log in</h1>
        <form action="LoginServlet" method="post">
            Username: <input name="username"/><br>
            Password: <input type="password" name="password"/><br>
            <input type="submit" value="Login"/>
        </form>
    </body>
</html>
