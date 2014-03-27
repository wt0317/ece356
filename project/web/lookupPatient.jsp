<%-- 
    Document   : lookupPatients
    Created on : Mar 26, 2014, 4:00:56 PM
    Author     : Wilson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template>
    <jsp:attribute name="pagetitle">
        Lookup Patient
    </jsp:attribute>
    <jsp:attribute name="style">
        <link rel="stylesheet" href="css/watable.css"></link>
        <link rel='stylesheet' href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"/>
        <script src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/jquery.watable.js"></script>
        <script type="text/javascript">
            var rows = [];
            function appendRow(json) {
                json.doctor = json.defaultDoctor.name;
                delete json.defaultDoctor;
//                $("#myDiv").text(JSON.stringify(json));
                rows.push(json);
            }
            function makeTable() {

                //An example with all options.
                 var waTable = $('#patientsTable').WATable({
                    pageSize: 8,                //Initial pagesize
                    filter: true,               //Show filter fields
                    sortEmptyLast:true,         //Empty values will be shown last
                    pageSizes: [],  //Set custom pageSizes. Leave empty array to hide button.
                    hidePagerOnEmpty: true,     //Removes the pager if data is empty.
                    preFill: true,              //Initially fills the table with empty rows (as many as the pagesize).
                    types: {                    //Following are some specific properties related to the data types
                        string: {
                            //filterTooltip: "Giggedi..."    //What to say in tooltip when hoovering filter fields. Set false to remove.
                            placeHolder: "Type here..."    //What to say in placeholder filter fields. Set false for empty.
                        },
                        number: {
                            decimals: 1   //Sets decimal precision for float types
                        }
                    }
                }).data('WATable');  //This step reaches into the html data property to get the actual WATable object. Important if you want a reference to it as we want here.

                var data = getData();
                waTable.setData(data);  //Sets the data.
            }

            //Generates some data. This step is of course normally done by your web server.
            function getData() {

                //First define the columns
                var cols = {
                    name: {
                        index: 1, //The order this column should appear in the table
                        type: "string", //The type. Possible are string, number, bool, date(in milliseconds).
                        friendly: "Name",  //Name that will be used in header. Can also be any html as shown here.
                        format: "<a href='#' class='userId' target='_blank'>{0}</a>",  //Used to format the data anything you want. Use {0} as placeholder for the actual data.
                        sortOrder: "asc" //Data will initially be sorted by this column. Possible are "asc" or "desc"
                    },
                    healthCard: {
                        index: 2,
                        type: "string",
                        friendly: "Health Card"
                    },
                    sin: {
                        index: 3,
                        type: "string",
                        friendly: "Social Insurance Number"
                    },
                    numOfVisits: {
                        index: 4,
                        type: "number",
                        friendly: "Number of Visits"
                    },
                    doctor: {
                        index: 5,
                        type: "string",
                        friendly: "Default Doctor",
                        format: "<a href='#' class='userId' target='_blank'>{0}</a>"
                    },
                    currentHealth: {
                        index: 6,
                        type: "string",
                        friendly: "Current Health"
                    },
                    comment: {
                        index: 7,
                        type: "string", //Don't forget dates are expressed in milliseconds
                        friendly: "Comment"
                    }
                };

                //Create the returning object. Besides cols and rows, you can also pass any other object you would need later on.
                var data = {
                    cols: cols,
                    rows: rows
                };

                return data;
            }
        </script>
    </jsp:attribute>
    <jsp:attribute name="pagetitle"> 
     Lookup Patient
    </jsp:attribute>
    <jsp:attribute name="onload"> 
        <c:forEach items="${listPatients}" var="p">
            appendRow(<c:out value="${p.toJSON()}" />);
        </c:forEach>
        makeTable();
    </jsp:attribute>

    <jsp:attribute name="content">  
        <div id="patientsTable" style="width:auto"></div>
    </jsp:attribute>
</t:template>
