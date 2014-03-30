<%-- 
    Document   : doctorSummary
    Created on : 25-Mar-2014, 11:39:00 PM
    Author     : rkn24
--%>

<%@page import="ece356.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%! String role; %>
<% role = ((User)session.getAttribute("userObject")).getRole(); %>  

<t:template>
    <jsp:attribute name="pagetitle">
      Doctor Summary
    </jsp:attribute>
    
    <jsp:attribute name="content">
            <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css"/>
      <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
      <script>
        $(function() {
          $("#startDate").datepicker({
            defaultDate: "+1w",
            changeMonth: true,
            numberOfMonths: 1,
            dateFormat: "yy-mm-dd",
            onClose: function( selectedDate ) {
              $("#startDate").datepicker( "option", "maxDate", selectedDate );
              $("#endDate").datepicker( "option", "minDate", selectedDate );
            }
          });
          $("#endDate").datepicker({
            defaultDate: "+1w",
            changeMonth: true,
            numberOfMonths: 1,
            dateFormat: "yy-mm-dd",
            onClose: function( selectedDate ) {
              $("#startDate").datepicker( "option", "maxDate", selectedDate );
            }
          });
        });
      </script>
      <c:if test="${queryDone.equals(true)}" >
          <h2 class="sub-header">Summary for Dr. <c:out value="${doctorName}"/></h2>
          <h3 class="sub-header">
            This doctor has <c:out value="${numPatients}"/> patients.           
          </h3>
          <form action="DoctorSummaryServlet" method="post">
            <input name="doctor" type="hidden" value='<c:out value="${doctor}"/>' />
            <input name="doctorName" type="hidden" value='<c:out value="${doctorName}"/>' />
            <label for="startDate">&nbsp;&nbsp;Start Date&nbsp;&nbsp;</label>
            <input type="text" id="startDate" name="startDate" placeholder="YYYY-MM-DD" value="<c:out value="${startDate}"/>"/>
            <label for="endDate">&nbsp;&nbsp;End Date&nbsp;&nbsp;</label>
            <input type="text" id="endDate" name="endDate" placeholder="YYYY-MM-DD" value="<c:out value="${endDate}"/>"/>
            <button type="submit" class="btn btn-default">Submit</button>
          </form>
            
          <c:if test="${listDoctorSummaryEmpty.equals(false)}">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Patient ID</th>
                            <th># of Visitations</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listDoctorSummary}" var="data">
                            <tr>
                                <td> <c:out value="${data.getUsername()}"/></td>
                                <td> <c:out value="${data.getPatientCount()}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>  
          </c:if>
      </c:if>

      
      </div>
    </jsp:attribute>
</t:template>
