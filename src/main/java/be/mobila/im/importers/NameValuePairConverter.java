package be.mobila.im.importers;

import be.mobila.im.importers.exceptions.ConverterException;
import be.mobila.im.models.Insurance;
import be.mobila.im.utils.NameValuePair;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 1, 2010
 * Time: 5:31:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NameValuePairConverter {
  String STRING = "String";
  String BOOLEAN = "Boolean";
  String NUMERIC = "Numeric";
  String DATE = "Date";
  
  String FORMULA = "Formula";

  String COMMENT = "comment";
  String VARIABLE = "variable";
  String PROPERTY = "property";
  public void init(Insurance insurance) throws ConverterException, IOException;
  public List<NameValuePair> interpret() throws ConverterException;
  public void read(Insurance insurance) throws IOException, InvalidFormatException, ConverterException;
  public void writeXml(List<NameValuePair> properties, Insurance insurance) throws IOException;
}
