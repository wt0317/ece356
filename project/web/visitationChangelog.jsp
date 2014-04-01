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
                            <th>Scheduled Time</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${visitations}" var="v">
                            <tr class="collapsed">
                                <c:choose>
                                    <c:when test="${v.getChangelog().size() == 0}">
                                        <td> ${v.getPKey()} </td>
                                    </c:when>

                                    <c:otherwise>
                                        <td onclick='expand(this, ${v.toJSON().toString()})'> + ${v.getPKey()} </td>
                                    </c:otherwise>
                              </c:choose>
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
                    var str = "<table class='table'>";
                    str += "<tr><th>Patient</th>";
                    str += "<th>Doctor</th>";
                    str += "<th>Start Time</th></tr>";
                    $.each(json.changelog, function() {
                        str += "<tr>";
                        str += "<td>" + this.patient + "</td>";
                        str += "<td>" + this.doctor + "</td>";
                        str += "<td>" + this.startTime + "</td>";
                        str += "</tr>";
                    });
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