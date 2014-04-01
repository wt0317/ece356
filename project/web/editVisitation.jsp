<%-- 
    Document   : editVisitation
    Created on : Mar 31, 2014, 10:42:06 PM
    Author     : FireChemist
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
    <jsp:attribute name="pagetitle">
        Doctor
    </jsp:attribute>
    <jsp:attribute name="content">
        <h2> Edit </h2>
        <form class="form-horizontal" role="form" action="EditVisitationRecordInsertServlet" method="post">
            <div class="form-group">
                <c:if test="${error.equals('Invalid')}">
                    <div class="alert alert-danger">
                        <Strong>
                            Error: 
                        </Strong>
                        This patient does not exist!
                    </div>
                </c:if>
                <c:if test="${error.equals('Duplicate')}">
                    <div class="alert alert-warning">
                        <Strong>
                            Warning: 
                        </Strong>
                        Duplicate match encountered, please select ID or click
                        <a href="javascript:history.back()"> Back </a> to return.
                    </div>
                </c:if>

                <c:if test="${error.equals('invalidEndTime')}">
                    <div class="alert alert-warning">
                        <Strong>
                            Warning: 
                        </Strong>
                        End time cannot be before or the same as start time!
                    </div>
                </c:if>

                <label for="patientName" class="col-sm-2 control-label">Patient Name</label>
                <div class="col-sm-10">
                    <c:choose>
                        <c:when test="${error.equals('Duplicate')}">
                            <fieldset disabled>
                                <input type="text" class="form-control" id="disabledTextInput" name="name" value="${name}">
                            </fieldset>
                        </c:when>
                        <c:otherwise>
                            <input class="form-control" id="name" name="name" placeholder="Bruce Wayne" value="${patientName}" required>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <c:if test="${error.equals('Duplicate')}" >
                <div class="form-group">
                    <label for="searchID" class="col-sm-2 control-label">ID</label>   
                    <div class="col-sm-10">
                        <select name="username" class="form-control">
                            <c:forEach items="${listPatientID}" var= "username">
                                <option> <c:out value="${username}" />
                                </c:forEach>
                        </select>   
                    </div>
                </div>
            </c:if>
            <div class="form-group">
                <label for="timeScheduled" class="col-sm-2 control-label">Time Scheduled</label>
                <div class="col-sm-10">
                    <input type="datetime-local" class="form-control" name="timeScheduled" value="${timeScheduled}" required>
                </div>
            </div>
            <div class="form-group">
                
                    <label for="disabledTextInput" class="col-sm-2 control-label">Start Time</label>
                    <div class="col-sm-10">
                        <input type="datetime-local" id=disabledTextInput class="form-control" name="startTime" value="${startTime}" required>
                    </div>
                
            </div>
            <div class="form-group">
                <label for="endTime" class="col-sm-2 control-label">End Time</label>
                <div class="col-sm-10">
                    <input type="datetime-local" class="form-control" name="endTime" value="${endTime}" required>
                </div>
            </div>
            <div class="form-group">
                <label for="procedure" class="col-sm-2 control-label">Procedure</label>
                <div class="col-sm-10">
                    <select name="procedure" class="form-control" required>
                        <c:forEach items="${procedures}" var="procedure">
                            <option value="${procedure}" ${procedure == procedureName ? 'selected' : ''}>${procedure}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="diagnosis" class="col-sm-2 control-label">Diagnosis</label>
                <div class="col-sm-10">
                    <select name="diagnosis" class="form-control" required>
                        <c:forEach items="${diagnoses}" var="diagnosis">
                            <option value="${diagnosis}" ${diagnosis == diagnosisName ? 'selected' : ''}>${diagnosis}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="prescription" class="col-sm-2 control-label">Prescriptions</label>
                <div class="col-sm-10">
                    <select name="prescription" class="form-control" required>
                        <c:forEach items="${prescriptions}" var="prescription">
                            <option value="${prescription}" ${prescription == prescriptionName ? 'selected' : ''}>${prescription}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="surgeryPerformed" class="col-sm-2 control-label">Surgery Performed</label>
                <div class="col-sm-10">
                    <select name="surgery" class="form-control" required>
                        <c:forEach items="${surgeries}" var="surgery">
                            <option value="${surgery}" ${surgery == surgeryName ? 'selected' : ''}>${surgery}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="freeformComments" class="col-sm-2 control-label">Freeform Comments</label>
                <div class="col-sm-10">
                    <textarea class="form-control" name="freeformComments" id="freeformComments" cols="80" rows="4" >${freeformComments}</textarea>
                </div>
            </div>

            <div class="form-group">
                <label for="revisionComments" class="col-sm-2 control-label">Revision Comments</label>
                <div class="col-sm-10">
                    <textarea class="form-control" name="revisionComments" id="freeformComments" cols="80" rows="4" >${revisionComments}</textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:if test="${error.equals('Duplicate')}">
                        <button type="button" class="btn btn-default" onclick="history.back()">Back</button>
                    </c:if>
                    <button type="submit" class="btn btn-default" name="button" value="edit" >Edit</button>
                </div>
            </div>

        </form>
    </form
</jsp:attribute>
</t:template>