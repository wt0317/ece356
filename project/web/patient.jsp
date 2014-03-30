<%@page import="ece356.UserDAO"%>
<%@page import="ece356.User"%>
<%@page import="ece356.PatientDAO"%>
<%@page import="java.util.List"%>
<%@page import="ece356.Patient"%>
<%@page import="ece356.DBAO"%>
<%@page import="ece356.Directory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
    User user = (User) session.getAttribute("userObject");

    String role = user.getRole();
    
    Patient patient = UserDAO.getUserInfo(user.getUsername(), user.getPassword());
    
    List<String> patientList = PatientDAO.getAllPatients();
    
    request.setAttribute("hin", patient.getHealthCard());
    request.setAttribute("username", user.getUsername());
    request.setAttribute("name", user.getName());
    request.setAttribute("address", user.getAddress());
    request.setAttribute("phonenum", user.getPhoneNumber());
    request.setAttribute("patientList", patientList);
    request.setAttribute("role",role);
    request.setAttribute("sin", patient.getSin());
    request.setAttribute("defaultDoctor", PatientDAO.getName(patient.getDefaultDoctor().getUsername()));
    
   
%>
<t:template> 
    <jsp:attribute name="pagetitle">
      Patient Homepage
    </jsp:attribute>
    <jsp:attribute name="content">
    
        <div class="container-fluid">
            <div class="row-fluid">
          <div class="span9">
          <div class="well">
            <h1>Notifications</h1>
            <p>You have no notifications at this time</p>
          </div>
          <div class="row-fluid">
            <div class="panel-heading">
               <h2>Personal Information</h2>
               
                <c:if test="${error.equals('notEqual')}">
                    <div class="alert alert-danger">
                        <Strong>
                            Error: 
                        </Strong>
                        Passwords did not match.
                    </div>
                </c:if>
                <c:if test="${error.equals('password')}">
                    <div class="alert alert-danger">
                        <Strong>
                            Error: 
                        </Strong>
                        You must enter a new password.
                    </div>
                </c:if>
               
               <form role="form" action="PatientServlet" method="post">
                <p>User ID: 

                    <c:if test="${role == 'Patient'}">
                         <input name="username" type="input" class="form-control" value="${username}" required="" autofocus="" readonly>
                   </c:if>
                   <c:if test="${role != 'Patient'}">
                      <select class="form-control" name="patient" value="${username}">
                        <c:forEach items="${patientList}" var="item">
                           <option>${item}</option>
                        </c:forEach>
                      </select>
                   </c:if>

                </p>
                <p class = "disabled">Name: 
                    <input name="name" type="input" class="form-control disabled" value="${name}" required="" autofocus="">
                </p>
                <p>Address: 
                    <input name="address" type="input" class="form-control" id="disabledInput" value="${address}" required="" autofocus="">
                </p>
                <p>Phone Number: 
                    <input name="phonenum" type="input" class="form-control disabled" value="${phonenum}" required="" autofocus="">
                </p>
                <p class = "disabled">Health Card Number: 
                    <input name="hin" type="input" class="form-control" value="${hin}" required="" autofocus="">
                </p>
                <p class = "disabled">Social Insurance Number: 
                    <input name="sin" type="input" class="form-control" value="${sin}" required="" autofocus="">
                </p>
                <p>Default Doctor: 
                    <input name="defaultDoctor" type="input" class="form-control" value="${defaultDoctor}" required="" autofocus="" readonly>
                </p>
                <h2>Change Password</h2>
                <p>New Password: 
                    <input name="password" type="password" class="form-control" placeholder="Password" autofocus="" >
                </p>
                <p>Confirm Password: 
                    <input name="passwordConfirm" type="password" class="form-control" placeholder="Password" autofocus="">
                </p>
                <p class="hidden">
                    <input name = "role" value = "${role}">
                </p>                
                <button class="btn btn-lg btn-primary btn-block" type="save">UPDATE</button>
              </form>
            </div><!--/span-->

        </div><!--/span-->
      </div><!--/row-->
      
      <hr>
        </div>
    </jsp:attribute>
</t:template>