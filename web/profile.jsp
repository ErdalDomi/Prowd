<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="javax.xml.transform.Result" %>
<%@ page import="it.unibz.prowd.Profile" %>
<%@ page import="it.unibz.prowd.DatabaseAPI" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: e7250
  Date: 7/24/2017
  Time: 10:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="css/semantic.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="javascript/semantic.js"></script>
    <script src="javascript/jquery.jqplot.js"></script>
    <link rel="stylesheet" href="css/profileStyling.css">
    <script src="javascript/profile.js"></script>



    <script src="javascript/jqplot.barRenderer.js"></script>
    <%--<script src="javascript/jqplot.pierenderer.js"></script>--%>
    <script src="javascript/jqplot.categoryAxisRenderer.js"></script>
    <script src="javascript/jqplot.pointLabels.js"></script>


    <link href="css/jquery.jqplot.css"></link>
</head>
<body>

<div id="wrapper">

    <div id="pageTitle">
        <h1 id="${profile}"><c:out value="${profile}"/></h1>
        <!-- <p style="display: inline;">Database connection: </p><p id="dbConnectionStatus" style="display: inline; color: red;"></p> -->
    </div>


    <%


        Connection connection = new DatabaseAPI().getConnection();
        Statement statement = null;
        ResultSet rs = null;

        statement = connection.createStatement();
        // sql query to retrieve values from the specified table.

        String QueryString = "SELECT * from facetsxprofile where name='"+request.getParameter("id")+"'";
        System.out.println("troublesome string: " + QueryString);
        ResultSet rsLabel;
        rs = statement.executeQuery(QueryString);
        Map<String,ArrayList<String>> facetValue = new HashMap<>();
        ArrayList<String> facetList = new ArrayList<>();
        while(rs.next()){
          facetList.add(rs.getString("facets"));
        }

        for(String currFacet : facetList){
            rs = statement.executeQuery("select * from valuesxfacet where facet ='"
                    + currFacet+"'");
            ArrayList<String> valueList = new ArrayList<>();
            while (rs.next()) {
              valueList.add(rs.getString("value"));
            }
            facetValue.put(currFacet,valueList);
        }

        System.out.println("facetList: " + facetList);
        //System.out.println("valueList: " + valueList);
        System.out.println("map: " + facetValue);


    %>




    <div id="facetPanel">

        <h3>Facets</h3>

        <div class="ui form" id="myForm" action="/info" method="post">
            <%
                for(String currFacet : facetList){
            %>
            <div class="grouped fields">
                <label><%=currFacet%></label>
                <%
                    for(String currValue : facetValue.get(currFacet)){
                %>
                <div class="field">
                    <div class="ui radio checkbox">
                        <input type="radio" name="<%=currFacet%>" value="<%=currValue%>">
                        <label><%=currValue%></label>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
            <%
                }
            %>
            <%--<button class="ui button" type="submit" >Inspect</button>--%>
        </div>

    </div>

    <div id="rightPanel">

        <div id="chart" style="height:300px; width:500px;"></div>

        <button class="huge primary ui button" id="bigButton" onclick="$('#myForm').submit();">Inspect</button>
        <div style="display:inline;">
            <h4 style="display:inline;">Select entities with:</h4>
            <select class="ui dropdown" id="myDropdown">
                <option value="">Existing attributes</option>
            </select>
        </div>

        <table id="responseTable">

        </table>

    </div>
</div>
</body>
</html>
