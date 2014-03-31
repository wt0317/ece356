<%@page import="ece356.Doctor"%>
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
    
    List<String> patientList = PatientDAO.getAllPatients();
    
    
    request.setAttribute("username", user.getUsername());
    request.setAttribute("name", user.getName());
    request.setAttribute("address", user.getAddress());
    request.setAttribute("phonenum", user.getPhoneNumber());
    request.setAttribute("patientList", patientList);
    request.setAttribute("role",role);
    
    
    if (role.equals("Patient")){
        Patient patient = (Patient) UserDAO.getUserInfo(user.getUsername(), user.getPassword(), role);
        request.setAttribute("hin", patient.getHealthCard());
        request.setAttribute("sin", patient.getSin());
        request.setAttribute("defaultDoctor", PatientDAO.getName(patient.getDefaultDoctor().getUsername()));
    }
    if (role.equals("Doctor")){
        Doctor doctor = (Doctor) UserDAO.getUserInfo(user.getUsername(), user.getPassword(), role);
        request.setAttribute("licenseId", doctor.getLicenseId());
        request.setAttribute("dateHired", doctor.getDateHired());
    }
%>
<t:template> 
    <jsp:attribute name="pagetitle">
      Account Settings
    </jsp:attribute>
    <jsp:attribute name="content">
    
        <div class="container-fluid">
            <div class="row-fluid">
          <div class="span9">
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
                <c:if test="${error.equals('noError')}">
                    <div class="alert alert-success">
                        <Strong>
                            Well done! 
                        </Strong>
                        Information Updated.
                    </div>
                </c:if>
               
               <form class="form-horizontal" role="form" action="UpdateInfoServlet" method="post">
                <p>User ID: 
                   <input name="username" type="input" class="form-control" value="${username}" required="" autofocus="true" readonly>
                </p>
                <p>Name: 
                    <input name="name" type="input" class="form-control disabled" value="${name}" required="" readonly>
                </p>
                <p>Address: 
                    <input name="address" type="input" class="form-control" id="disabledInput" value="${address}" required="" required>
                </p>
                <p>Phone Number: 
                    <input name="phone" id="phone" type="input" class="form-control disabled" value="${phonenum}" required="" required>
                </p>
                <c:if test="${role.equals('Patient')}">
                    <p class = "disabled">Health Card Number: 
                        <input name="hin" type="input" class="form-control" value="${hin}" required="" readonly>
                    </p>
                    <p class = "disabled">Social Insurance Number: 
                        <input name="sin" type="input" class="form-control" value="${sin}" required="" readonly>
                    </p>
                    <p>Default Doctor: 
                        <input name="defaultDoctor" type="input" class="form-control" value="${defaultDoctor}" required="" readonly>
                    </p>
                </c:if>
                <c:if test="${role.equals('Doctor')}">
                    <p class = "disabled">License Id: 
                        <input name="licenseId" type="input" class="form-control" value="${licenseId}" required="" readonly>
                    </p>
                    <p class = "disabled">Date Hired: 
                        <input name="dateHired" type="input" class="form-control" value="${dateHired}" required="" readonly>
                    </p>
                </c:if>
                <h2>Change Password</h2>
                <p>New Password: 
                    <input name="password" type="password" class="form-control" placeholder="Password">
                </p>
                <p>Confirm Password: 
                    <input name="passwordConfirm" type="password" class="form-control" placeholder="Password Confirm">
                </p>
                <p class="hidden">
                    <input name = "role" value = "${role}">
                </p>                
                <button class="btn btn-default" type="save">UPDATE</button>
              </form>
            </div><!--/span-->

        </div><!--/span-->
      </div><!--/row-->
      
      <hr>
        </div>
    </jsp:attribute>
    <jsp:attribute name="script">
        <script>
//        $(function(){
//           alert("okay"); 
//        });
        $(".form-horizontal").validate({
            rules : {
                phone : {
                    digits : true,
                    rangelength : [10,10]
                }
            },
            messages : {
                phone : {
                    rangelength : "Phone number must be 10 digits, no hyphen, no spaces"
                }
            }
        });
 
        </script>
    </jsp:attribute>
</t:template>