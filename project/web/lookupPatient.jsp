<%-- 
    Document   : lookupPatients
    Created on : Mar 26, 2014, 4:00:56 PM
    Author     : Wilson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
    <jsp:attribute name="pagetitle">
      Lookup Patient
    </jsp:attribute>
    
    <jsp:attribute name="content">  
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Health Card</th>
                        <th>Social Insurance Number</th>
                        <th>Number of Visits</th>
                        <th>Default Doctor</th>
                        <th>Current Health</th>
                        <th>Comment</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listPatients}" var="p">
                        <tr>
                            <td> <c:out value="${p.getUsername()}" /></td>
                            <td> <c:out value="${p.getHealthCard()}"/></td>
                            <td> <c:out value="${p.getSin()}" /></td>
                            <td> <c:out value="${p.getNumOfVisits()}" /></td>
                            <td> <c:out value="${p.getDefaultDoctor().getUsername()}" /></td>
                            <td> <c:out value="${p.getCurrentHealth()}" /></td>
                            <td> <c:out value="${p.getComment()}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:attribute>
</t:template>
