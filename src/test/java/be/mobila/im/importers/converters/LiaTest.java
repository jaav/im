package be.mobila.im.importers.converters;

import be.mobila.im.flow.communicate.receivers.FileSystemReceiver;
import be.mobila.im.flow.communicate.senders.MailSender;
import be.mobila.im.flow.utils.PdfCreator;
import be.mobila.im.importers.Importer;
import be.mobila.im.importers.exceptions.ConverterException;
import be.mobila.im.models.*;
import be.mobila.im.utils.DBFiller;
import com.itextpdf.text.DocumentException;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 1, 2010
 * Time: 10:04:49 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/applicationContext.xml"})
public class LiaTest {
  static Logger logger = Logger.getLogger(LiaTest.class);

  @Autowired
  private Importer importer;
  @Autowired
  private DBFiller dbFiller;
  @Autowired
  private PdfCreator pdfCreator;
  @Autowired
  private FileSystemReceiver fileSystemReceiver;
  @Autowired
  private MailSender mailSender;


  @Test
  public void testConversion(){
    //Don't forget to drop a .xls file in the "import" folder
    logger.debug("************************************ START LIATEST ***************************************");
    dbFiller.fill();
    Insurance life = Insurance.findInsurancesByInsuranceType(InsuranceType.LIFE).getSingleResult();
    Assert.assertTrue("Couldn't find a life insurance!", life!=null);
    importer.convert(life);
    List<InsuranceValue> values =  InsuranceValue.findAllInsuranceValues();
    Assert.assertTrue("Something went wrong with the import of LIFE insurance values", !values.isEmpty());
  }

  @Test//This test creates a request and some responses in the db
  public void testRequestCreation(){
    //Don't forget to drop a file in the "drop" folder
    //empty the Request and Response tables
    List<Response> responses = Response.findAllResponses();
    for (Response response : responses) {
      response.remove();
    }
    List<ImRequest> requests = ImRequest.findAllImRequests();
    for (ImRequest request : requests) {
      request.remove();
    }
    //Start importing ...
    fileSystemReceiver.fetch();
    responses.clear();
    requests.clear();
    responses = Response.findAllResponses();
    requests = ImRequest.findAllImRequests();
    Assert.assertTrue("Something went wrong while creating new Requests and responses from the fileSystem", responses.size()>0);
    Assert.assertTrue("Something went wrong while creating new Requests and responses from the fileSystem", requests.size()>0);

  }

  @Test
  public void testPdfCreation(){
    try {
      List<Response> responses = Response.findResponsesByStatus(Status.INITIATED).getResultList();
      for (Response response : responses) {
        pdfCreator.createPdf(response);
        //Then, send the generated pdf through to the addressee stored in this response object and
        //do this using the protocol mentioned in the response object
      }
    } catch (IOException e) {
      Assert.assertTrue(e.getMessage(), 1==0);
    } catch (DocumentException e) {
      Assert.assertTrue(e.getMessage(), 1==0);
    } catch (org.dom4j.DocumentException e) {
      Assert.assertTrue(e.getMessage(), 1==0);
    } catch (ConverterException e) {
      Assert.assertTrue(e.getMessage(), 1==0);
    }
  }

  @Test
  public void testCreatePdfAndSendResponse(){
    List<Response> responses = Response.findResponsesByStatus(Status.INITIATED).getResultList();
    for (Response response : responses) {
      mailSender.send(response);
    }
  }



  private void fillDB(){
    dbFiller.fill();
  }

}
