package be.mobila.im.flow.communicate.receivers;

import be.mobila.im.FolderResources;
import be.mobila.im.flow.communicate.parsers.RequestParser;
import be.mobila.im.importers.exceptions.ConverterException;
import be.mobila.im.models.*;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 13, 2010
 * Time: 2:51:35 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractDataSaver implements DataSaver, ApplicationContextAware {
  protected ApplicationContext context;
  protected File xmlStorageDir;
  protected FolderResources folderResources;

  public void setFolderResources(FolderResources folderResources) {
    this.folderResources = folderResources;
  }

  @Override
  public void save(Document requestData, ImRequest request) {
    if(hasPerson(requestData)){
      Person person = savePerson(requestData);
      request.setPerson(person);
    }
    try {
      request.setUri(persistXml(requestData));
    } catch (IOException e) {
      request.setLog(e.getMessage());
    }
    request.persist();
    createResponses(requestData, request);

  }

  private boolean hasPerson(Document requestData){
    return true;
  }

  protected Person savePerson(Document requestData){
    Person person = RequestParser.parsePerson(requestData);
    person.persist();
    return person;
  }


  protected void createResponses(Document requestData, ImRequest request){
    String resultBlock = calculateResult(requestData, request);
    if(resultBlock != null){
      Map<String, ResponseMode> responseModes = RequestParser.parseResponseModes(requestData);
      if(request.getImuser().getIdentifier() != null)
        responseModes.put(request.getImuser().getIdentifier(), ResponseMode.BB_MAIL);
      for (String address : responseModes.keySet()) {
        if(StringUtils.isNotBlank(address)){
          Response response = new Response();
          response.setAddress(address);

          response.setResponseDate(new Date());
          response.setResponseMode(responseModes.get(address));
          if(resultBlock!=null) response.setImresult(resultBlock);

          InsuranceValue value = getValueId(requestData);
          if(value != null)
            response.setValueId(value.getId());
          response.setStatus(Status.INITIATED);
          response.setImRequest(request);
          response.persist();
        }
      }
    }
    else{
      //Maybe add request saving code !!!!!!!!!!!!!!!!
    }
  }



  protected String persistXml(Document requestData) throws IOException {
    String uuid = null;
    try {
      init();
      uuid = UUID.randomUUID().toString();
      XMLWriter writer = new XMLWriter(
          new FileWriter(new File(xmlStorageDir, uuid+".xml")
      ));
      writer.write(requestData);
      writer.close();
    } catch (ConverterException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    return uuid;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  public void init() throws ConverterException, IOException {
    xmlStorageDir = folderResources.getXmlStorageFolder();
  }
}
