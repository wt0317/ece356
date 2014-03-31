<%-- 
    Document   : appointments
    Created on : 21-Mar-2014, 8:50:30 PM
    Author     : Johnny
--%>

<%@page import="ece356.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
    <jsp:attribute name="style">
        <link rel="stylesheet" href="css/fullcalendar.css"></link>
        <link rel="stylesheet" href="css/fullcalendar.print.css"></link>
        <link rel="stylesheet" href="css/appointments.css"></link>
    </jsp:attribute>

    <jsp:attribute name="pagetitle">
        <c:if test="${(userObject.getRole()).equals('Staff')}">
            Manage Appointments
        </c:if>
        <c:if test="${(userObject.getRole()).equals('Doctor')}">
            View Appointments
        </c:if>
    </jsp:attribute>

    <jsp:attribute name="content">
        <c:if test="${(userObject.getRole()).equals('Staff')}">
                <!--<span class="current-doctor">
                    <select name="form" onchange="location=this.options[this.selectedIndex].getAttribute('data-redirect') + '?appDocUser='+this.options[this.selectedIndex].value+'&appDocName='+this.options[this.selectedIndex].innerHTML" >            
                    <c:forEach items="${docList}" var="doctor">
                        <option <c:if test="${doctor.getUsername() == appDoc.getUsername()}">selected</c:if> data-redirect="Appointments" value="${doctor.getUsername()}">${doctor.getName()}</option>
                    </c:forEach>
                    </select>
                </span>-->
                <div class="input-group">
                    <span id="appointmentHeader" class="input-group-addon">Manage Appointments: </span>
                    <select id="appointment-select-doctor" name="form" class="form-control" onchange="location=this.options[this.selectedIndex].getAttribute('data-redirect') + '?appDocUser='+this.options[this.selectedIndex].value+'&appDocName='+this.options[this.selectedIndex].innerHTML" >            
                        <c:forEach items="${docList}" var="doctor">
                            <option <c:if test="${doctor.getUsername() == appDoc.getUsername()}">selected</c:if> data-redirect="Appointments" value="${doctor.getUsername()}">${doctor.getName()}</option>
                        </c:forEach>
                    </select>
                </div>
        </c:if>
        <c:if test="${(userObject.getRole()).equals('Doctor')}">
        <h1 class="page-header">View Appointments</h1>
        </c:if>
        
        <div id='calendar'></div>
        <div class="modal fade" id="overlayMask" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
        <!-- Add Appointment Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">Add Appointment</h4>
                    </div>
                    <div class="modal-body">
                        <div  class="input-group">
                            <span class="input-group-addon">Title: </span>
                            <input id="appTitle" type="text" class="form-control" placeholder="Type of appointment?">
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon">Patient: </span>
                            <select id="appPatient" class="form-control">
                                <c:forEach items="${appDoc.patients}" var="patient">
                                    <option value="${patient.getUsername()}">${patient.getName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <button id="addAppointment" type="button" class="btn btn-primary" data-dismiss="modal">Add Appointment</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- More Info Modal -->
        <div class="modal fade" id="eventModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="event-modal-title"></h4>
                    </div>
                    <div class="modal-body">
                        <div  class="input-group">
                            <span class="input-group-addon">Time: </span>
                            <input id="event-modal-time" type="text" disabled="true" class="form-control" placeholder="Type of appointment?">
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon">Patient: </span>
                            <input id="event-modal-patient" type="text" disabled="true" class="form-control" placeholder="Type of appointment?">
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon">Doctor: </span>
                            <input id="event-modal-doctor" type="text" disabled="true" class="form-control" placeholder="Type of appointment?">
                        </div>
                        <div class="input-group">
                            <span class="input-group-addon">Created By: </span>
                            <input id="event-modal-staff" type="text" disabled="true" class="form-control" placeholder="Type of appointment?">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button id="deleteAppointment" type="button" class="btn btn-primary" data-dismiss="modal">Remove Appointment</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </jsp:attribute>

    <jsp:attribute name="script">
        <script>
        <c:if test="${(userObject.getRole()).equals('Staff')}">
            var selectable = true;
            var removeable = true;
        </c:if>
        <c:if test="${(userObject.getRole()).equals('Doctor')}">
            var selectable = false;
            var removeable = false;
        </c:if>
        var appointmentDoctor = '<c:out value="${appDoc.getName()}"/>';
        var appointmentDoctorID = <c:out value="${appDoc.getUsername()}"/>;
        var appointmentCreator = '<c:out value="${userObject.getName()}"/>';
        </script>
        <script type="text/javascript" src="js/jquery-ui.custom.min.js"></script>
        <script type="text/javascript" src="js/fullcalendar.min.js"></script>
        <script type="text/javascript" src="js/appointments.js"></script>
    </jsp:attribute>
</t:template>