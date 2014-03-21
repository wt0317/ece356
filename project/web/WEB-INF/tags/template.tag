<%-- 
    Document   : template
    Created on : 18-Mar-2014, 4:45:31 PM
    Author     : rkn24
--%>

<%@tag import="ece356.Directory"%>

<%@tag description="Template" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="pagetitle" fragment="true"%>
<%@attribute name="content" fragment="true"%>

<%! String role; %>
<% role = ((Directory)session.getAttribute("userObject")).getRole(); %>  

<%-- any content can be specified here e.g.: --%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>
          <jsp:invoke fragment="pagetitle"/>
        </title>
        
        <!-- Bootstrap core CSS -->
        <link href="assets/css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/dashboard.css" rel="stylesheet">
        
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    
    <%! Directory user;%>
    <% 
         if (session.getAttribute("userObject") != null){
            user = (Directory) session.getAttribute("userObject");
        } else {
            response.sendRedirect("index.jsp");
            return;
        }
       
    %>
    
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
          <div class="container-fluid">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="#">
                <jsp:invoke fragment="pagetitle"/>
              </a>
            </div>
            <div class="navbar-collapse collapse">
              <ul class="nav navbar-nav navbar-right">
<!--                <li><a href="#">Dashboard</a></li>
                <li><a href="#">Settings</a></li>-->
                <li><a href="#">Welcome! <% out.print(user.getName()); %></a></li>
<!--                <li><a href="#">Help</a></li>-->
              </ul>
<!--              <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
              </form>-->
            </div>
          </div>
        </div>

        <div class="container-fluid">
          <div class="row">
            <div class="col-sm-3 col-md-2 sidebar">
              <ul class="nav nav-sidebar">
                <li><a href="welcome.jsp">Home</a></li>
                
                <%-- DOCTOR MENU --%>
                <%
                 if (role.equals("doctor"))      {
                        out.print("<li><a href=\"#\">Lookup Visitation Record</a></li>");
                        out.print("<li><a href=\"#\">Lookup Patient Information</a></li>");
                        out.print("<li><a href=\"#\">Manage Appointments</a></li>");
                        out.print("<li><a href=\"#\">Account Settings</a></li>");
                }
                %>    

                   <%-- PATIENT MENU --%>
                <%                 
                 if (role.equals("patient"))      {
                        out.print("<ul class=\"nav nav-sidebar\">");
                        out.print(" <li class=\"active\"><a href=\"#\">Summary</a></li>");
                        out.print(" <li><a href=\"#\">Update Personal Information</a></li>");
                        out.print(" <li><a href=\"#\">Change Password</a></li>");
                        out.print("</ul>");
                        out.print("<ul class=\"nav nav-sidebar\">");
                        out.print(" <li><a href=\"\">Future Appointments</a></li>");
                        out.print(" <li><a href=\"\">Past Appointments</a></li>");
                        out.print("</ul>");
                }
                %>  

                <%-- STAFF MENU --%>
                <%                 
                 if (role.equals("staff"))      {
                        out.print("<li><a href=\"#\">Lookup Visitation Record</a></li>");
                        out.print("<li><a href=\"#\">Lookup Patient Information</a></li>");
                        out.print("<li><a href=\"#\">Manage Appointments</a></li>");
                        out.print("<li><a href=\"#\">Create Account</a></li>");
                        out.print("<li><a href=\"#\">Account Settings</a></li>");
                }
                %>  

                <%-- FINANCE MENU --%>
                <%                 
                 if (role.equals("finance"))      {
                        out.print("<li><a href=\"#\">Lookup Doctor Summary</a></li>");
                        out.print("<li><a href=\"lookup_patient_summary.jsp\">Lookup Patient Summary</a></li>");
                        out.print("<li><a href=\"#\">Account Settings</a></li>");
                }             
                %>
                </ul>
            </div>
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
              <jsp:invoke fragment="content"/>
            </div>
          </div>
        </div>

        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/docs.min.js"></script>
    </body>
</html>
