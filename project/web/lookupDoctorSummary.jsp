<%-- 
    Document   : lookup doctor summary
    Created on : 23-Mar-2014, 9:45:14 PM
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
      Lookup Doctor Summary
    </jsp:attribute>
    
    <jsp:attribute name="content">
      
      <form role="form" action="DoctorLookupServlet" method="post">
        <div class="form-group">
            <label for="searchName">Name</label>
            <input class="form-control" id="name" name="name" placeholder="Search by name." >
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
        
      <c:if test="${queryDone.equals(true)}" >
          <h2 class="sub-header">Results</h2>    
          <br>
          <c:if test="${listDoctorEmpty.equals(false)}">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Doctor</th>
                            <th>Doctor ID</th>
                            <th># of Patients</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listDoctorSummary}" var="data">
                            <tr>
                                <td>
                                  <form id='doctorForm_<c:out value="${data.getUsername()}"/>' action="DoctorSummaryServlet" method="post">
                                    <input name="doctor" type="hidden" value='<c:out value="${data.getUsername()}"/>' />
                                    <input name="doctorName" type="hidden" value='<c:out value="${data.getName()}"/>' />
                                    <a href="javascript:{}" onclick="document.getElementById('doctorForm_<c:out value="${data.getUsername()}"/>').submit();"><c:out value="${data.getName()}" /></a>
                                  </form>
                                </td>
                                <td> <c:out value="${data.getUsername()}"/></td>
                                <td> <c:out value="${data.getPatientCount()}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>  
          </c:if>
           <c:if test="${listDoctorEmpty.equals(true)}">
             Your search has returned no results.
           </c:if>
      </c:if>

      
      </div>
    </jsp:attribute>
</t:template>