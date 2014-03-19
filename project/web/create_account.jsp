<%-- 
    Document   : create_account
    Created on : Mar 18, 2014, 11:40:08 AM
    Author     : Wilson
--%>

<%@page import="java.util.List"%>
<%@page import="ece356.Doctors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%! boolean success; %>
<% success = (request.getAttribute("success") != null) ? (Boolean) request.getAttribute("success") : false; %>  
<%! List<Doctors> doctors; %>
<% doctors = (List<Doctors>) request.getAttribute("doctors"); %>  

<t:template>
    <jsp:attribute name="pagetitle">
      Project Zero
    </jsp:attribute>
    <jsp:attribute name="leftmenu">
    <ul class="nav nav-sidebar">
      <li class="active"><a href="#">Overview</a></li>
      <li><a href="#">Past Appointments</a></li>
    </ul>
    <ul class="nav nav-sidebar">
      <li><a href="">Update Address and Phone</a></li>
      <li><a href="">Change Password</a></li>
    </ul>
    </jsp:attribute>
    <jsp:attribute name="content">
          <c:if test="${success}">
              <div class="alert alert-success alert-dismissable">Account created!</div>
          </c:if>
        <form class="form-horizontal" role="form" action="CreateAccountServlet" method="post">
          <h1 class="form-heading">Create Account</h1>
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
                <option>Doctor</option>
                <option>Staff</option>
                <option>Finance</option>            
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
                <input class="form-control" id="phone" name="phone" placeholder="519-555-1234" required/>
            </div>
          </div>
          <!--Patient-->
          <div id="patientDiv" class="role-specific-fields" >
              <h2>Patient Specific Information</h2>
              <div class="form-group">
                <label for="healthCard" class="col-sm-2 control-label">Health Card</label>
                <div class="col-sm-10">
                    <input class="form-control" id="healthCard" name="healthCard" placeholder="12345678ab" required/>
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
                        <c:forEach items="${doctors}" var="item">
                            <option>${item.getDirectory().getName()}</option>
                        </c:forEach>
                    </select>
                  </div>
              </div>
              <div class="form-group">
                <label for="Health" class="col-sm-2 control-label">Health</label>
                <div class="col-sm-10">
                    <input class="form-control" id="health" name="health" placeholder="Health"/>
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
                <label for="dateHired" class="col-sm-2 control-label">Date Hired</label>
                <div class="col-sm-10">
                    <input type="date" class="form-control" id="dateHired" name="dateHired" required/>
                </div>
              </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-default">Create Account</button>
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
            }
        }).change();
        </script>
    </jsp:attribute>
</t:template>