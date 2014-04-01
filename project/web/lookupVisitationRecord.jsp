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
        <c:if test="${(userObject.getRole()).equals('Patient')}">
            Patient
        </c:if>
    </jsp:attribute>

    <jsp:attribute name="content">

        <c:if test="${(userObject.getRole()).equals('Doctor')}">
            <c:if test="${success.equals('Added visitation record!')}">
                <div class="alert alert-success">
                    <strong>
                        Success!
                    </strong>
                    ${success}
                </div>
            </c:if>

            <form role="form" action="AddVisitationRecordCreateFormServlet" method="post">
                <button type="submit" class="btn btn-primary" name="button" value="add">Add Visitation Record</button>
            </form>
            <br>            
            <form role="form" action="AddVisitationRecordCreateFormServlet" method="post">
                <button type="submit" class="btn btn-default" name="button" value="search">Search</button>
            </form>

            <c:if test="${status.equals('Valid')}">
                <h2 class="sub-header"> Visitation Records </h2>
                <p> <strong> Count </strong>: ${count} </p> 
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <c:forEach items="${columnNames}" var="attribute">
                                    <th><c:out value="${attribute}"/></th>
                                    </c:forEach>
                                    <th> Action </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${records}" var="data">
                                <tr>
                                    <td> <c:out value="${data.patientName}"/></td>
                                    <td> <c:out value="${data.timeScheduled}" /></td>
                                    <td> <c:out value="${data.startTime}" /></td>
                                    <td> <c:out value="${data.endTime}" /></td>
                                    <td> <c:out value="${data.creationTime}" /></td>
                                    <td> <c:out value="${data.createdName}" /></td>
                                    <td> <c:out value="${data.procedureName}" /></td>
                                    <td> <c:out value="${data.diagnosisName}" /></td>
                                    <td> <c:out value="${data.prescriptionName}" /></td>
                                    <td> <c:out value="${data.surgeryName}" /></td>
                                    <td> <c:out value="${data.comments}" /></td>
                                    <td> <c:out value="${data.revisionComments}" /></td>
                                    <td> <a href="EditVisitations?doctorUsername=${userObject.getUsername()}&startTime=${data.startTime}&creationTime=${data.creationTime}"> Edit </a> </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </c:if>

        <c:if test="${(userObject.getRole()).equals('Patient')}">

            <c:if test="${status.equals('Valid')}">
                <h2 class="sub-header"> Visitation Records </h2>
                <p> <strong> Count </strong>: ${count} </p> 
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <c:forEach items="${columnNames}" var="attribute">
                                    <th><c:out value="${attribute}"/></th>
                                    </c:forEach>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${records}" var="data">
                                <tr>
                                    <td> <c:out value="${data.doctorName}"/></td>
                                    <td> <c:out value="${data.timeScheduled}" /></td>
                                    <td> <c:out value="${data.startTime}" /></td>
                                    <td> <c:out value="${data.endTime}" /></td>
                                    <td> <c:out value="${data.creationTime}" /></td>
                                    <td> <c:out value="${data.createdName}" /></td>
                                    <td> <c:out value="${data.procedureName}" /></td>
                                    <td> <c:out value="${data.diagnosisName}" /></td>
                                    <td> <c:out value="${data.prescriptionName}" /></td>
                                    <td> <c:out value="${data.surgeryName}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </c:if>

        <c:if test="${(userObject.getRole()).equals('Staff')}">
            <c:if test="${status.equals('Valid')}">
                <h2 class="sub-header"> Visitation Records </h2>
                <p> <strong> Count </strong>: ${count} </p> 
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <c:forEach items="${columnNames}" var="attribute">
                                    <th><c:out value="${attribute}"/></th>
                                    </c:forEach>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${records}" var="data">
                                <tr>
                                    <td> <c:out value="${data.patientName}"/></td>
                                    <td> <c:out value="${data.timeScheduled}" /></td>
                                    <td> <c:out value="${data.startTime}" /></td>
                                    <td> <c:out value="${data.endTime}" /></td>
                                    <td> <c:out value="${data.creationTime}" /></td>
                                    <td> <c:out value="${data.createdName}" /></td>
                                    <td> <c:out value="${data.procedureName}" /></td>
                                    <td> <c:out value="${data.diagnosisName}" /></td>
                                    <td> <c:out value="${data.prescriptionName}" /></td>
                                    <td> <c:out value="${data.surgeryName}" /></td>
                                    <td> <c:out value="${data.comments}" /></td>
                                    <td> <c:out value="${data.revisionComments}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </c:if>
    </jsp:attribute>
</t:template>