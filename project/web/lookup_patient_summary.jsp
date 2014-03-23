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
      
      <form role="form" action="Lookup_Patient_SummaryServlet" method="post">
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
                <input class="form-control" id="name" name="name" value="${name}" >
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
        </c:if>
        <button type="submit" class="btn btn-default">Submit</button>
     </form>

      
      </div>
    </jsp:attribute>
</t:template>