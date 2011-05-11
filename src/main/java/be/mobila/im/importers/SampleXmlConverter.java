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
 * Date: Dec 2, 2010
 * Time: 8:58:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class SampleXmlConverter extends AbstractXMLStorageConverter implements NameValuePairConverter, Converter {
  
  @Override
  public List<NameValuePair> interpret() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void read(Insurance insurance) throws IOException, InvalidFormatException, ConverterException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void writeXml(List<NameValuePair> properties, Insurance insurance) throws IOException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void convert(Insurance insurance) {
    try {
      init(insurance);
      read(insurance);
      List<NameValuePair> properties = interpret();
      writeXml(properties, insurance);
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (InvalidFormatException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (ConverterException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
  }
}
