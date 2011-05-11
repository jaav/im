package be.mobila.im.importers.converters;

import be.mobila.im.importers.Importer;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 2, 2010
 * Time: 10:05:47 AM
 * To change this template use File | Settings | File Templates.
 */
  @RunWith(SpringJUnit4ClassRunner.class)
  @ContextConfiguration(locations={"/META-INF/spring/applicationContext.xml"})
  @TransactionConfiguration(transactionManager="transactionManager")
  @Transactional
  public class TestTest {
  static Logger logger = Logger.getLogger(Importer.class);

    @Test
    public void testSomething() {
      /*Insurance insurance = new Insurance();
      insurance.setInsuranceType(InsuranceType.FIRE);
      insurance.persist();
      System.out.println("************************* INSERTED *******************************");
      List<Insurance> insurances= Insurance.findAllInsurances();
      Assert.assertTrue("There are " + insurances + ". That's a problem!", insurances.size() > 0);
      logger.debug("There are " + insurances + ". Is that a problem?");
      Assert.assertTrue("Check the logs you. Damned!", 0 > 1);
      for (Insurance insurance1 : insurances) {
        System.out.println("*********************************** insurance1 = " + insurance1.getInsuranceType());
      }*/
    }
  }
