package it.unibz.prowd;

/**
 * Created by e7250 on 6/19/2017.
 * This class defines the attributes of the profile. These attributes determine the completeness of the
 * profiles, by our definition and approach. Complete means the attribute value exists in the entity wikidata page.
 */
public class Attribute {
  String name = "";
  String code;

  public Attribute(String code){
    this.code = code;
  }

  public String toString(){
    return this.name + " " + this.code + " ";
  }

  public void setName(String name){
    this.name = name;
  }

  //This function will clean whitespace characters so it plays well with the database
  public String getNameforDB(){
    String returnString="";

    if(this.name==""){
      returnString=this.code;
    } else{
      returnString = this.name.replace(" ", "");
      System.out.print("Attribute name changed into: " + returnString +" for database purposes.");
    }
    return returnString;
  }
}
