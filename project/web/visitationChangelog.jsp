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
        Visitation Changelog
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container-fluid">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Visitation Records</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${visitations}" var="v">
                            <tr class="collapsed">
                                <td onclick='expand(this, ${v.toJSON().toString()})'> + ${v.getDoctorName()} - ${v.getPatientName()} - ${v.getStartTime()} [${v.numChanges()}] </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>  
        </div>
    </jsp:attribute>
    <jsp:attribute name="script">
        <script type="text/javascript">
            function expand(cur, json) {
                if ($(cur).parent().hasClass('collapsed')) {
                    $(cur).parent().removeClass('collapsed').addClass('expanded');
                    var str = "<table class='table'><tr>";
                    str += "<th>Time Scheduled</th>";
                    str += "<th>End Time</th>";
                    str += "<th>Procedure</th>";
                    str += "<th>Diagnosis</th>";
                    str += "<th>Prescription</th>";
                    str += "<th>Presc Legal</th>";
                    str += "<th>Surgery</th>";
                    str += "<th>Comments</th>";
                    str += "<th>Created By</th>";
                    str += "<th>Creation Time</th>";
                    str += "<th>Revision Comments</th></tr>";
                    
                    str += "<tr style='outline: thin solid green;'>";
                    str += "<td>" + json.timeScheduled + "</td>";
                    str += "<td>" + json.endTime + "</td>";
                    str += "<td>" + json.procedureName + "</td>";
                    str += "<td>" + json.diagnosisName + "</td>";
                    str += "<td>" + json.prescriptionName + "</td>";
                    str += "<td>" + json.legal + "</td>";
                    str += "<td>" + json.surgeryName + "</td>";
                    str += "<td>" + json.comments + "</td>";
                    str += "<td>" + json.createdName + "</td>";
                    str += "<td>" + json.creationTime + "</td>";
                    str += "<td>" + json.revisionComments + "</td>";
                    str += "</tr>";
                    
                    if (json.changelog) {
                        $.each(json.changelog, function() {
                            str += "<tr>";
                            str += "<td>" + this.timeScheduled + "</td>";
                            str += "<td>" + this.endTime + "</td>";
                            str += "<td>" + this.procedureName + "</td>";
                            str += "<td>" + this.diagnosisName + "</td>";
                            str += "<td>" + this.prescriptionName + "</td>";
                            str += "<td>" + this.legal + "</td>";
                            str += "<td>" + this.surgeryName + "</td>";
                            str += "<td>" + this.comments + "</td>";
                            str += "<td>" + this.createdName + "</td>";
                            str += "<td>" + this.creationTime + "</td>";
                            str += "<td>" + this.revisionComments + "</td>";
                            str += "</tr>";
                        });
                    }
                    str += "</table>"
                    $(cur).append(str);
                } else {
                    $(cur).parent().removeClass('expanded').addClass('collapsed');
                    $(cur).children().first().remove();
                }
            }
        </script>
    </jsp:attribute>
</t:template>