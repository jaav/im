package be.mobila.im.importers;

import be.mobila.im.models.Insurance;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 1, 2010
 * Time: 10:07:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Importer {
  static Logger logger = Logger.getLogger(Importer.class);

  private Map converters;

  public void setConverters(Map converters) {
    this.converters = converters;
  }

  public void convert(Insurance insurance){
    Converter converter = (Converter)converters.get(insurance.getConverter());
    converter.convert(insurance);
  }

  public void convert(){
    List<Insurance> insurances = Insurance.findAllInsurances();
    for (Insurance insurance : insurances) {
      convert(insurance);
    }
  }

  /*private Insurance getDefaultInsurance(){
    return Insurance.findInsurancesByInsuranceType(InsuranceType.LIFE).getSingleResult();
  }*/
}
