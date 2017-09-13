<%@ page import="it.unibz.prowd.Profile" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="it.unibz.prowd.DatabaseAPI" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: Erdal
  Date: 6/19/2017
  Time: 11:30 AM
  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>ProWD</title>
  <link rel="stylesheet" href="css/semantic.css">
  <link rel="stylesheet" href="css/style.css">
  <!-- Latest compiled and minified CSS -->
  <%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">--%>
  <!-- Latest compiled and minified JavaScript -->
  <%--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>--%>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="javascript/semantic.js"></script>
  <script src="javascript/dynamicFields.js"></script>
</head>
<body>

<div id="titleDiv">
  <h1>ProWD</h1>
</div>

<div id="flexContainer">

  <div id="createDiv">
    <h3 style="text-align:center;">Create profiles...</h3>

    <form class="ui form" id="createForm" action="start" method="GET">


      <div class="inline field">
        <label>Name</label>
        <input type="text" name="profile-name" placeholder="Profile name" id="regularright">
      </div>

      <div class="inline field">
        <label>Description</label>
        <input type="text" name="profile-description" placeholder="Description" id="regularright">
      </div>

      <div class="inline field">
        <label>Class</label>
        <input type="text" name="profile-class" placeholder="Class" id="regularright">
      </div>

      <div class="inline field">
        <label>Facet </label>
        <input type="text" name="profile-facet1" placeholder="Facet 1" id="regularright">
      </div>

      <div id="f1values">
        <div class="inline field">
          <label>Facet value</label>
          <input type="text" name="profile-f1v1" placeholder="Facet value 1" id="right">
        </div>
        <button style="margin-bottom: 5px;" class="circular ui icon green button valuebutton" id="f1v2" >Add value</button>
      </div>

      <button style="margin-bottom: 5px;" class="circular ui icon green button facetbutton" id="f2">Add facet</button>

      <div class="inline field">
        <label>Attribute</label>
        <input type="text" name="attribute1" placeholder="Attribute1" id="regularright">
      </div>

      <button style="margin-bottom: 5px;" class="circular ui icon green button attributebutton" type="button" onclick="addAtt(event); ">Add attribute</button>

      <br>
      <button style="margin-top: 25px; margin-left: 36%;" class="ui button" type="submit" onclick="alert('Once you click okay the server will start working and collecting data. If you want to browse profiles or create a new one, please open another tab.');">Prepare</button>


    </form>
  </div>


  <%

    Connection connection = new DatabaseAPI().getConnection();
    Statement statement = null;
    ResultSet rs = null;

      statement = connection.createStatement();
// sql query to retrieve values from the secified table.
      String QueryString = "SELECT * from profile";
      rs = statement.executeQuery(QueryString);
  %>





  <div id="viewDiv">
    <h3 style="text-align:center;">Recent profiles</h3>
    <ul>
      <%
        while (rs.next()) {
      %>
      <li><a href="${pageContext.request.contextPath}/profile?id=<%=rs.getString(1)%>"><%=rs.getString(1)%></a> -<%=rs.getString(2)%></li>
      <% } %>
    </ul>
  </div>
<%
  rs.close();
  statement.close();
  connection.close();
%>


</div>

</body>
</html>
