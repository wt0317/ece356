<%-- 
    Document   : addVisitationRecord
    Created on : Mar 25, 2014, 4:24:36 PM
    Author     : FireChemist
--%>

<%@page import="ece356.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<%! String role;%>
<% role = ((User) session.getAttribute("userObject")).getRole();%>  

<t:template>
    <jsp:attribute name="pagetitle">
        <c:if test="${(userObject.getRole()).equals('Doctor')}">
            Doctor
        </c:if>
    </jsp:attribute>

    <jsp:attribute name="content">


        <h1> Add Visitation Record </h1>
        <form class="form-horizontal" role="form" action="AddVisitationRecordInsertServlet" method="post">
            <div class="form-group">
                <label for="patientName" class="col-sm-2 control-label">Patient Name</label>
                <div class="col-sm-10">
                    <input type="patientName" class="form-control" id="inputPassword3" placeholder="Peter Parker">
                </div>
            </div>
            <div class="form-group">
                <label for="timeScheduled" class="col-sm-2 control-label">Time Scheduled</label>
                <div class="col-sm-10">
                    <input type="datetime-local" class="form-control" name="timeScheduled">
                </div>
            </div>
            <div class="form-group">
                <label for="startTime" class="col-sm-2 control-label">Start Time</label>
                <div class="col-sm-10">
                    <input type="datetime-local" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="endTime" class="col-sm-2 control-label">End Time</label>
                <div class="col-sm-10">
                    <input type="datetime-local" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <label for="procedure" class="col-sm-2 control-label">Procedure</label>
                <div class="col-sm-10">
                    <select name="procedure" class="form-control">
                        <c:forEach items="${procedures}" var="procedure">
                            <option> <c:out value="${procedure}" />
                            </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="diagnosis" class="col-sm-2 control-label">Diagnosis</label>
                <div class="col-sm-10">
                    <select name="diagnosis" class="form-control">
                        <c:forEach items="${diagnoses}" var="diagnosis">
                            <option> <c:out value="${diagnosis}" />
                            </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="prescription" class="col-sm-2 control-label">Prescriptions</label>
                <div class="col-sm-10">
                    <select name="prescripton" class="form-control">
                        <c:forEach items="${prescriptions}" var="prescription">
                            <option> <c:out value="${prescription}" />
                            </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="surgeryPerformed" class="col-sm-2 control-label">Surgery Performed</label>
                <div class="col-sm-10">
                    <select name="surgeryPerformed" class="form-control">
                        <c:forEach items="${surgeries}" var="surgery">
                            <option> <c:out value="${surgery}" />
                            </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="freeformComments" class="col-sm-2 control-label">Freeform Comments</label>
                <div class="col-sm-10">
                    <textarea class="form-control" name="freeformComments" id="freeformComments" cols="80" rows="4"></textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Add</button>
                </div>
            </div>

        </form>
    </form
</jsp:attribute>

<jsp:attribute name="script">


</jsp:attribute>

</t:template>