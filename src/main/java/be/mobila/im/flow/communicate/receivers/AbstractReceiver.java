package be.mobila.im.flow.communicate.receivers;

import be.mobila.im.models.Insurance;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 15, 2010
 * Time: 3:20:38 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractReceiver implements Receiver {

  private Map<String, DataSaver> dataSavers;

  public void setDataSavers(Map<String, DataSaver> dataSavers) {
    this.dataSavers = dataSavers;
  }

  protected AbstractDataSaver getDataSaver(Insurance insurance){
    return (AbstractDataSaver)dataSavers.get(insurance.getAbstractDataSaver());
  }
}
