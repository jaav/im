package be.mobila.im.flow.communicate.senders;

import be.mobila.im.FolderResources;
import be.mobila.im.flow.utils.PdfCreator;
import be.mobila.im.importers.exceptions.ConverterException;
import be.mobila.im.models.Insurance;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 16, 2010
 * Time: 9:37:37 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractSender implements Sender, ApplicationContextAware {
  protected List locations;
  protected ApplicationContext context;
  protected File xmlStorageDir;
  protected Map<String, PdfCreator> pdfCreators;
  protected FolderResources folderResources;

  public void setFolderResources(FolderResources folderResources) {
    this.folderResources = folderResources;
  }

  public void setLocations(List locations) {
    this.locations = locations;
  }

  public void setPdfCreators(Map<String, PdfCreator> pdfCreators) {
    this.pdfCreators = pdfCreators;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  public void init(Insurance insurance) throws ConverterException, IOException {
    xmlStorageDir = folderResources.getXmlStorageFolder();
  }
}