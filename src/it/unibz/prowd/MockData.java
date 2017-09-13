//package it.unibz.prowd;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by e7250 on 6/19/2017.
// */
//public class MockData {
//
//  public static Profile createProfile(Profile profile1){
//
//    String profileName = "Profile1";
//    String profileDescr = "This is a stub description";
//
//    String classCode = "Q5";
//    String className = "Human";
//
//    WikidataClass class1 = new WikidataClass(classCode);
//
//    Attribute facet1Attr = new Attribute("P1");
//    Facet facet1 = new Facet(facet1Attr);
//    Value facet1Attr1 = new NormalValue("Italy", "Q11", facet1);
//    Value facet1Attr2 = new NormalValue("France", "Q12", facet1);
//    Value facet1Attr3 = new NormalValue("Germany", "Q13", facet1);
//
//    Value facet1any = new AnyValue(facet1);
//    Value facet1other = new OtherValue(facet1);
//
//    List<Value> valueList1 = new ArrayList<Value>();
//
//    valueList1.add(facet1Attr1);
//    valueList1.add(facet1Attr2);
//    valueList1.add(facet1Attr3);
//    valueList1.add(facet1any);
//    valueList1.add(facet1other);
//
//    facet1.assignValues(valueList1);
//
//    Attribute facet2Attr = new Attribute("P2");
//    Facet facet2 = new Facet(facet2Attr);
//    Value facet2Attr1 = new NormalValue("Lawyer", "Q14", facet2);
//    Value facet2Attr2 = new NormalValue("Actor", "Q15", facet2);
//    Value facet2any = new AnyValue(facet2);
//    Value facet2other = new OtherValue(facet2);
//    List<Value> valueList2 = new ArrayList<Value>();
//    valueList2.add(facet2Attr1);
//    valueList2.add(facet2Attr2);
//    valueList2.add(facet2any);
//    valueList2.add(facet2other);
//    facet2.assignValues(valueList2);
//
//    List<Facet> classFacets = new ArrayList<Facet>();
//    classFacets.add(facet1);
//    classFacets.add(facet2);
//
//    List<Attribute> classAttrs = new ArrayList<Attribute>();
//    Attribute classAttr1 = new Attribute("P36");
//    Attribute classAttr2 = new Attribute("P60");
//    classAttrs.add(classAttr1);
//    classAttrs.add(classAttr2);
//
//    profile1 = new Profile(profileName, profileDescr, class1, classAttrs, classFacets);
//
//    System.out.println("Profile successfully created.");
//    System.out.println("trying toString the profile \n" + profile1.toString());
//
//    return profile1;
//  }
//
//}
