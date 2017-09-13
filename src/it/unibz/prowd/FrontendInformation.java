package it.unibz.prowd;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by e7250 on 7/25/2017.
 */
public class FrontendInformation extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println("-------- PostgreSQL " + "JDBC Connection Frontendinfo ------------");
    Connection connection = new DatabaseAPI().getConnection();
    Statement statement = null;
    ResultSet rs = null;


    //----------------------------
    Enumeration<String> reqData = request.getParameterNames();
    String profileName = request.getParameter("name");
    String query = "select * from attrxprofile where name='"+profileName+"'";
    int attrCount = 0;
    int total = 0;
    ArrayList<String> attributeList = new ArrayList<>();
    ArrayList<String> attributeNames = new ArrayList<>();
    ArrayList<String> attributeCompleteness = new ArrayList<>();
    ArrayList<String> columnNames = new ArrayList<>();
    ArrayList<String> valueCodes = new ArrayList<>();
    try {
      rs = statement.executeQuery(query);
      while(rs.next()){
        attributeList.add(rs.getString("attrcode"));
        attributeNames.add(rs.getString("attr"));
        attrCount++;
      }
      query = "select code from facetsxprofile where name='"+profileName+"'";
      rs = statement.executeQuery(query);
      while(rs.next()){
        columnNames.add(rs.getString("code"));
      }
      while(reqData.hasMoreElements()) {
        String currElm = reqData.nextElement().toString();
        System.out.println("request.getparam currelm: " + request.getParameter(currElm));
        query = "select valuecode from valuesxfacet where value='"+request.getParameter(currElm)+"'";
        rs = statement.executeQuery(query);
        while(rs.next()){
          valueCodes.add(rs.getString("valuecode"));
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("we have: " + attrCount + "attributes");
    System.out.println("we have attrs: " + attributeList); //to compute table percentage
    System.out.println("columnNames: " + columnNames);
    ArrayList<String> pSlots = new ArrayList<>();
    ArrayList<String> eSlots = new ArrayList<>();
    ArrayList<String> lSlots = new ArrayList<>();
    ArrayList<String> binVecSlots = new ArrayList<>();
    for(int i = 0; i <= attrCount; i++){
      pSlots.add("p"+(int)(((float)i/attrCount)*100));
      eSlots.add("e"+(int)(((float)i/attrCount)*100));
      lSlots.add("l"+(int)(((float)i/attrCount)*100));
      binVecSlots.add("bin_vec"+(int)(((float)i/attrCount)*100));
    }
    System.out.println("we have to look for: " + pSlots);
    System.out.println("the value codes are: " + valueCodes);

    query = "select ";
    for(String currPSlot : pSlots){
      query += currPSlot + ", ";
    }
    query = query.substring(0,query.length()-2);
    query += " from " + profileName+ " where ";

    int index = 0;
    for(String currColName : columnNames){
      query += currColName+"='" + valueCodes.get(index)+ "' and ";
      index++;
    }
    query = query.substring(0,query.length()-5);
    System.out.println("the query for the graph: " + query);
    ArrayList<String> graphData = new ArrayList<>();
    try {
      rs = statement.executeQuery(query);
      while (rs.next()) {
        for(int i=0;i<=attrCount;i++){
          graphData.add(rs.getString(i+1));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("graphData: " + graphData);

    query = "select ";
    for(String currESlot : eSlots){
      query += currESlot + ", ";
    }
    query = query.substring(0,query.length()-2);
    query += " from " + profileName+ " where ";

    index = 0;
    for(String currColName : columnNames){
      query += currColName+"='" + valueCodes.get(index)+ "' and ";
      index++;
    }
    query = query.substring(0,query.length()-5);
    System.out.println("the query for the entities: " + query);

    ArrayList<String> entity = new ArrayList<>();
    try {
      rs = statement.executeQuery(query);
      while (rs.next()) {
        for(int i=0;i<=attrCount;i++){
          entity.add(rs.getString(i+1));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("entity data: " + entity);


    //now labels

    query = "select ";
    for(String currLSlot : lSlots){
      query += currLSlot + ", ";
    }
    query = query.substring(0,query.length()-2);
    query += " from " + profileName+ " where ";

    index = 0;
    for(String currColName : columnNames){
      query += currColName+"='" + valueCodes.get(index)+ "' and ";
      index++;
    }
    query = query.substring(0,query.length()-5);
    System.out.println("the query for the labels: " + query);

    ArrayList<String> label = new ArrayList<>();
    try {
      rs = statement.executeQuery(query);
      while (rs.next()) {
        for(int i=0;i<=attrCount;i++){
          label.add(rs.getString(i+1));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("label data: " + label);

    //now binvecs

    query = "select ";
    for(String currBinVecSlot : binVecSlots){
      query += currBinVecSlot + ", ";
    }
    query = query.substring(0,query.length()-2);
    query += " from " + profileName+ " where ";

    index = 0;
    for(String currColName : columnNames){
      query += currColName+"='" + valueCodes.get(index)+ "' and ";
      index++;
    }
    query = query.substring(0,query.length()-5);
    System.out.println("the query for the binvecs: " + query);

    ArrayList<String> binvec = new ArrayList<>();
    try {
      rs = statement.executeQuery(query);
      while (rs.next()) {
        for(int i=0;i<=attrCount;i++){
          binvec.add(rs.getString(i+1));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    //------all this code repetition can be collapsed into one select *
    //refactor

    //get attribute completeness for table
    query = "select total, ";
    for(String currAtrCount : attributeList){
      query += currAtrCount + "count, ";
    }
    query = query.substring(0,query.length()-2);
    query += " from " + profileName+ " where ";

    index = 0;
    for(String currColName : columnNames){
      query += currColName+"='" + valueCodes.get(index)+ "' and ";
      index++;
    }
    query = query.substring(0,query.length()-5);
    System.out.println("the query for the attr %: " + query);

    try {
      rs = statement.executeQuery(query);
      while (rs.next()) {
        for(String currAttr : attributeList){
          int currCount = rs.getInt(currAttr.toLowerCase()+"count");
          System.out.println("the attribute coutn got: " + currCount);
          float currCompleteness = ((float)currCount/rs.getInt("total"));
          attributeCompleteness.add(currCompleteness+"");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("attributeCompleteness data: " + attributeCompleteness);

    System.out.println("attribute names: " + attributeNames);

    //select p0..p100 from humans where p21=female, p27=usa, p106=lawyer;
    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
    response.getWriter().write(graphData.toString());       // Write response body.
    response.getWriter().write("|");
    response.getWriter().write(entity.toString());
    response.getWriter().write("|");
    response.getWriter().write(label.toString());
    response.getWriter().write("|");
    response.getWriter().write(binvec.toString());
    response.getWriter().write("|");
    response.getWriter().write(attributeNames.toString());
    response.getWriter().write("|");
    response.getWriter().write(attributeCompleteness.toString());
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    System.out.println("we're here again");
    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
    response.getWriter().write("hello");       // Write response body.
  }
}