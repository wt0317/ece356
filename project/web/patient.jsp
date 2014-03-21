<%@page import="java.util.List"%>
<%@page import="ece356.Patients"%>
<%@page import="ece356.DBAO"%>
<%@page import="ece356.Directory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
    Directory user = (Directory) session.getAttribute("userObject");

    Patients patient = DBAO.getUserInfo(user.getUsername(), user.getPassword());
    
    List<String> patientList = DBAO.getAllPatients();
    
    request.setAttribute("hin", patient.getHealthCard());
    request.setAttribute("usermane", user.getUsername());
    request.setAttribute("name", user.getName());
    request.setAttribute("address", user.getAddress());
    
    
    //Patients patient = new Patients(user.getUsername());
    //patient.queryUserInfo();
    //System.out.println("test : " + user.getAddress());
   //request.setAttribute("address", user.getAddress());
   
%>
<t:template> 
    <jsp:attribute name="pagetitle">
      Patient Homepage
    </jsp:attribute>

    <jsp:attribute name="content">
    
        <div class="container-fluid">
            <div class="row-fluid">
          <div class="span9">
          <div class="well">
            <h1>Notifications</h1>
            <p>You have no notifications at this time</p>
          </div>
          <div class="row-fluid">
            <div class="span4">
              <h2>Personal Information</h2>
              <p>User ID: 
                  <select class="form-control" id="patientList" name="patientList">
                    <c:forEach items="${patientList}" var="item">
                       <option>${item}</option>
                    </c:forEach>
                  </select>
              </p>
              <p>Name: 
                  <input name="name" type="input" class="form-control" value="${name}" required="" autofocus="">
              </p>
              <p>Address: 
                  <input name="address" type="input" class="form-control" value="${address}" required="" autofocus="">
              </p>
              <p>Phone Number: 
                  <input name="phonenum" type="input" class="form-control" value="${phonenum}" required="" autofocus="">
              </p>
              <p><a class="btn" href="#">View details &raquo;</a></p>
            </div><!--/span-->
            <div class="span4">
              <h2>Heading</h2>
              <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
              <p><a class="btn" href="#">View details &raquo;</a></p>
            </div><!--/span-->
            <div class="span4">
              <h2>Heading</h2>
              <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
              <p><a class="btn" href="#">View details &raquo;</a></p>
            </div><!--/span-->
          </div><!--/row-->
          <div class="row-fluid">
            <div class="span4">
              <h2>Heading</h2>
              <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
              <p><a class="btn" href="#">View details &raquo;</a></p>
            </div><!--/span-->
            <div class="span4">
              <h2>Heading</h2>
              <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
              <p><a class="btn" href="#">View details &raquo;</a></p>
            </div><!--/span-->
            <div class="span4">
              <h2>Heading</h2>
              <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
              <p><a class="btn" href="#">View details &raquo;</a></p>
            </div><!--/span-->
          </div><!--/row-->
        </div><!--/span-->
      </div><!--/row-->
      
      <hr>
        </div>
    </jsp:attribute>
</t:template>