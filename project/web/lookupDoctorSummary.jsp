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
      
      <form role="form" action="DoctorServlet" method="post">
        <div class="form-group">
            <label for="searchName">Name</label>
            <input class="form-control" id="name" name="name" placeholder="Search by name." >
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
        
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
                                  <td> <c:out value="${data.name}" /></td>
                                  <td> <c:out value="${data.username}"/></td>
                                  <td> <c:out value="${data.patientcount}"/></td>
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
     </form>

      
      </div>
    </jsp:attribute>
</t:template>