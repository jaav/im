package be.mobila.im.flow.communicate.senders;

import be.mobila.im.models.Response;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 9, 2010
 * Time: 9:40:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Sender {
  public boolean send(Response response);
}
