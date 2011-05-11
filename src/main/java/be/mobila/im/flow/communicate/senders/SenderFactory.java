package be.mobila.im.flow.communicate.senders;

import be.mobila.im.flow.utils.InsuranceTranslator;
import be.mobila.im.models.Response;
import be.mobila.im.models.Status;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 16, 2010
 * Time: 9:38:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class SenderFactory {
  static Logger logger = Logger.getLogger(SenderFactory.class);

  private Map<String,Sender> senders;

  public void setSenders(Map<String, Sender> senders) {
    this.senders = senders;
  }

  //The one and only very important convention used here is that the id's given to the sender objects in the applicationContext.xml file
  // is equal to it's corresponding responseMode value (BB_MAIL, MAIL, MAIL_WA, FAX, ...)
  public void sendMessages(){
    List<Response> responses = Response.findResponsesByStatus(Status.INITIATED).getResultList();
    for (Response response : responses) {
      Sender sender = senders.get(InsuranceTranslator.getResponseMode(response.getResponseMode()));
      boolean success;
      if(sender != null)
        success = sender.send(response);
      else success = false;
      response = Response.findResponse(response.getId());
      if(success) response.setStatus(Status.DONE_SUCCESS);
      else{
        logger.error("Setting the status of this message to \"DONE_FAIL\"");
        response.setStatus(Status.DONE_FAIL);
      }
      response.merge();
    }
  }
}
