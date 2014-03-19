<%-- 
    Document   : create_account
    Created on : Mar 18, 2014, 11:40:08 AM
    Author     : Wilson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%! boolean success; %>
<% success = (request.getAttribute("success") != null) ? (Boolean) request.getAttribute("success") : false; %>  

<t:template>
    <jsp:attribute name="pagetitle">
      Project Zero
    </jsp:attribute>
    <jsp:attribute name="leftmenu">
    <ul class="nav nav-sidebar">
      <li class="active"><a href="#">Overview</a></li>
      <li><a href="#">Past Appointments</a></li>
    </ul>
    <ul class="nav nav-sidebar">
      <li><a href="">Update Address and Phone</a></li>
      <li><a href="">Change Password</a></li>
    </ul>
    </jsp:attribute>
    <jsp:attribute name="content">
          <c:if test="${success}">
              <div class="alert alert-success alert-dismissable">Account created!</div>
          </c:if>
        <form class="form-horizontal" role="form" action="CreateAccountServlet" method="post">
          <h1 class="form-heading">Create Account</h2>
          <div class="form-group">
            <label for="name" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
              <input class="form-control" id="name" name="name" placeholder="Name" required/>
            </div>
          </div>
          <div class="form-group">
            <label for="role" class="col-sm-2 control-label">Role</label>
            <div class="col-sm-10">
              <select class="form-control" id="role" name="role">
                <option>Patient</option>
                <option>Doctor</option>
                <option>Staff</option>
                <option>Finance</option>
                <option>Lawyer</option>                
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="password" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password" required/>
            </div>
          </div>
          <div class="form-group">
            <label for="confirm_password" class="col-sm-2 control-label">Confirm Password</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="confirm_password" name="confirm_password" placeholder="Confirm Password" data-validation-matches-match="email" />
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-default">Create Account</button>
            </div>
          </div>
        </form>
    </jsp:attribute>
    <jsp:attribute name="script">
        <script>
        $(function(){
           alert("okay"); 
        });
        $(".form-horizontal").validate({
            rules : {
                password : "required",
                confirm_password : {
                    equalTo : "#password"
                }
            }
        });
        </script>
    </jsp:attribute>
</t:template>