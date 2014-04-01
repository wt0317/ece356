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
                margin-left:3em;
                text-align:justify;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container-fluid">
            <div class="row">
                <h2>Patient Information</h2>
                <c:if test="${param.updated}">
                    <div class="alert alert-success">Database updated</div>
                </c:if>
                <form class="form-horizontal" action="UpdatePatientInfoServlet" method="post">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="name" class="col-md-6 control-label">Name</label> 
                            <div class="col-md-6">
                                <input name="name" id="name" class="form-control" value="${p.getName()}" disabled/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="healthCard" class="col-md-6 control-label">Health Card</label> 
                            <div class="col-md-6">
                                <input name="healthCard" id="healthCard" class="form-control" value="${p.getHealthCard()}" disabled/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="sin" class="col-md-6 control-label">Social Insurance Number</label> 
                            <div class="col-md-6">
                                <input name="sin" id="sin" class="form-control" value="${p.getSin()}" disabled/>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="doctorName" class="col-md-6 control-label">Default Doctor</label> 
                            <div class="col-md-6">
                                <input name="doctorName" id="doctorName" class="form-control" value="${p.getDefaultDoctor().getName()}" disabled/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="numVisits" class="col-md-6 control-label">Number of Visits</label> 
                            <div class="col-md-6">
                                <input name="numVisits" id="numVisits" class="form-control" value="${p.getNumOfVisits()}" disabled/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="health" class="col-md-6 control-label">Current Health</label> 
                            <div class="col-md-6">
                                <input name="health" id="health" class="form-control" value="${p.getCurrentHealth()}" disabled/>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </jsp:attribute>
</t:template>