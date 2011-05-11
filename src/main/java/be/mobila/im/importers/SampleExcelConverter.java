package be.mobila.im.importers;

import be.mobila.im.importers.exceptions.ConverterException;
import be.mobila.im.models.Insurance;
import be.mobila.im.utils.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 1, 2010
 * Time: 3:11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class SampleExcelConverter extends AbstractXMLStorageConverter implements NameValuePairConverter, Converter {

  private Sheet sheet;

  @Override
  public void convert(Insurance insurance){
    try {
      init(insurance);
      read(insurance);
      List<NameValuePair> properties = interpret();
      writeXml(properties, insurance);
    } catch (FileNotFoundException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (InvalidFormatException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (ConverterException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }

  }

  @Override
  public void read(Insurance insurance) throws IOException, InvalidFormatException, ConverterException {
    if(StringUtils.isBlank(insurance.getImportFile())) throw new ConverterException("The importFileName is undefined");
    InputStream inputStream = new FileInputStream(new File("import", insurance.getImportFile()));
    Workbook wb = WorkbookFactory.create(inputStream);
    String text;
    if(wb instanceof XSSFWorkbook){
      XSSFExcelExtractor extractor = new XSSFExcelExtractor((XSSFWorkbook)wb);
      extractor.setFormulasNotResults(true);
      extractor.setIncludeSheetNames(false);
      text = extractor.getText();
      String test = text;
    }
    else if(wb instanceof HSSFWorkbook){
      ExcelExtractor extractor = new ExcelExtractor((HSSFWorkbook)wb);
      extractor.setFormulasNotResults(true);
      extractor.setIncludeSheetNames(false);
      text = extractor.getText();
      String test = text;
    }
    sheet = wb.getSheetAt(0);

  }

  @Override
  public void writeXml(List<NameValuePair> properties, Insurance insurance) throws IOException {
    Document document = DocumentHelper.createDocument();
    Element root = document.addElement(insurance.getInsuranceType().toString());
    for (NameValuePair property : properties) {
      Element prop = root.addElement("property");
      Element name = prop.addElement("name").addText(property.getName());
      Element value = prop.addElement("value").addText(property.getValue().toString());
      if(property.getValue() instanceof String) value.addAttribute("type", STRING);
      else if(property.getValue() instanceof Boolean) value.addAttribute("type", BOOLEAN);
      else if(property.getValue() instanceof Double) value.addAttribute("type", NUMERIC);
      else if(property.getValue() instanceof Date) value.addAttribute("type", DATE);

      if(COMMENT.equals(property.getComment())) value.addAttribute("check", COMMENT);
      else if(PROPERTY.equals(property.getComment())) value.addAttribute("check", PROPERTY);
      else if(VARIABLE.equals(property.getComment())) value.addAttribute("check", VARIABLE);
      else if(FORMULA.equals(property.getComment())) value.addAttribute("check", FORMULA);
    }
    XMLWriter writer = new XMLWriter(new FileWriter(new File("export",insurance.getImportFile()+".xml")));
    writer.write( document );
    writer.close();
  }

  @Override
  public List<NameValuePair> interpret() throws ConverterException {
    List<NameValuePair> properties = new ArrayList<NameValuePair>();
    Iterator<Row> rows = sheet.iterator();
    while (rows.hasNext()) {
      Row row = rows.next();
      Iterator<Cell> cells = row.cellIterator();
      NameValuePair pair = new NameValuePair();
      while (cells.hasNext()) {
        Cell cell = cells.next();
        String value = cell.toString();
        if(cell.getCellType() != Cell.CELL_TYPE_BLANK){
          if(StringUtils.isBlank(pair.getName())){
            if(cell.getCellType()==Cell.CELL_TYPE_STRING) pair.setName(cell.getStringCellValue());
            else throw new ConverterException("The first cell of every row should be textual data (no numerics, dates, etc)");
          }
          else if(pair.getValue()==null){
            if(cell.getCellType()==Cell.CELL_TYPE_STRING) pair.setValue(cell.getStringCellValue());
            if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
              if(DateUtil.isCellDateFormatted(cell))
                pair.setValue(cell.getDateCellValue());
              else
                pair.setValue(cell.getNumericCellValue());
            }
            if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN) pair.setValue(cell.getBooleanCellValue());
          }
          else{
            if(cell.getCellType()==Cell.CELL_TYPE_STRING){
              if("comment".equalsIgnoreCase(cell.getStringCellValue())) pair.setComment(COMMENT);
              else if("variable".equalsIgnoreCase(cell.getStringCellValue())) pair.setComment(VARIABLE);
              else if("property".equalsIgnoreCase(cell.getStringCellValue())) pair.setComment(PROPERTY);
              else if("formula".equalsIgnoreCase(cell.getStringCellValue())) pair.setComment(FORMULA);
            }
          }
        }
        break;
      }
      if(pair.isValid()) properties.add(pair);
    }
    return properties;
  }
}
