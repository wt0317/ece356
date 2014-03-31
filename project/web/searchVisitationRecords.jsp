<%-- 
    Document   : searchVisitationRecords
    Created on : Mar 30, 2014, 2:08:15 PM
    Author     : FireChemist
--%>
<%@page import="ece356.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<t:template>
    <jsp:attribute name="pagetitle">
        <c:if test="${(userObject.getRole()).equals('Doctor')}">
            Doctor
        </c:if>
        <c:if test="${(userObject.getRole()).equals('Patient')}">
            Patient
        </c:if>
    </jsp:attribute>

    <jsp:attribute name="content">

        <c:if test="${error.equals('true')}">
            <div class="alert alert-warning">
                <Strong>
                    Error: 
                </Strong>
                ${errorMessage}
            </div>
        </c:if>

        <h2> Search </h2>
        <p> Please leave fields empty or as default if not searching by them. </p>

        <form class="form-horizontal" role="form" action="LookupVisitationRecordsServlet" method="post">
            <div class="form-group">
                <label for="patientName" class="col-sm-2 control-label">Patient Name</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="patientName" id="patientName" placeholder="Harley Quinn">
                </div>
            </div>

            <div class="form-group">
                <label for="patientUsername" class="col-sm-2 control-label">Patient Username</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="patientUsername" id="patientUsername" placeholder="332312">
                </div>
            </div>

            <div class="form-group">
                <label for="timeScheduled" class="col-sm-2 control-label">Time Scheduled Range </label>
                <div class="col-sm-10">
                    <input type="datetime-local" class="form-control" name="timeScheduled1" value="${timeScheduled1}">
                    <input type="datetime-local" class="form-control" name="timeScheduled2" value="${timeScheduled2}">
                </div>
            </div>
            <div class="form-group">
                <label for="startTime" class="col-sm-2 control-label">Start Time Range</label>
                <div class="col-sm-10">
                    <input type="datetime-local" class="form-control" name="timeStart1" value="${timeStart1}">
                    <input type="datetime-local" class="form-control" name="timeStart2" value="${timeStart2}">
                </div>
            </div>
            <div class="form-group">
                <label for="endTime" class="col-sm-2 control-label">End Time Range</label>
                <div class="col-sm-10">
                    <input type="datetime-local" class="form-control" name="timeEnd1" value="${timeEnd1}">
                    <input type="datetime-local" class="form-control" name="timeEnd2" value="${timeEnd2}">
                </div>
            </div>
            <div class="form-group">
                <label for="creationTime" class="col-sm-2 control-label">Creation Time Range</label>
                <div class="col-sm-10">
                    <input type="datetime-local" class="form-control" name="timeCreation1" value="${timeCreation1}">
                    <input type="datetime-local" class="form-control" name="timeCreation2" value="${timeCreation2}">
                </div>
            </div>
            <div class="form-group">
                <label for="diagnosisName" class="col-sm-2 control-label">Diagnosis Name</label>
                <div class="col-sm-10">
                    <select class="form-control" name="diagnosisName"  required>
                        <option> Any </option>
                        <c:forEach items="${diagnoses}" var="diagnosis">
                            <option> <c:out value="${diagnosis}" />
                            </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="procedureName" class="col-sm-2 control-label">Procedure Name</label>
                <div class="col-sm-10">
                    <select class="form-control" name="procedureName" required>
                        <option> Any </option>
                        <c:forEach items="${procedures}" var="procedure">
                            <option> <c:out value="${procedure}" />
                            </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="prescriptionName" class="col-sm-2 control-label">Prescription Name</label>
                <div class="col-sm-10">
                    <select class="form-control" name="prescriptionName" required>
                        <option> Any </option>
                        <c:forEach items="${prescriptions}" var="prescription">
                            <option> <c:out value="${prescription}" />
                            </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="surgeryName" class="col-sm-2 control-label">Surgery Name</label>
                <div class="col-sm-10">
                    <select class="form-control" name="surgeryName" required>
                        <option> Any </option>
                        <c:forEach items="${surgeries}" var="surgery">
                            <option> <c:out value="${surgery}" />
                            </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="comments" class="col-sm-2 control-label">Comments</label>
                <div class="col-sm-10">
                    <input type="text" name="comments" class="form-control" id="patientName" placeholder="Good condition">
                </div>
            </div>
            <div class="form-group">
                <label for="revisionComments" class="col-sm-2 control-label">Revision Comments</label>
                <div class="col-sm-10">
                    <input type="text" name="revisionComments" class="form-control" id="patientName" placeholder="Slight periodic fever">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" class="btn btn-default" onclick="history.back()">Back</button>
                    <button type="submit" class="btn btn-default" name="button" value="search">Search</button>
                </div>
            </div>

        </form>



    </jsp:attribute>



</t:template>