package be.mobila.im.flow.communicate.receivers;

import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 1, 2010
 * Time: 10:07:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReceiverFactory {
  static Logger logger = Logger.getLogger(ReceiverFactory.class);

  private Map<String, Receiver> receivers;

  public void setReceivers(Map<String, Receiver> receivers) {
    this.receivers = receivers;
  }

  public void checkMessages(){
    for (Receiver receiver : receivers.values()) {
      receiver.fetch();
    }
  }
}
