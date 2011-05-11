package be.mobila.im.startup;

import be.mobila.im.importers.Importer;
import be.mobila.im.models.Insurance;
import be.mobila.im.utils.DBFiller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Jan 13, 2011
 * Time: 7:49:49 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/applicationContext.xml"})
public class StartUpTest {

  @Autowired
  private Importer importer;
  @Autowired
  private DBFiller dbFiller;

  @Test
  public void testStartUp(){
    dbFiller.fill();
    importer.convert(Insurance.findAllInsurances().get(0));
  }
}
