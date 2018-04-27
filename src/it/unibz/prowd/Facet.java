package it.unibz.prowd;

import java.util.List;

/**
 * Created by e7250 on 6/19/2017.
 * The facet class is the dimension along which we analyse the entities for their completeness.
 * Each facet has values which the user can select. For example, a facet 'Nationality' can have values 'Germany', 'USA'
 */

public class Facet {
  Attribute attr; //facet code
  String name;
  List<Value> values;

  public Facet(Attribute attr, String name){
    this.name = name;
    this.attr = attr;
  }

  public void assignValues(List<Value> values){
    this.values = values;
  }

  public String toString(){
    return attr + " " + name;

  }
  //This function cleans whitespace characters for database purposes
  public String getNameforDB(){
    String returnString="";

    if(this.name==""){
      returnString=this.attr.code;
    } else{
      returnString = this.name.replace(" ", "");
    }
    return returnString;
  }

}
