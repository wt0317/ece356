<%-- 
    Document   : lookup_patient_summary
    Created on : Mar 19, 2014, 10:54:13 PM
    Author     : FireChemist
--%>

<%@page import="ece356.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%! String role; %>
<% role = ((User)session.getAttribute("userObject")).getRole(); %>  

<t:template>
    <jsp:attribute name="pagetitle">
      Finance
    </jsp:attribute>
    
    <jsp:attribute name="content">
      <h1 class="page-header">Lookup Patient Summary</h1>
      
      <form role="form" action="LookupPatientSummaryServlet" method="post">
        <div class="form-group">
            <c:if test="${error.equals('Invalid')}">
                <div class="alert alert-danger">
                    <Strong>
                        Error: 
                    </Strong>
                        This person does not exist!
                </div>
            </c:if>
            <c:if test="${error.equals('Duplicate')}">
                <div class="alert alert-warning">
                <Strong>
                    Warning: 
                </Strong>
                    Duplicate match encountered, please select ID.
                </div>
            </c:if>
            
            <label for="searchName">Name</label>
            <c:choose>
            <c:when test="${error.equals('Duplicate')}" >
                <p id="name" name="name" value="${name}"> ${name} </p>
            </c:when>
            <c:otherwise>
                <input class="form-control" id="name" name="name" placeholder="Bruce Wayne" >
            </c:otherwise>
            </c:choose>
        </div>
          
        <c:if test="${error.equals('Duplicate')}" >
              <div class="form-group">
                 <label for="searchID">ID</label>           
                    <select name="username" class="form-control">
                    <c:forEach items="${listPatientID}" var= "username">
                        <option> <c:out value="${username}" />
                    </c:forEach>
                    </select>        
               </div>
              <button type="button" class="btn btn-default" onclick="history.back()">Back</button>
        </c:if>
        <button type="submit" class="btn btn-default">Submit</button>
        
        <c:if test="${status.equals('Valid')}" >
            <h2 class="sub-header">Results for ${resultName}</h2>    
            <p> <strong>Number of Visits: </strong> <c:out value="${countVisits}" /></p>
            <br>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Diagnosis</th>
                            <th>Diagnosis Name</th>
                            <th>Drugs Prescribed</th>
                            <th>Drug Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${patientResults}" var="data">
                            <tr>
                                <td> <c:out value="${data.diagnosis_id}" /></td>
                                <td> <c:out value="${data.diagnosis_name}"/></td>
                                <td> <c:out value="${data.prescription_id}" /></td>
                                <td> <c:out value="${data.prescription_name}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>           
        </c:if>
     </form>

      
      </div>
    </jsp:attribute>
</t:template>