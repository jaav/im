package be.mobila.im.importers;

import be.mobila.im.importers.exceptions.ConverterException;
import be.mobila.im.models.Insurance;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 1, 2010
 * Time: 5:31:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ValueMatrixConverter {
  String STRING = "String";
  String BOOLEAN = "Boolean";
  String NUMERIC = "Numeric";
  String DATE = "Date";
  
  String FORMULA = "Formula";

  String COMMENT = "comment";
  String VARIABLE = "variable";
  String PROPERTY = "property";
  public void init(Insurance insurance) throws ConverterException, IOException;
  public void read(Insurance insurance) throws IOException, InvalidFormatException, ConverterException;
  public void write(Insurance insurance, Workbook workbook) throws IOException;
}
