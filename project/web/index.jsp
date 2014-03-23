<%-- 
    Document   : index
    Created on : Mar 7, 2014, 2:37:29 PM
    Author     : Wilson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Signin</title>
        
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/signin.css">
    </head>
    <body>
        <div class="container">
            <c:if test="${loginFailed}">
                <div class="alert alert-danger">Username or password incorrect</div>
            </c:if>
            <form class="form-signin" role="form" action="LoginServlet" method="post">
                <div class="signin-box">
                    <input name="username" type="input" class="form-control" placeholder="Username" required="" autofocus="">
                    <input name="password" type="password" class="form-control" placeholder="Password" required="">
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                </div>
            </form>
        </div>
    </body>
</html>
