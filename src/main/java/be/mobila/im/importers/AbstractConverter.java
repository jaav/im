package be.mobila.im.importers;

import be.mobila.im.FolderResources;
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

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 16, 2010
 * Time: 11:32:15 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractConverter implements ApplicationContextAware {
  protected List locations;
  protected ApplicationContext context;
  protected File importDir;
  protected FolderResources folderResources;

  public void setFolderResources(FolderResources folderResources) {
    this.folderResources = folderResources;
  }

  public void setLocations(List locations) {
    this.locations = locations;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  public void init(Insurance insurance) throws ConverterException, IOException {
    if(StringUtils.isBlank(insurance.getImportFile())) throw new ConverterException("The importFileName is undefined");
    importDir = folderResources.getImportFolder();
  }
}
