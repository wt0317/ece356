<%-- 
    Document   : appointments
    Created on : 21-Mar-2014, 8:50:30 PM
    Author     : Johnny
--%>

<%@page import="ece356.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    <jsp:attribute name="style">
        <link rel="stylesheet" href="css/fullcalendar.css"></link>
        <link rel="stylesheet" href="css/fullcalendar.print.css"></link>
    </jsp:attribute>
    
    <jsp:attribute name="pagetitle">
      Manage Appointments
    </jsp:attribute>
    
    <jsp:attribute name="content">
      <h1 class="page-header">Manage Appointments</h1>
      <div id='calendar'></div>
    </jsp:attribute>
    
    <jsp:attribute name="script">
        <script type="text/javascript" src="js/jquery-ui.custom.min.js"></script>
        <script type="text/javascript" src="js/fullcalendar.min.js"></script>
        <script type="text/javascript" src="js/appointments.js"></script>
    </jsp:attribute>
</t:template>