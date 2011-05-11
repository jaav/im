package be.mobila.im;

import be.mobila.im.flow.utils.LiaPdfCreator;
import be.mobila.im.importers.Importer;
import be.mobila.im.importers.exceptions.ConverterException;
import be.mobila.im.models.ImRequest;
import be.mobila.im.models.Response;
import be.mobila.im.models.Status;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Jan 11, 2011
 * Time: 3:26:00 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/applicationContext.xml"})
public class RootFactoryTest{
  static Logger logger = Logger.getLogger(RootFactoryTest.class);

  @Autowired
  private RootFactory rootFactory;

  @Autowired
  private LiaPdfCreator liaPdfCreator;

  @Test
  public void testStart() throws Exception {
    long requestCount = ImRequest.countImRequests();
    long responseCount = Response.countResponses();
    rootFactory.start();
    Assert.assertTrue("No requests and/or responses were added to the DB", ImRequest.countImRequests()>requestCount && Response.countResponses()>responseCount);
  }

  public void pdfCreationTest_Pricing() throws Exception{
    createPdf(92L);
  }

  public void pdfCreationTest_Proposal() throws Exception{
    createPdf(100L);
  }

  public void pdfCreationTest_Contract() throws Exception{
    createPdf(96L);
  }

  private void createPdf(Long id) throws ConverterException, IOException, DocumentException, com.itextpdf.text.DocumentException {
    Response response = Response.findResponse(id);
    response.setStatus(Status.INITIATED);
    liaPdfCreator.createPdf(response);

  }
}
