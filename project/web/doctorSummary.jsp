<%-- 
    Document   : doctor_summary
    Created on : 23-Mar-2014, 9:45:14 PM
    Author     : rkn24
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%! ResultSet rs; %>
<% 
    rs = (ResultSet) session.getAttribute("doctorList");
%>

<t:template>
    <jsp:attribute name="pagetitle">
      Doctor's Summary
    </jsp:attribute>
    <jsp:attribute name="content">
      <c:choose>
        <c:when test="${rs.equals(null)}">
            Your search has returned zero results.
        </c:when>
        <c:otherwise>
            Your search returned results.
            <c:out value="${rs}"/>
        </c:otherwise>
      </c:choose>
    </jsp:attribute>
</t:template>
