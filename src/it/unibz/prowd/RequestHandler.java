package it.unibz.prowd;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.jena.atlas.json.io.parser.JSONParser;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.tdb.TDBFactory;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by e7250 on 6/19/2017.
 */
public class RequestHandler extends HttpServlet {


  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    //--trying to get code names---------------------------------------------
//    String endpoint = "https://query.wikidata.org/sparql";
//    String query = "SELECT DISTINCT * WHERE {\n" +
//            "  <http://www.wikidata.org/entity/Q19675> <http://www.w3.org/2000/01/rdf-schema#label> ?label . \n" +
//            "  FILTER (langMatches( lang(?label), \"EN\" ) )  \n" +
//            "}";
//    String catQuery = "SELECT ?item  WHERE {\n" +
//            "  ?item <http://www.wikidata.org/prop/direct/P31> <http://www.wikidata.org/entity/Q146>.\n" +
//            "}";
//    QueryExecution qe;
//    ResultSet rs;
//    QuerySolution qs;
//    RDFNode node;

//    qe = QueryExecutionFactory.sparqlService(endpoint, query);//fire up query
//    rs = qe.execSelect();
//    qs = rs.next();
//    System.out.println(qs.get("label"));
    //--------------------------------------------------------------
    System.out.println("GET request on server.");
    Enumeration<String> reqData = request.getParameterNames();
    System.out.println("Received parameters.");

    String profileName="";
    String profileDescription="";
    WikidataClass profileClass = null;
    List<Attribute> profileAttributes = new ArrayList<Attribute>();
    PrintWriter out = response.getWriter();


    ///////------------
//    List<Facet> profileFacets = new ArrayList<Facet>();
//    List<List<Value>> profileValues = new ArrayList<>();
//    profileValues.add(new ArrayList<Value>());
//    Facet initialFacet = null;
//    int currFacetNr = 1;
    /////////-----------
    int totalFacets = 0;

    while(reqData.hasMoreElements()){ //first run: getting easy pickings and facet number
      String currElm = reqData.nextElement().toString();

      if(currElm.equals("profile-name")){
        profileName = request.getParameter(currElm);
        System.out.println("Fetched profile name: " + profileName);
      }

      if(currElm.equals("profile-description")){
        profileDescription = request.getParameter(currElm);
        System.out.println("Fetched profile description: " + profileDescription);
      }

      //here we use some Sparql query to get the label of the Q-code class label
      if(currElm.equals("profile-class")){
        profileClass = new WikidataClass(request.getParameter(currElm));
        System.out.println("Profile class created: " + profileClass);
//        query = "SELECT DISTINCT * WHERE {\n" +
//                "  <http://www.wikidata.org/entity/"+profileClass.code+"> <http://www.w3.org/2000/01/rdf-schema#label> ?label . \n" +
//                "  FILTER (langMatches( lang(?label), \"EN\" ) )  \n" +
//                "}";
//        System.out.println("debug: class label query: " + query);
//        qe = QueryExecutionFactory.sparqlService(endpoint, query);//fire up query
//        rs = qe.execSelect();
//        qs = rs.next();
        String profileClassName = getQlabel(profileClass.code);
        System.out.println("Got class name from API: " + profileClassName);
        profileClass.setName(profileClassName.substring(0,profileClassName.length()-3));
        System.out.println("Profile class name updated to: " + profileClass.name);
      }


      if(currElm.contains("attribute")){
        String currentAttributeCode = request.getParameter(currElm).toString();
        String currentAttributeName = getPropertyName(currentAttributeCode);
        System.out.println("Got an attribute code: " + currentAttributeCode + " matches with: " + currentAttributeName);
        Attribute currAttribute = new Attribute(currentAttributeCode);
        currAttribute.setName(currentAttributeName);
        profileAttributes.add(currAttribute);
        System.out.println("Attribute created, name set and added to profile attribute list.");
      }

      if(currElm.contains("facet")){//if its a facet
        totalFacets++;
      }
      //-----------------
//      if(currElm.length() > 10 && currElm.charAt(10) == 'v'){
//        System.out.println("This should be a facet value: " + request.getParameter(currElm));
//        System.out.println("This is the number we're looking for: " + currElm.charAt(9));
//        System.out.println("This is profilesValues.(nr) " + profileValues.get(currElm.charAt(9)-1));
//        if(profileValues.get(request.getParameter(currElm).charAt(1)) == null){
//          profileValues.add(new ArrayList<Value>());
//        }
//        if(Character.getNumericValue(currElm.charAt(9)) == currFacetNr){
//          profileValues.get(Character.getNumericValue(currElm.charAt(9)))
//                       .add(new Value(request.getParameter(currElm)));
//          System.out.println("profile values: " + profileValues);
//        }
//      }
      //------------
    }

    List<Facet> profileFacets = new ArrayList<>();
    List<List<Value>> profileValues = new ArrayList<>();

    for(int i=0; i<totalFacets; i++){
      //every for loop we add one Facet to the profileFacets list
      //and one Value list to the profileValues
      //this way we give the facets their attribute and make the lists ready to populate
      //for every facet, instantiate object with attribute code
      //html has 1,2,3 whlie i has 0,1,2 hence the i+1
      String currentFacetCode = request.getParameter("profile-facet"+(i+1));
      String currentFacetName = getPropertyName(currentFacetCode);
      profileFacets.add(new Facet(new Attribute(currentFacetCode), currentFacetName));
      //we need one list of values for every facet
      profileValues.add(new ArrayList<>());
    }
    System.out.println("Fetched facets from user input: " + profileFacets);
    //here we store how many values there are for each facet
    //dubious use -- delete on refactoring
    int[] valueCountArr = new int[totalFacets];

    for(int i=0; i<totalFacets;i++){ //we iterate the request items for each facet
      int currFacetValueCounter = 0;
      reqData = request.getParameterNames();
      while(reqData.hasMoreElements()){
        String currElm = reqData.nextElement().toString();
        //System.out.println("currElm in " + i + " iteration is " + currElm);
        //System.out.println("f"+(i+1)+"v");
        if(currElm.length() > 10 && currElm.contains("f"+(i+1)+"v")){
          //if its a facet value input field for the current facet
          //get the right faceet value list
          //instantiate a normal value from the parameter
          //insert it into list
          String currValue = request.getParameter(currElm);
          String currValueName = getQlabel(currValue);
          currValueName = currValueName.substring(0,currValueName.length()-3);
          Value valueToAdd = new Value(currValue, currValueName);
          valueToAdd.setFacet(profileFacets.get(i));
          profileValues.get(i).add(valueToAdd);
          currFacetValueCounter++;
        }
      }
      valueCountArr[i]=currFacetValueCounter; //dubious use
      Value anyValue = new Value("any","any");
      anyValue.setFacet(profileFacets.get(i));
      Value otherValue = new Value("other", "other");
      otherValue.setFacet(profileFacets.get(i));
      profileValues.get(i).add(anyValue);
      profileValues.get(i).add(otherValue);
      profileFacets.get(i).assignValues(profileValues.get(i));

      //line here to add otherValue and NormalValue -- done?

    }

    System.out.println("Facets and their values collected.");
    System.out.println("Facets: " + profileFacets);
    System.out.println("Values: " + profileValues);

    DatabaseAPI db = new DatabaseAPI();
    Profile profile1 = new Profile(profileName, profileDescription,
            profileClass,profileAttributes,profileFacets);
    profile1.createTable(db.getConnection());
    profile1.insertProfileInformation(db.getConnection());
    profile1.populateProfileTable();

    System.out.println("Total queries to run: " + profile1.totalQueries);

  }


  //to get the P-code labels we have to use a wikidata api endpoint
  //first we send a GET reqeuest to get a json file with the info we need
  //then we use a JSON library to get the value we want

  //example link: https://www.wikidata.org/w/api.php?action=wbgetentities&props=labels&ids=Q76&languages=en
  public String getPropertyName(String code) throws ServletException, IOException{

    String returnValue = "";
    URL url = new URL("https://www.wikidata.org/w/api.php?action=wbgetentities&ids="+code+"&languages=en&format=json");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("Content-Type", "application/json");
    int status = con.getResponseCode();
    BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer content = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      content.append(inputLine);
    }

    JSONObject jsonObj = null;

    try {
      jsonObj = new JSONObject(content.toString());
      returnValue = jsonObj.getJSONObject("entities")
              .getJSONObject(code)
              .getJSONObject("labels")
              .getJSONObject("en")
              .getString("value");

    } catch (JSONException e) {
      System.out.println("label not found for " + code);
      System.out.println("returning code");
      returnValue = code;
    }

    in.close();
    con.disconnect();

    return returnValue;
  }

  public String getQlabel(String code){
    String returnValue = "";
    String endpoint = "https://query.wikidata.org/sparql";
    QueryExecution qe;
    ResultSet rs;
    QuerySolution qs;
    String query = "";
    query = "SELECT DISTINCT * WHERE {\n" +
            "  <http://www.wikidata.org/entity/"+code+"> <http://www.w3.org/2000/01/rdf-schema#label> ?label . \n" +
            "  FILTER (langMatches( lang(?label), \"EN\" ) )  \n" +
            "}";
    qe = QueryExecutionFactory.sparqlService(endpoint, query);//fire up query
    rs = qe.execSelect();
    qs = rs.next();
    returnValue = qs.getLiteral("label").toString();

    return returnValue;
  }
}


