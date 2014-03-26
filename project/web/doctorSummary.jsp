<%-- 
    Document   : doctorSummary
    Created on : 25-Mar-2014, 11:39:00 PM
    Author     : rkn24
--%>

<%@page import="ece356.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%! String role; %>
<% role = ((User)session.getAttribute("userObject")).getRole(); %>  

<t:template>
    <jsp:attribute name="pagetitle">
      Doctor Summary
    </jsp:attribute>
    
    <jsp:attribute name="content">
      
      <c:if test="${queryDone.equals(true)}" >
          <h2 class="sub-header">Summary for Dr. <c:out value="${doctorName}"/></h2>
          <h2 class="sub-header">This doctor has <c:out value="${numPatients}"/> patients.</h2>  
          <br>
          <c:if test="${listDoctorSummaryEmpty.equals(false)}">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Patient ID</th>
                            <th># of Visitations</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listDoctorSummary}" var="data">
                            <tr>
                                <td> <c:out value="${data.getUsername()}"/></td>
                                <td> <c:out value="${data.getPatientCount()}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>  
          </c:if>
      </c:if>

      
      </div>
    </jsp:attribute>
</t:template>
