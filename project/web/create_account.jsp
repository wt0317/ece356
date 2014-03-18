<%-- 
    Document   : create_account
    Created on : Mar 18, 2014, 11:40:08 AM
    Author     : Wilson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Account</title>
        
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <style>
          body {
            background: #CCCCCC;
          }
        </style>
    </head>
    <body>
      <div class="container">
        <form class="form-horizontal" role="form" action="CreateAccountServlet" method="post">
          <h1 class="form-heading">Create Account</h2>
          <div class="form-group">
            <label for="name" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
              <input class="form-control" id="name" name="name" placeholder="Name">
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
                <option>Lawyer</option>                
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="password" class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10">
              <input type="password" class="form-control" id="password" name="password" placeholder="Password">
            </div>
          </div>
          <div class="form-group">
            <label for="confirm_password" class="col-sm-2 control-label">Confirm Password</label>
            <div class="col-sm-10">
              <input type="password" class="form-control" id="confirm_password" name=confirm_password placeholder="Confirm Password">
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-default">Create Account</button>
            </div>
          </div>
        </form>
      </div>
    </body>
</html>
