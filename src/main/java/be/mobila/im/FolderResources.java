package be.mobila.im;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 23, 2010
 * Time: 10:45:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class FolderResources {
  private String rootPath;
  private String importFolderName;
  private String dropFolderName;
  private String pdfStorageFolderName;
  private String xmlStorageFolderName;
  private String templatesFolderName;

  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  public void setImportFolderName(String importFolderName) {
    this.importFolderName = importFolderName;
  }

  public void setDropFolderName(String dropFolderName) {
    this.dropFolderName = dropFolderName;
  }

  public void setPdfStorageFolderName(String pdfStorageFolderName) {
    this.pdfStorageFolderName = pdfStorageFolderName;
  }

  public void setXmlStorageFolderName(String xmlStorageFolderName) {
    this.xmlStorageFolderName = xmlStorageFolderName;
  }

  public void setTemplatesFolderName(String templatesFolderName) {
    this.templatesFolderName = templatesFolderName;
  }

  public File getImportFolder(){
    return new File(rootPath, importFolderName);
  }

  public File getDropFolder(){
    return new File(rootPath, dropFolderName);
  }

  public File getTemplatesFolder(){
    return new File(rootPath, templatesFolderName);
  }

  public File getXmlStorageFolder(){
    return new File(rootPath, xmlStorageFolderName);
  }

  public File getPdfStorageFolder(){
    return new File(rootPath, pdfStorageFolderName);
  }
}
