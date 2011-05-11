package be.mobila.im.flow.communicate.receivers;

import be.mobila.im.models.ImUser;
import be.mobila.im.models.Insurance;
import be.mobila.im.models.ImRequest;
import be.mobila.im.models.InsuranceValue;
import org.dom4j.Document;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 15, 2010
 * Time: 3:32:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DataSaver {
  public void save(Document requestData, ImRequest request);
  public InsuranceValue getValueId(Document document);
  public String calculateResult(Document requestData, ImRequest request);
}
