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
        Staff Assignment
    </jsp:attribute>
    <jsp:attribute name="content">
        <div class="container-fluid">
            <div class="row">
                <h2>Staff Assignment</h2>
                <div class="table-responsive col-md-8" id="assignmentsTableDiv">
                    <table class="table table-striped table-hover" id="assignmentsTable">
                        <c:forEach items="${assignedStaff.entrySet()}" var="s">
                            <tr id=${s.getKey()}>
                                <td><c:out value="${s.getValue()}"/></td>
                                <td>
                                    <button type="button" class="close" aria-hidden="true" onclick="removeAssignment(this, <c:out value="${s.getKey()}"/>)">
                                        &times;
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="form-group">
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-plus"></span></button>
                        <button type="button" class="btn btn-primary" onclick="sendAjax()">Save</button>
                    </div>
                </div>
            </div>
            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">Assign Staff</h4>
                        </div>
                        <div class="modal-body">
                            <div class="input-group" id="chooseStaff">
                                <span class="input-group-addon">Staff</span>
                                <select class="form-control" id="staff" name="staff">
                                    <c:forEach items="${allStaff.entrySet()}" var="s">
                                        <option value=${s.getKey()}>${s.getValue()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="addAssignment()">Assign</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
    <jsp:attribute name="script">
        <script type="text/javascript">
            var trackChanges = {add : [], remove : []};
            function removeAssignment(cur, staff) {
                var addIndex = trackChanges.add.indexOf(staff);
                var removeIndex = trackChanges.remove.indexOf(staff);
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
                    trackChanges.remove.push(staff);
                }
//                console.log(JSON.stringify(trackChanges));
            }
            function addAssignment() {
                var username = $("#staff").val();
                var name = $("#staff").find(":selected").text();
                var exists = false;
                $("#assignmentsTable > tbody > tr").each(function(){
                    if ($(this).attr('id') === username) {
                        exists = true;
                        return;
                    }
                });
                if (exists) {
                    alert("Already added");
                    return;
                }
                $("#assignmentsTable")
                    .append($('<tr id=' + username + '>')
                        .append($('<td>')
                            .append(name)
                        )
                        .append($('<td>')
                            .append('<button type="button" class="close" aria-hidden="true" onclick="removeAssignment(this, ' + username + ')">&times;</button></span></a>')
                        ).css("color", "green")
                    );
                trackChanges.add.push(parseInt(username));
            }
            function sendAjax() {
//                console.log(JSON.stringify(trackChanges));
                $.ajax({
                    url: "UpdateAssignmentsServlet",
                    dataType: 'json',
                    data: trackChanges,
                    success: function () {
                        trackChanges = {patient: 0, add : [], remove : []};
                        $("#assignmentsTable > tbody > tr").each(function(){
                            if ($(this).css("color") === 'rgb(0, 128, 0)') { // green
                                $(this).css("color","");
                            } else if ($(this).css("color") === 'rgb(255, 0, 0)') { // red
                                $(this).remove();
                            }
                        });
                        $("#assignmentsTableDiv").prepend('<div class="alert alert-success">Updated database</div>');
                    },
                    error: function () {
                        $("#assignmentsTableDiv").prepend('<div class="alert alert-danger">Failed to update database</div>');
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