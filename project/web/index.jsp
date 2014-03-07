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
        <title>Signin</title>
        
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/signin.css">
    </head>
    <body>
        <div class="container">
            <form class="form-signin" role="form" action="LoginServlet" method="post">
                <h2 class="form-signin-heading">Please sign in</h2>
                <input name="username" type="input" class="form-control" placeholder="Username" required="" autofocus="">
                <input name="password" type="password" class="form-control" placeholder="Password" required="">
                <label class="checkbox">
                    <input type="checkbox" value="remember-me"> Remember me
                </label>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>
        </div>
    </body>
</html>
