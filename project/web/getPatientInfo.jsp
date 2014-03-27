<%-- 
    Document   : lookup_patient_summary
    Created on : Mar 19, 2014, 10:54:13 PM
    Author     : FireChemist
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
    <jsp:attribute name="pagetitle">
        <c:out value="${p.getName()}" />
    </jsp:attribute>
    <jsp:attribute name="style">
        <style>
            .comments {
                padding-left:5em;
                text-align:justify;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container-fluid">
            <div class="row">
                <h2>Patient Information</h2>
                <div class="col-md-6">
                    <p>Name: <c:out value="${p.getName()}"/></p>
                    <p>Health Card: <c:out value="${p.getHealthCard()}"/></p>
                    <p>Social Insurance Number: <c:out value="${p.getSin()}"/></p>
                </div>
                <div class="col-md-6">
                    <p>Default Doctor: <c:out value="${p.getDefaultDoctor().getName()}"/></p>
                    <p>Number of Visits: <c:out value="${p.getNumOfVisits()}"/></p>
                    <p>Current Health: <c:out value="${p.getCurrentHealth()}"/></p>
                </div>
                <div class="col-md-12">
                    <p>Comment: <p class='comments'><c:out value="${p.getComment()}"/></p></p>
                </div>
            </div>
            <hr>
            <div class="row">
                <h2>Permissions</h2>
            </div>
            <hr>
            <div class="row">
                <h2>Visitation Records</h2>
            </div>
        </div>
    </jsp:attribute>
</t:template>