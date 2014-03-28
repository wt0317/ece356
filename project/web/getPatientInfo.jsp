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
        <c:out value="${p.getName()}" />
    </jsp:attribute>
    <jsp:attribute name="style">
        <style>
            .comments {
                padding-left:5em;
                text-align:justify;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container-fluid">
            <div class="row">
                <h2>Patient Information</h2>
                <div class="col-md-6">
                    <p>Name: <c:out value="${p.getName()}"/></p>
                    <p>Health Card: <c:out value="${p.getHealthCard()}"/></p>
                    <p>Social Insurance Number: <c:out value="${p.getSin()}"/></p>
                </div>
                <div class="col-md-6">
                    <p>Default Doctor: <c:out value="${p.getDefaultDoctor().getName()}"/></p>
                    <p>Number of Visits: <c:out value="${p.getNumOfVisits()}"/></p>
                    <p>Current Health: <c:out value="${p.getCurrentHealth()}"/></p>
                </div>
                <div class="col-md-12">
                    <p>Comment: <p class='comments'><c:out value="${p.getComment()}"/></p></p>
                </div>
            </div>
            <hr>
            <div class="row">
                <h2>Permissions</h2>
                <div class="table-responsive col-md-8" id="permissionsTableDiv">
                    <table class="table table-striped table-hover" id="permissionsTable">
                        <c:forEach items="${p.getPermissions()}" var="perm">
                            <tr id=${perm.getUsername()}>
                                <td><c:out value="${perm.getName()}"/></td>
                                <td>
                                    <button type="button" class="close" aria-hidden="true" onclick="removePermission(this, <c:out value="${perm.getUsername()}"/>)">
                                        &times;
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="form-group">
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-plus"></span></button>
                        <button type="button" class="btn btn-primary" name="savePermissions" onclick="sendAjax(<c:out value="${p.getUsername()}"/>)">Save</button>
                    </div>
                </div>
            </div>
            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">Add Permission</h4>
                        </div>
                        <div class="modal-body">
                            <label for="role" class="col-sm-2 control-label">Role</label>
                            <select class="form-control" id="role" name="role">
                                <option>Doctor</option>
                                <option>Staff</option>
                            </select>
                            <div class="chooseEmployee" id="chooseDoctor">
                                <label for="doctor" class="col-sm-2 control-label">Doctor</label>
                                <select class="form-control" id="doctor" name="doctor">
                                    <c:forEach items="${doctors.entrySet()}" var="d">
                                        <option value=${d.getKey()}>${d.getValue()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="chooseEmployee" id="chooseStaff">
                                <label for="staff" class="col-sm-2 control-label">Staff</label>
                                <select class="form-control" id="staff" name="staff">
                                    <c:forEach items="${staff.entrySet()}" var="s">
                                        <option value=${s.getKey()}>${s.getValue()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="addPermission()">Add</button>
                        </div>
                    </div>
                </div>
            </div>
            <hr>
            <div class="row">
                <h2>Visitation Records</h2>
            </div>
        </div>
        <input type="hidden" id="defaultDoctor" value="<c:out value="${p.getDefaultDoctor().getUsername()}"/>">
    </jsp:attribute>
    <jsp:attribute name="script">
        <script type="text/javascript">
            var trackChanges = {patient: 0, add : [], remove : []};
            function removePermission(cur, employee) {
                if ($("#defaultDoctor").val() == employee) {
                    alert("Cannot remove default doctor");
                    return;
                }
                var addIndex = trackChanges.add.indexOf(employee);
                var removeIndex = trackChanges.remove.indexOf(employee);
                if (removeIndex > -1) {
                    var correspondingRow = $(cur).parent().prev().children().first();
                    correspondingRow.replaceWith(correspondingRow.text());
                    $(cur).html('&times;');
                    $(cur).parent().parent().css("color", "");
                    trackChanges.remove.splice(removeIndex,1);
                } else if (addIndex > -1) {
                    $(cur).parent().parent().remove();
                    trackChanges.add.splice(addIndex,1);
                } else {
                    $(cur).parent().prev().wrapInner('<strike>');
                    $(cur).html('UNDO');
                    $(cur).parent().parent().css("color", "red");
                    trackChanges.remove.push(employee);
                }
//                console.log(JSON.stringify(trackChanges));
            }
            function addPermission() {
                var role = ($("#role").val() === "Doctor") ? "doctor" : "staff";
                var username = $("#" + role).val();
                var name = $("#" + role).find(":selected").text();
                var exists = false;
                $("#permissionsTable > tbody > tr").each(function(){
                    if ($(this).attr('id') === username) {
                        exists = true;
                        return;
                    }
                });
                if (exists) {
                    alert("Already added");
                    return;
                }
                $("#permissionsTable")
                    .append($('<tr>')
                        .append($('<td>')
                            .append(name)
                        )
                        .append($('<td>')
                            .append('<button type="button" class="close" aria-hidden="true" onclick="removePermission(this, ' + username + ')">&times;</button></span></a>')
                        ).css("color", "green")
                    );
                trackChanges.add.push(parseInt(username));
            }
            $("#role").change(function(){
                $(".chooseEmployee").hide();
                switch (this.value) {
                    case "Doctor" :
                        $("#chooseDoctor").show();
                        break;
                    case "Staff" :
                        $("#chooseStaff").show();
                        break;
                }
            }).change();
            function sendAjax(patientUsername) {
                trackChanges.patient = patientUsername;
                console.log(JSON.stringify(trackChanges));
                $.ajax({
                    url: "UpdatePermissionsServlet",
                    dataType: 'json',
                    data: trackChanges,
                    success: function () {
                        trackChanges = {patient: 0, add : [], remove : []};
                        $("#permissionsTable > tbody > tr").each(function(){
                            if ($(this).css("color") === 'rgb(0, 128, 0)') { // green
                                $(this).css("color","");
                            } else if ($(this).css("color") === 'rgb(255, 0, 0)') { // red
                                $(this).remove();
                            }
                        });
                        $("#permissionsTableDiv").prepend('<div class="alert alert-success">Updated database</div>');
                    },
                    error: function () {
                        $("#permissionsTableDiv").prepend('<div class="alert alert-danger">Failed to update database</div>');
                    }
                });
                window.setTimeout(function() {
                    $(".alert").fadeTo(500, 0).slideUp(500, function(){
                        $(this).remove(); 
                    });
                }, 2500);
            }
        </script>
    </jsp:attribute>
</t:template>