<%-- 
    Document   : create_account
    Created on : Mar 18, 2014, 11:40:08 AM
    Author     : Wilson
--%>

<%@page import="ece356.User"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%! User user; %>
<% 
    user = (User) session.getAttribute("userObject");
    if (user != null && !user.getRole().equals("Admin") && !user.getRole().equals("Staff")) {
        response.sendRedirect("welcome.jsp");
        return;
    }
%>

<t:template>
    <jsp:attribute name="pagetitle">
      Create Account
    </jsp:attribute>
    <jsp:attribute name="content">
          <c:if test="${username != -1}">
              <div class="alert alert-success">Account created! Username: <c:out value="${username}"/></div>
          </c:if>
        <form class="form-horizontal" role="form" action="CreateAccountServlet" method="post">
          <h1 class="form-heading">General Account Information</h1>
          <div class="form-group">
            <label for="name" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
              <input class="form-control" id="name" name="name" placeholder="John Doe" required/>
            </div>
          </div>
          <div class="form-group">
            <label for="role" class="col-sm-2 control-label">Role</label>
            <div class="col-sm-10">
              <select class="form-control" id="role" name="role">
                <option>Patient</option>
                <c:if test="${userObject.getRole().equals('Admin')}">
                    <option>Doctor</option>
                    <option>Staff</option>
                    <option>Finance</option>
                    <option>Legal</option>
                </c:if>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="password" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password" required/>
            </div>
          </div>
          <div class="form-group">
            <label for="confirm_password" class="col-sm-2 control-label">Confirm Password</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="confirm_password" name="confirm_password" placeholder="Confirm Password" data-validation-matches-match="email" />
            </div>
          </div>
          <div class="form-group">
            <label for="address" class="col-sm-2 control-label">Address</label>
            <div class="col-sm-10">
                <input class="form-control" id="address" name="address" placeholder="123 Street Ave, Waterloo, ON, N2L 2W4" required/>
            </div>
          </div>
          <div class="form-group">
            <label for="Phone Number" class="col-sm-2 control-label">Phone Number</label>
            <div class="col-sm-10">
                <input class="form-control" id="phone" name="phone" placeholder="5195551234" required/>
            </div>
          </div>
          <!--Patient-->
          <div id="patientDiv" class="role-specific-fields" >
              <h2>Patient Specific Information</h2>
              <div class="form-group">
                <label for="healthCard" class="col-sm-2 control-label">Health Card</label>
                <div class="col-sm-10">
                    <input class="form-control" id="healthCard" name="healthCard" placeholder="1234-567-890-AB" required/>
                </div>
              </div>
              <div class="form-group">
                <label for="SIN" class="col-sm-2 control-label">SIN</label>
                <div class="col-sm-10">
                    <input class="form-control" id="sin" name="sin" placeholder="123456" required/>
                </div>
              </div>
              <div class="form-group">
                <label for="doctor" class="col-sm-2 control-label">Default Doctor</label>
                <div class="col-sm-10">
                    <select class="form-control" id="doctor" name="doctor">
                        <c:forEach items="${doctors.entrySet()}" var="d">
                            <option value="${d.getKey()}">${d.getValue()}</option>
                        </c:forEach>
                    </select>
                  </div>
              </div>
              <div class="form-group">
                <label for="health" class="col-sm-2 control-label">Health</label>
                <div class="col-sm-10">
                    <input class="form-control" id="health" name="health" placeholder="Health"/>
                </div>
              </div>
              <div class="form-group">
                <label for="comments" class="col-sm-2 control-label">Comments</label>
                <div class="col-sm-10">
                    <input class="form-control" id="comments" name="comments" placeholder="Comments"/>
                </div>
              </div>
          </div>
          <!--Doctor-->
          <div id="doctorDiv" class="role-specific-fields">
              <h2>Doctor Specific Information</h2>
              <div class="form-group">
                <label for="license" class="col-sm-2 control-label">License ID</label>
                <div class="col-sm-10">
                    <input class="form-control" id="license" name="license" placeholder="123456789" required/>
                </div>
              </div>
              <div class="form-group">
                <label for="licenseIssue" class="col-sm-2 control-label">License Issue Date</label>
                <div class="col-sm-10">
                    <input type="date" class="form-control" id="licenseIssue" name="licenseIssue" required/>
                </div>
              </div>
              <div class="form-group">
                <label for="licenseExpiry" class="col-sm-2 control-label">License Expiry Date</label>
                <div class="col-sm-10">
                    <input type="date" class="form-control" id="licenseExpiry" name="licenseExpiry" required/>
                </div>
              </div>
              <div class="form-group">
                <label for="dateHired" class="col-sm-2 control-label">Date Hired</label>
                <div class="col-sm-10">
                    <input type="date" class="form-control" id="dateHired" name="dateHired" required/>
                </div>
              </div>
              <div class="form-group">
                  <label class="col-sm-2 control-label">Assigned Staff(s)</label>
                  <div class="col-sm-10">
                      <c:forEach items="${staff.entrySet()}" var="s">
                          <input type="checkbox" id="assignedStaff" name="assignedStaff" value="${s.getKey()}"/>
                          <label >${s.getValue()}</label>
                          <br/>
                      </c:forEach>
                  </div>
              </div>
          </div>
          <!--Staff-->
          <div id="staffDiv" class="role-specific-fields">
              <h2>Staff Specific Information</h2>
              <div class="form-group">
                  <label class="col-sm-2 control-label">Assigned Doctor(s)</label>
                  <div class="col-sm-10">
                      <c:forEach items="${doctors.entrySet()}" var="d">
                          <input type="checkbox" id="assignedDoctors" name="assignedDoctors" value="${d.getKey()}"/>
                          <label>${d.getValue()}</label>
                          <br/>
                      </c:forEach>
                  </div>
              </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-default" name="submit">Create Account</button>
            </div>
          </div>
        </form>
    </jsp:attribute>
    <jsp:attribute name="script">
        <script>
//        $(function(){
//           alert("okay"); 
//        });
        $(".form-horizontal").validate({
            rules : {
                confirm_password : {
                    equalTo : "#password"
                },
                phone : {
                    digits : true,
                    rangelength : [10,10]
                },
                sin : {
                    digits : true,
                    rangelength : [6,6]
                }
            },
            messages : {
                phone : {
                    rangelength : "Phone number must be 10 digits, no hyphen, no spaces"
                },
                sin : {
                    rangelength : "SIN must be 6 digits, no hyphen, no spaces"
                }
            }
        });
        $("#role").change(function(){
            $(".role-specific-fields").hide();
            switch (this.value) {
                case "Patient" :
                    $("#patientDiv").show();
                    break;
                case "Doctor" :
                    $("#doctorDiv").show();
                    break;
                case "Staff" :
                    $("#staffDiv").show();
                    break;
            }
        }).change();
        </script>
    </jsp:attribute>
</t:template>