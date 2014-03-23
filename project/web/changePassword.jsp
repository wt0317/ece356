<%@page import="java.util.List"%>
<%@page import="ece356.Patients"%>
<%@page import="ece356.DBAO"%>
<%@page import="ece356.Directory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
    Directory user = (Directory) session.getAttribute("userObject");

    String role = user.getRole();
    


   
%>
<t:template> 
    <jsp:attribute name="pagetitle">
      Patient Homepage
    </jsp:attribute>
    <jsp:attribute name="content">
    
        <div class="container-fluid">
            <div class="row-fluid">
          <div class="span9">
          <div class="row-fluid">
            <div class="panel-heading">
               <h2>Change Password</h2>
               <form role="form" action="ChangePassword" method="post">
                <p>New Password: 
                    <input name="username" type="password" class="form-control" placeholder="Password" required="" autofocus="" >
                </p>
                <p>Confirm Password: 
                    <input name="name" type="password" class="form-control" placeholder="Password" required="" autofocus="">
                </p>
                <button class="btn btn-lg btn-primary btn-block" type="save">Save</button>
              </form>
            </div><!--/span-->

        </div><!--/span-->
      </div><!--/row-->
      
      <hr>
        </div>
    </jsp:attribute>
</t:template>