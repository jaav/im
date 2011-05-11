package be.mobila.im;

import be.mobila.im.flow.communicate.receivers.ReceiverFactory;
import be.mobila.im.flow.communicate.senders.SenderFactory;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 22, 2010
 * Time: 7:32:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class RootFactory {
  private ReceiverFactory receiverFactory;
  private SenderFactory senderFactory;

  public void setReceiverFactory(ReceiverFactory receiverFactory) {
    this.receiverFactory = receiverFactory;
  }

  public void setSenderFactory(SenderFactory senderFactory) {
    this.senderFactory = senderFactory;
  }

  public void start(){
    receiverFactory.checkMessages();
    senderFactory.sendMessages();
  }
}
