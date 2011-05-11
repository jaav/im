package be.mobila.im.flow.utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Nov 13, 2010
 * Time: 2:36:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlFileFilter implements FilenameFilter {

  public boolean accept(File dir, String name) {
    return name.endsWith(".xml");
  }
}