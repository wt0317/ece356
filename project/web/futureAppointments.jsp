<%-- 
    Document   : futureAppointments
    Created on : 31-Mar-2014, 1:55:24 PM
    Author     : Johnny
--%>

<%@page import="ece356.User"%>
<%@page import="ece356.Appointment"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<t:template>
    <jsp:attribute name="style">
        <link rel="stylesheet" href="css/futureAppointments.css"></link>
    </jsp:attribute>
        
    <jsp:attribute name="pagetitle">
        Future Appointments
    </jsp:attribute>

    <jsp:attribute name="content">   
        <h1 class="header">Future Appointments</h1>
        <c:choose>
            <c:when test="${empty appointments.size()}">
                You have no future appointments currently.
            </c:when>
            <c:otherwise>
                <table class="table table-hover">
                    <thead>
                        <th>Title</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Doctor</th>
                    </thead>
                    <tbody>
                    <c:forEach items="${appointments}" var="appointment" varStatus="status">
                        <tr>
                            <td>${appointment.getTitle()}</td>
                            <td>${appointment.getDateString(appointment.getStartTime())}</td>
                            <td>${appointment.getTimeString(appointment.getStartTime())} - ${appointment.getTimeString(appointment.getEndTime())}</td>
                            <td>${appointment.getDoctorName()}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</t:template>