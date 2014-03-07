<%-- 
    Document   : success
    Created on : Mar 7, 2014, 4:05:32 PM
    Author     : Wilson
--%>

<%@page import="ece356.Directory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logged In</title>
    </head>
    
    <%! Directory user;%>
    <% user = (Directory) request.getAttribute("userObject");%>
    
    <body>
        <%
            if (user != null) {
                out.print("<p>Welcome ");
                out.print(user.getName());
                out.println("!</p>");
            }
        %>
    </body>
</html>
