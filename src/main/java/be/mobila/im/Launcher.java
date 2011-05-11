package be.mobila.im;

import be.mobila.im.importers.Importer;
import be.mobila.im.utils.DBFiller;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 2, 2010
 * Time: 7:44:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Launcher {

  private static AbstractApplicationContext context;

  public static void main(String args[]) {
    context = new ClassPathXmlApplicationContext("/META-INF/spring/applicationContext.xml");
    context.registerShutdownHook();
    //DBFiller filler = (DBFiller) context.getBean("dbFiller");
    //filler.fill();

  }
}
