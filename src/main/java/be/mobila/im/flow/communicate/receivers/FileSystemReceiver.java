package be.mobila.im.flow.communicate.receivers;

import be.mobila.im.FolderResources;
import be.mobila.im.flow.communicate.parsers.RequestParser;
import be.mobila.im.flow.exceptions.FlowException;
import be.mobila.im.flow.utils.XmlFileFilter;
import be.mobila.im.models.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 15, 2010
 * Time: 2:34:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileSystemReceiver extends AbstractReceiver implements ApplicationContextAware {
  static Logger logger = Logger.getLogger(MailReceiver.class);
  private ApplicationContext context;
  protected FolderResources folderResources;

  public void setFolderResources(FolderResources folderResources) {
    this.folderResources = folderResources;
  }

  public void fetch(){

    try {
      File dropFolder = folderResources.getDropFolder();
      FilenameFilter xmlFilter = new XmlFileFilter();
      File[] xmlFiles = dropFolder.listFiles(xmlFilter);
      int counter = 1;
      if(xmlFiles.length > 0){
        for (File xmlFile : xmlFiles) {
          logger.debug("Counter = "+counter);
          processXml(new FileInputStream(xmlFile));
          xmlFile.renameTo(new File(dropFolder, xmlFile.getName()+".im.bak"));
          counter++;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }


  }

  /*
  As this is a fileSystemReceiver
  - we get the Insurance from the metedata block inside the xml document (should be equal to one of the InsuranceType enum values)
  - ditto for the ImUser's emailaddress.
   */
  private void processXml(InputStream xmlStream) {
    DataSaver dataSaver = null;
    Document document = null;
    ImRequest request = new ImRequest();
    try{
      SAXReader reader = new SAXReader();
      document = reader.read(xmlStream);
      request.setRequestMode(RequestMode.DROPPING);
      RequestFor requestFor;
      try{
        requestFor = RequestFor.valueOf(RequestParser.parseRequestFor(document));
      }
      catch(IllegalArgumentException e){
        throw new FlowException("RequestFor could not be parsed from uploaded xml document or it doesn't match a predefined value.");
      }
      request.setRequestFor(requestFor);
      ImUser imUser;
      try{
        imUser = ImUser.findImUsersByIdentifierEquals(RequestParser.parseUserIdentifier(document)).getSingleResult();
      }catch(Exception e){
        throw new FlowException("ImUser identifier could not be parsed from uploaded xml or it doesn't match an ImUser identifier from the DB.");
      }
      request.setImuser(imUser);
      Insurance insurance;
      try{
        insurance = Insurance.findInsurancesByInsuranceType(InsuranceType.valueOf(RequestParser.parseInsuranceType(document))).getSingleResult();
      }catch(Exception e){
        throw new FlowException("An InsuranceType could not be parsed from the uploaded xml document or an Insurance could not be found in the DB for this InsuranceType.");
      }

      dataSaver = getDataSaver(insurance);
      request.setInsurance(insurance);
      request.setRequestDate(new Date());
    }catch (DocumentException e) {
      request.setLog(e.getMessage());
      logger.error(e.getMessage());
    }catch(FlowException e){
      request.setLog(e.getMessage());
      logger.error(e.getMessage());
    }
    finally {
      if(dataSaver == null)
        request.persist();
      else
        dataSaver.save(document, request);
    }

  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }
}
