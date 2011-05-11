package be.mobila.im.flow.utils;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import be.mobila.im.FolderResources;
import be.mobila.im.importers.exceptions.ConverterException;
import be.mobila.im.models.*;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.html.simpleparser.ChainedProperties;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.html.simpleparser.ImageProvider;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import javax.imageio.stream.FileImageInputStream;
import javax.sql.rowset.spi.XmlWriter;
import javax.swing.text.html.HTML;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 8, 2010
 * Time: 4:19:51 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PdfCreator extends PdfPageEventHelper implements ApplicationContextAware {
  HashMap<String,Object> map;
  protected ApplicationContext context;
  protected File pdfStorageDir;
  protected File templatesDir;
  protected FolderResources folderResources;

  public void setFolderResources(FolderResources folderResources) {
    this.folderResources = folderResources;
  }


  protected StyleSheet styles = new StyleSheet();

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  public File createPdf(Response response) throws IOException, DocumentException, org.dom4j.DocumentException, ConverterException {
    init();
    String templateName = response.getImRequest().getInsurance().getPdfTemplate();
    File template = null;
    File newPdf = null;
    //Document document = null;
    String fullHtml = null;
    PdfWriter writer;
    PdfStamper stamper = null;
    //Both methods are equal for the time being. Maybe in the future they will become different
    if(response.getResponseMode().equals(ResponseMode.BB_MAIL)){
      template = new File(templatesDir, templateName+"_"+response.getImRequest().getRequestFor()+".pdf");
      newPdf = new File(pdfStorageDir, response.getImRequest().getUri()+"_"+response.getImRequest().getRequestFor()+"_BB.pdf");
      PdfReader reader = new PdfReader(new FileInputStream(template));
      stamper = new PdfStamper(reader, new FileOutputStream(newPdf));
      if(response.getImRequest().getRequestFor().equals(RequestFor.PRICING))
        getPricingContentSmall(response, stamper);
      else if(response.getImRequest().getRequestFor().equals(RequestFor.PROPOSAL))
        getProposalContentSmall(response, stamper);
      else if(response.getImRequest().getRequestFor().equals(RequestFor.CONTRACT))
        getContractContentSmall(response, stamper);
    }
    else{
      template = new File(templatesDir, templateName+"_"+response.getImRequest().getRequestFor()+".pdf");
      newPdf = new File(pdfStorageDir, response.getImRequest().getUri()+"_"+response.getImRequest().getRequestFor()+".pdf");  
      PdfReader reader = new PdfReader(new FileInputStream(template));
      stamper = new PdfStamper(reader, new FileOutputStream(newPdf));
      if(response.getImRequest().getRequestFor().equals(RequestFor.PRICING))
        getPricingContent(response, stamper);
      else if(response.getImRequest().getRequestFor().equals(RequestFor.PROPOSAL))
        getProposalContent(response, stamper);
      else if(response.getImRequest().getRequestFor().equals(RequestFor.CONTRACT))
        getContractContent(response, stamper);
    }
    stamper.close();
    return newPdf;
  }

  protected abstract void getPricingContentSmall(Response response, PdfStamper stamper) throws org.dom4j.DocumentException, IOException, DocumentException;

  protected abstract void getPricingContent(Response response, PdfStamper stamper) throws org.dom4j.DocumentException, IOException, DocumentException;

  protected abstract void getProposalContentSmall(Response response, PdfStamper stamper) throws org.dom4j.DocumentException, IOException, DocumentException;

  protected abstract void getProposalContent(Response response, PdfStamper stamper) throws org.dom4j.DocumentException, IOException, DocumentException;

  protected abstract void getContractContentSmall(Response response, PdfStamper stamper) throws org.dom4j.DocumentException, IOException, DocumentException;

  protected abstract void getContractContent(Response response, PdfStamper stamper) throws org.dom4j.DocumentException, IOException, DocumentException;

  private void init() throws ConverterException, IOException {
    map = new HashMap<String, Object>();
    map.put("font_factory", new MyFontFactory());
    map.put("img_provider", new MyImageFactory());
    templatesDir = folderResources.getTemplatesFolder();
    pdfStorageDir = folderResources.getPdfStorageFolder();
    setStyles();
  }



  /** Imported page with the stationery. */
  protected PdfImportedPage page;

  /**
   * Initialize the imported page.
   * @param writer The PdfWriter
   * @throws IOException
   */
  public void useStationary(PdfWriter writer, File template) throws IOException {
      writer.setPageEvent(this);
      PdfReader reader = new PdfReader(new FileInputStream(template));
      page = writer.getImportedPage(reader, 1);
  }

  /**
   * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
   *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
   */
  public void onOpenDocument(PdfWriter writer, Document document) {
      writer.getDirectContent().addTemplate(page, 0, 0);
  }

  protected abstract void setStyles();


  /**
     * Inner class implementing the ImageProvider class.
     * This is needed if you want to resolve the paths to images.
     */
    public static class MyImageFactory implements ImageProvider {
        @SuppressWarnings("unchecked")
        public Image getImage(String src, HashMap h,
                ChainedProperties cprops, DocListener doc) {
            try {
                return Image.getInstance(
                    String.format("resources/posters/%s",
                        src.substring(src.lastIndexOf("/") + 1)));
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Inner class implementing the FontProvider class.
     * This is needed if you want to select the correct fonts.
     */
    public static class MyFontFactory implements FontProvider {
        public Font getFont(String fontname,
                String encoding, boolean embedded, float size,
                int style, BaseColor color) {
            return new Font(Font.FontFamily.TIMES_ROMAN, size, style, color);
        }

        public boolean isRegistered(String fontname) {
            return false;
        }
    }
}
