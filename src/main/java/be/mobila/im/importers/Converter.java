package be.mobila.im.importers;

import be.mobila.im.models.Insurance;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 14, 2010
 * Time: 2:14:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Converter {
  public void convert(Insurance insurance);
}
