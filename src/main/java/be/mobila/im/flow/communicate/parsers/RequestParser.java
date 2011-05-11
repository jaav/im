package be.mobila.im.flow.communicate.parsers;

import be.mobila.im.models.Person;
import be.mobila.im.models.ResponseMode;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 16, 2010
 * Time: 10:57:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class RequestParser {
  private static final DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy hh:mm");

  public static Person parsePerson(Document requestData){
    Person person = new Person();
    List list = requestData.selectNodes("//field/name");
    for (Iterator iter = list.iterator(); iter.hasNext(); ) {
      Node name = (Node)iter.next();
      String nameText = name.getText();
      if("Name".equals(nameText)) person.setName(name.getParent().selectSingleNode("value").getText());
      if("Firstnames".equals(nameText)) person.setFirstname(name.getParent().selectSingleNode("value").getText());
      if("Address".equals(nameText)) person.setAddress(name.getParent().selectSingleNode("value").getText());
      if("PostalCode".equals(nameText)) person.setPostalcode(name.getParent().selectSingleNode("value").getText());
      if("Place".equals(nameText)) person.setCity(name.getParent().selectSingleNode("value").getText());
      if("Nationality".equals(nameText)) person.setNationality(name.getParent().selectSingleNode("value").getText());
      if("Gender".equals(nameText)) person.setGender(name.getParent().selectSingleNode("value").getText());
      try {
        if("PlaceAndDateOfBirth".equals(nameText))
        person.setPlaceofbirth(name.getParent().selectSingleNode("value").getText());
          //person.setBirthdate(fmt.parse(name.getParent().selectSingleNode("value").getText()));
      } catch (Exception e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
    }
    return person;
  }

  private static Document getDocument(File xmlFile) throws DocumentException {
    SAXReader reader = new SAXReader();
    Document document = reader.read(xmlFile);
    return document;
  }

  public static String parseEmailAddress(File xmlFile){
    String emailAddress = null;
    try {
      Document requestData = getDocument(xmlFile);
      List list = requestData.selectNodes("//field/name");
      for (Iterator iter = list.iterator(); iter.hasNext(); ) {
        Node name = (Node)iter.next();
        String nameText = name.getText();
        if("Email".equals(nameText)) emailAddress = name.getParent().selectSingleNode("value").getText();
      }
    }
    catch (DocumentException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    return emailAddress;
  }

  public static String parseFaxNumber(File xmlFile){
    String emailAddress = null;
    try {
      Document requestData = getDocument(xmlFile);
      List list = requestData.selectNodes("//field/name");
      for (Iterator iter = list.iterator(); iter.hasNext(); ) {
        Node name = (Node)iter.next();
        String nameText = name.getText();
        if("Fax".equals(nameText)) emailAddress = name.getParent().selectSingleNode("value").getText();
      }
    }
    catch (DocumentException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    return emailAddress;
  }

  public static String parseUserIdentifier(Document requestData){
    Node emailNode = requestData.selectSingleNode("//bb");
    return emailNode.getText();
  }

  public static String parseUserIdentifier(File xmlFile){
    try {
      Document requestData = getDocument(xmlFile);
      return parseUserIdentifier(requestData);
    } catch (DocumentException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    return null;
  }

  public static String parseInsuranceType(Document requestData){
    Node emailNode = requestData.selectSingleNode("//insuranceType");
    return emailNode.getText();
  }

  public static String parseInsuranceType(File xmlFile){
    try {
      Document requestData = getDocument(xmlFile);
      return parseInsuranceType(requestData);
    } catch (DocumentException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    return null;
  }

  public static Map<String, ResponseMode> parseResponseModes(Document requestData){
    Map<String, ResponseMode> responseModes = new HashMap<String, ResponseMode>();
    List list = requestData.selectNodes("//field/name");
    for (Iterator iter = list.iterator(); iter.hasNext(); ) {
      Node name = (Node)iter.next();
      String nameText = name.getText();
      if("mail1".equals(nameText)) responseModes.put(name.getParent().selectSingleNode("value").getText(), ResponseMode.MAIL_WA);
      if("mail2".equals(nameText)) responseModes.put(name.getParent().selectSingleNode("value").getText(), ResponseMode.MAIL_WA);
      if("fax1".equals(nameText)) responseModes.put(name.getParent().selectSingleNode("value").getText(), ResponseMode.FAX);
    }
    return responseModes;
  }


  public static String parseCapital(Document requestData){
    String capital = null;
    List list = requestData.selectNodes("//field/name");
    for (Iterator iter = list.iterator(); iter.hasNext(); ) {
      Node name = (Node)iter.next();
      String nameText = name.getText();
      if("Capital".equals(nameText)) capital = name.getParent().selectSingleNode("value").getText();
    }
    return capital;
  }


  public static String parseInsuranceSubType(Document requestData){
    String capital = null;
    List list = requestData.selectNodes("//field/name");
    for (Iterator iter = list.iterator(); iter.hasNext(); ) {
      Node name = (Node)iter.next();
      String nameText = name.getText();
      if("InsuranceSubType".equals(nameText)) capital = name.getParent().selectSingleNode("value").getText();
    }
    return capital;
  }


  public static String parseRate(Document requestData){
    String rate = null;
    List list = requestData.selectNodes("//field/name");
    for (Iterator iter = list.iterator(); iter.hasNext(); ) {
      Node name = (Node)iter.next();
      String nameText = name.getText();
      if("Rate".equals(nameText)) rate = name.getParent().selectSingleNode("value").getText();
    }
    return rate;
  }

  public static String parseRequestFor(Document requestData){
    String rate = null;
    List list = requestData.selectNodes("//field/name");
    for (Iterator iter = list.iterator(); iter.hasNext(); ) {
      Node name = (Node)iter.next();
      String nameText = name.getText();
      if("RequestFor".equals(nameText)) rate = name.getParent().selectSingleNode("value").getText();
    }
    return rate;
  }
}
