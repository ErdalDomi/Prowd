package it.unibz.prowd;

/**
 * Created by e7250 on 6/19/2017.
 */
public class WikidataClass {
  String name;
  String code;

  public WikidataClass(String classCode){
    this.code = classCode;
  }

  public String toString(){
    return this.code + " " + this.name;
  }

  public void setName(String name){
    this.name = name;
  }
}
