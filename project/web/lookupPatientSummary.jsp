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
            
        <c:if test="${status.equals('Valid')}" >
            <h2 class="sub-header">Results for ${resultName}</h2>
            <c:choose>
                <c:when test="${startDate == null}">
                    <strong>Over the full time span.</strong>
                </c:when>
                <c:otherwise>
                    <strong>From:</strong> <c:out value="${startDate}"/> <strong>To:</strong> <c:out value="${endDate}"/>
                </c:otherwise>
            </c:choose>
            
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