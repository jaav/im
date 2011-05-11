package be.mobila.im.flow.communicate.receivers;

import be.mobila.im.flow.communicate.parsers.RequestParser;
import be.mobila.im.flow.utils.InsuranceTranslator;
import be.mobila.im.models.*;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 15, 2010
 * Time: 3:08:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class LiaDataSaver extends AbstractDataSaver {
  static Logger logger = Logger.getLogger(LiaDataSaver.class);

  public String calculateResult(Document requestData, ImRequest request){
    double rate;
    try{
      rate = Double.parseDouble(RequestParser.parseRate(requestData));
    } catch(Exception e){
      logger.error("The 'Rate' value could not be parsed");
      request.setLog("The 'Rate' value could not be parsed");
      request.merge();
      return null;
    }
    rate = normalizeRate(rate);

    Long capital;
    try{
      capital = Long.parseLong(RequestParser.parseCapital(requestData));
    } catch(Exception e){
      logger.error("The 'Capital' value could not be parsed");
      request.setLog("The 'Capital' value could not be parsed");
      request.merge();
      return null;
    }

    if(RequestFor.PRICING.equals(request.getRequestFor())){
      Double minRate = rate-0.001<0.001 ? 0.001 : rate-0.001;
      Double maxRate = rate+0.001>0.01 ? 0.01 : rate+0.001;
      if(minRate==0.001) maxRate=0.003;
      if(maxRate==0.01) minRate=0.008;
      List<LiaValue> liaValues = LiaValue.findLiaValuesForPricing(capital, minRate, maxRate).getResultList();
      if(!liaValues.isEmpty()) return writeResultFromList(liaValues, capital);
      else{
        logger.error("No value block could be found for a capital of "+capital+" and a rate of "+rate);
        request.setLog("No value block could be found for a capital of "+capital+" and a rate of "+rate);
        request.merge();
        return null;
      }
    }
    else if(request.getRequestFor() != null){
      String insuranceSubType = RequestParser.parseInsuranceSubType(requestData);
      if(insuranceSubType != null){
        LiaValue liaValue = null;
        try{
          liaValue = LiaValue.findLiaValueForProposal(capital, rate, LiaInsuranceSubType.valueOf(insuranceSubType)).getSingleResult();
          return writeResultFromValue(liaValue, capital);
        }catch(Exception e){
          logger.error("The 'InsuranceSubType' value could not be parsed");
          request.setLog("The 'InsuranceSubType' value could not be parsed");
          request.merge();
          return null;
        }
      }
      else{
        logger.error("No value block could be found for a capital of "+capital+" and a rate of "+rate);
        request.setLog("No value block could be found for a capital of "+capital+" and a rate of "+rate);
        request.merge();
        return null;
      }
    }
    else
      logger.error("The 'RequestFor' value could not be matched to a predefined one");
      request.setLog("The 'InsuranceSubType' value could not be matched to a predefined one");
      request.merge();
      return null;
  }

  @Override
  public InsuranceValue getValueId(Document document) {
    try{
      return LiaValue.findLiaValueForProposal(
        Long.parseLong(RequestParser.parseCapital(document)),
        normalizeRate(Double.parseDouble(RequestParser.parseRate(document))),
        LiaInsuranceSubType.valueOf(RequestParser.parseInsuranceSubType(document))).getSingleResult();
    } catch(Exception e){
      logger.error(e.getMessage());
      return null;
    }
  }

  /*private Double findMinRate(Double rate){
    Double test = rate-0.00025 >0 ? rate-0.00025 : 0;
    test = rate+0.00025;
    LiaValue liaValue = LiaValue.findLiaValuesByRateBetween(rate-0.00025, rate+0.00025).getSingleResult();
    return liaValue.getRate() - 0.001;
  }*/

  private double normalizeRate(double rate){
    double result = rate * 100;
    result = Math.round(result);
    result = result / 10000;
    return result;
  }

  private String writeResultFromList(List<LiaValue> liaValues, Long capital) {
    if(liaValues.isEmpty() || capital<=0) return "";
    Document document = DocumentHelper.createDocument();
    Element root = document.addElement( "RequestResult" );
    NumberFormat capitalFormatter = NumberFormat.getInstance(new Locale("nl","BE"));

    try{
      Element capitalElement = root.addElement("capital").addText(capitalFormatter.format(capital));
      Element row1 = root.addElement("tr").addAttribute("class","headerRow");
      row1.addElement("td").addAttribute("class","insuranceTypeCell").addText("Your rate:");
      double counter = 0;
      Element[] rows = new Element[6];
      NumberFormat formatter = new DecimalFormat("#0.00%");
      for (LiaValue liaValue : liaValues) {
        if(counter<5){
          row1.addElement("td").addText(formatter.format(liaValue.getRate()));
        }
        int rowNumber = (int)Math.floor(counter/5);
        double test = counter;
        test = counter%5;
        if(counter%5==0){
          rows[rowNumber] = root.addElement("tr");
          rows[rowNumber].addElement("td").addAttribute("class","headerCell").addText(InsuranceTranslator.getLiaSubType(liaValue.getInsuranceSubType()));
        }
        rows[rowNumber].addElement("td").addText(formatter.format(liaValue.getProposal()));
        counter++;
      }
    }catch(Exception e){
      logger.error(e.getMessage());
      return "";
    }


    StringWriter sw = new StringWriter();
    XMLWriter writer = new XMLWriter(sw);
    try {
      writer.write( document );
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    return sw.toString();
  }

  private String writeResultFromValue(LiaValue liaValue, Long capital) {
    if(liaValue==null || capital<=0) return "";

    Document document = DocumentHelper.createDocument();
    Element root = document.addElement("RequestResult");
    NumberFormat percentFormatter = new DecimalFormat("#0.00%");
    NumberFormat capitalFormatter = NumberFormat.getInstance(new Locale("nl","BE"));
    try{
      Element capitalElement = root.addElement("capital").addText(capitalFormatter.format(capital));
      Element rateElement = root.addElement("rate").addText(percentFormatter.format(liaValue.getRate()));
      Element proposalElement = root.addElement("proposal").addText(percentFormatter.format(liaValue.getProposal()));
      StringWriter sw = new StringWriter();
      XMLWriter writer = new XMLWriter(sw);
      try {
        writer.write( document );
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
      return sw.toString();
    }
    catch(Exception e){
      logger.error(e.getMessage());
      return "";
    }
  }
}
