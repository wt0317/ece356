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
    
    Patients patient = DBAO.getUserInfo(user.getUsername(), user.getPassword());
    
    List<String> patientList = DBAO.getAllPatients();
    
    request.setAttribute("hin", patient.getHealthCard());
    request.setAttribute("username", user.getUsername());
    request.setAttribute("name", user.getName());
    request.setAttribute("address", user.getAddress());
    request.setAttribute("patientList", patientList);
    request.setAttribute("role",role);
    request.setAttribute("sin", patient.getSocialInsuranceNumber());
    request.setAttribute("defaultDoctor", DBAO.getName(patient.getDefaultDoctor().getUsername()));
    
    //Patients patient = new Patients(user.getUsername());
    //patient.queryUserInfo();
    //System.out.println("test : " + user.getAddress());
   //request.setAttribute("address", user.getAddress());
   
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
            <div class="span4">
              <h2>Personal Information</h2>
              <p>User ID: 
                    
                  <c:if test="${role == 'patient'}">
                       <input name="name" type="input" class="form-control" value="${username}" required="" autofocus="">
                 </c:if>
                 <c:if test="${role != 'patient'}">
                    <select class="form-control" name="patient" value="${username}">
                      <c:forEach items="${patientList}" var="item">
                         <option>${item}</option>
                      </c:forEach>
                    </select>
                 </c:if>
                    
              </p>
              <p>Name: 
                  <input name="name" type="input" class="form-control" value="${name}" required="" autofocus="">
              </p>
              <p>Address: 
                  <input name="address" type="input" class="form-control" value="${address}" required="" autofocus="">
              </p>
              <p>Phone Number: 
                  <input name="phonenum" type="input" class="form-control" value="${phonenum}" required="" autofocus="">
              </p>
              <p>Social Insurance Number: 
                  <input name="sin" type="input" class="form-control" value="${sin}" required="" autofocus="">
              </p>
              <p>Default Doctor: 
                  <input name="defaultDoctor" type="input" class="form-control" value="${defaultDoctor}" required="" autofocus="">
              </p>
              <p><a class="btn" href="#">View details &raquo;</a></p>
            </div><!--/span-->

        </div><!--/span-->
      </div><!--/row-->
      
      <hr>
        </div>
    </jsp:attribute>
</t:template>