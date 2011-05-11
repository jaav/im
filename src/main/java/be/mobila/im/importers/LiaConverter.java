package be.mobila.im.importers;

import be.mobila.im.flow.utils.InsuranceTranslator;
import be.mobila.im.importers.exceptions.ConverterException;
import be.mobila.im.models.Insurance;
import be.mobila.im.models.LiaInsuranceSubType;
import be.mobila.im.models.LiaValue;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 7, 2010
 * Time: 10:02:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class LiaConverter extends AbstractDBStorageConverter implements ValueMatrixConverter, Converter{
  static Logger logger = Logger.getLogger(LiaConverter.class);


  @Override
  public void read(Insurance insurance) throws IOException, InvalidFormatException, ConverterException {
    File excel = new File(importDir, insurance.getImportFile());
    if(excel.exists()){
      InputStream inputStream = new FileInputStream(excel);
      Workbook workbook = WorkbookFactory.create(inputStream);
      write(insurance, workbook);
    }
  }

  @Override
  public void write(Insurance insurance, Workbook workbook) throws IOException {
    removeOldValues();
    for(int i = 0; i<6; i++){
      storeSheetContent(insurance, workbook.getSheetAt(i));
    }
    File xcel = new File(importDir, insurance.getImportFile());
    xcel.renameTo(new File(importDir, xcel.getName()+".im.bak"));
  }

  @Override
  public void convert(Insurance insurance) {
    try {
      init(insurance);
      read(insurance);
      //write(insurance);
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (InvalidFormatException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (ConverterException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
  }

  private void storeSheetContent(Insurance insurance, Sheet sheet){
    storeLiaContent(sheet, insurance);
  }

  private void storeLiaContent(Sheet sheet, Insurance insurance){
    Row amounts = sheet.getRow(4);
    Iterator<Row> rows = sheet.iterator();
    while (rows.hasNext()) {
      Row row = rows.next();
      if(row.getRowNum() > 4){
        Cell test = row.getCell(0);
        Iterator<Cell> cells = row.cellIterator();
        double rate;
        if(row.getCell(0)!=null && row.getCell(0).getCellType()==Cell.CELL_TYPE_NUMERIC){
          System.out.println("row.getCell(0) = " + row.getCell(0));
          rate = row.getCell(0).getNumericCellValue();
          while (cells.hasNext()) {
            Cell thisCell = cells.next();
            if(thisCell.getColumnIndex() > 0){
              LiaValue value = new LiaValue();
              value.setInsuranceSubType(getLiaType(sheet));
              try{
                value.setRate(rate);
                value.setAmount(new Double(amounts.getCell(thisCell.getColumnIndex()).getNumericCellValue()).longValue());
                Double d = thisCell.getNumericCellValue();
                int te = d.intValue();
                value.setProposal(thisCell.getNumericCellValue());
                value.setInsurance(insurance);
                value.persist();
              }
              catch(Exception e){
                e.printStackTrace();
              }
            }
          }
        }
        else break;
      }
    }
    
  }

  private void removeOldValues(){
    List<LiaValue> values = LiaValue.findAllLiaValues();
    for (LiaValue value : values) {
      value.remove();
    }

  }

  private LiaInsuranceSubType getLiaType(Sheet sheet){
    try{
      String type = sheet.getRow(2).getCell(8).getStringCellValue();
      if(StringUtils.isBlank(type)) return null;
      return InsuranceTranslator.getLiaSubType(type);
    }
    catch(Exception e){
      return null;
    }
  }
}
