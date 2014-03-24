<%-- 
    Document   : lookup_doctor_summary
    Created on : 23-Mar-2014, 9:45:14 PM
    Author     : rkn24
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    <jsp:attribute name="pagetitle">
      Lookup Doctor's Summary
    </jsp:attribute>
    <jsp:attribute name="content">
      <form class="form-signin" role="form" action="LoginServlet" method="post">
          <div class="signin-box">
              <input name="name" type="input" class="form-control" placeholder="name" required="" autofocus="">
              <button class="btn btn-lg btn-primary btn-block" type="submit">Search</button>
          </div>
      </form>
    </jsp:attribute>
</t:template>
