package be.mobila.im.importers.converters;

import be.mobila.im.importers.Importer;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 14, 2010
 * Time: 4:34:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImportTest {

  private static AbstractApplicationContext context;

  public static void main(String args[]) {
    context = new ClassPathXmlApplicationContext("/META-INF/spring/applicationContext.xml");
    context.registerShutdownHook();
    Importer importer = (Importer)context.getBean("converter");
    importer.convert();
  }
}
