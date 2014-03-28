<%-- 
    Document   : lookupVisitationRecord
    Created on : Mar 25, 2014, 3:54:00 PM
    Author     : FireChemist
--%>


<%@page import="ece356.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%! String role;%>
<% role = ((User) session.getAttribute("userObject")).getRole();%>  

<t:template>
    <jsp:attribute name="pagetitle">
        <c:if test="${(userObject.getRole()).equals('Doctor')}">
            Doctor
        </c:if>
    </jsp:attribute>

    <jsp:attribute name="content">

        <c:if test="${success.equals('Added visitation record!')}">
            <div class="alert alert-success">
                <strong>
                    Success!
                </strong>
                ${success}
            </div>
        </c:if>

        <h1> Lookup Visitation Record </h1>
        <br>

        <c:if test="${(userObject.getRole()).equals('Doctor')}">
            <p class="lead"> Actions 
            <form role="form" action="AddVisitationRecordCreateFormServlet" method="post">
                <button type="submit" class="btn btn-primary">Add Visitation Record</button>
            </form>
        </p>
    </c:if>

</jsp:attribute>
</t:template>