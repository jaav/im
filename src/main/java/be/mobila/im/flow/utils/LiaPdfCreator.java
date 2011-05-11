package be.mobila.im.flow.utils;

import be.mobila.im.flow.communicate.parsers.RequestParser;
import be.mobila.im.models.LiaValue;
import be.mobila.im.models.Person;
import be.mobila.im.models.Response;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.html.simpleparser.StyleSheet;
import com.itextpdf.text.pdf.*;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 20, 2010
 * Time: 10:22:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class LiaPdfCreator extends PdfCreator {

  protected Font smallFont;
  protected Font normalFont;
  protected Font titleFont;

  @Override
  protected void getPricingContent(Response response, PdfStamper stamper) throws org.dom4j.DocumentException, IOException, DocumentException {
    SAXReader reader = new SAXReader();
    SAXReader responseContentReader = new SAXReader();
    org.dom4j.Document responseContent = reader.read(new StringReader(response.getImresult()));

    PdfContentByte page = stamper.getOverContent(1);


    ColumnText.showTextAligned(page,
                Element.ALIGN_LEFT,
                new Phrase("Your request for rates for a capital of "+responseContent.selectSingleNode("//capital").getText()+" resulted in ...", titleFont),
                36, PageSize.A4.getHeight()-200, 0);

    PdfPTable pricingResultDataTable = getPricingResultDataTable(responseContent);

    ColumnText ct = new ColumnText(page);
    ct.setAlignment(Element.ALIGN_CENTER);
    ct.setSimpleColumn(36, 36, PageSize.A4.getWidth()-36, PageSize.A4.getHeight()-250);
    ct.addElement(pricingResultDataTable);
    writeCTContent(ct);
  }

  @Override
  protected void getPricingContentSmall(Response response, PdfStamper stamper) throws org.dom4j.DocumentException, IOException, DocumentException {
    getPricingContent(response, stamper);
  }

  @Override
  protected void getContractContent(Response response, PdfStamper stamper) throws org.dom4j.DocumentException, IOException, DocumentException {SAXReader reader = new SAXReader();
    SAXReader responseContentReader = new SAXReader();
    org.dom4j.Document responseContent = reader.read(new StringReader(response.getImresult()));


		PdfPCell divider = new MyCell(new Paragraph(":    ", smallFont));

                                  
    PdfPTable userDataTable = getUserDataTable(response, divider);
    PdfPTable supplierDataTable = getSupplierDataTable(response, divider);
    PdfPTable resultDataTable = getResultDataTable(response, divider);


    PdfContentByte firstPage = stamper.getOverContent(1);
    
    ColumnText ct = new ColumnText(firstPage);
    ct.setAlignment(Element.ALIGN_CENTER);
    ct.setSimpleColumn(36, 36, PageSize.A4.getWidth()-36, PageSize.A4.getHeight()-250);
    ct.addElement(userDataTable);

    ColumnText ct2 = new ColumnText(firstPage);
    ct2.setAlignment(Element.ALIGN_CENTER);
    ct2.setSimpleColumn(36, 36, PageSize.A4.getWidth()-36, PageSize.A4.getHeight()-550);
    ct2.addElement(supplierDataTable);

    writeCTContent(ct);
    writeCTContent(ct2);
    
    
    PdfContentByte secondPage = stamper.getOverContent(2);

    ColumnText ct3 = new ColumnText(secondPage);
    ct3.setAlignment(Element.ALIGN_CENTER);
    ct3.setSimpleColumn(36, 36, PageSize.A4.getWidth()-36, PageSize.A4.getHeight()-200);
    ct3.addElement(resultDataTable);

    writeCTContent(ct3);
  }

  @Override
  protected void getContractContentSmall(Response response, PdfStamper stamper) throws org.dom4j.DocumentException, IOException, DocumentException {
    getContractContent(response, stamper);

  }

  @Override
  protected void getProposalContent(Response response, PdfStamper stamper) throws org.dom4j.DocumentException, IOException, DocumentException {
    SAXReader reader = new SAXReader();
    SAXReader responseContentReader = new SAXReader();
    NumberFormat percentFormatter = new DecimalFormat("#0.00%");
    NumberFormat capitalFormatter = NumberFormat.getInstance(new Locale("nl","BE"));

    LiaValue result = (LiaValue)InsuranceTranslator.getResult(response.getValueId(), response.getImRequest().getInsurance().getInsuranceType());
    String insuranceSubType = InsuranceTranslator.getLiaSubType(result.getInsuranceSubType());

    PdfContentByte page = stamper.getOverContent(1);

    Person person = response.getImRequest().getPerson();


    ColumnText.showTextAligned(page,
                Element.ALIGN_LEFT,
                new Phrase(person.getName()+", "+person.getFirstname(), smallFont),
                290, PageSize.A4.getHeight()-196, 0);
    ColumnText.showTextAligned(page,
                Element.ALIGN_LEFT,
                new Phrase(person.getPlaceofbirth(), smallFont),
                290, PageSize.A4.getHeight()-217, 0);
    ColumnText.showTextAligned(page,
                Element.ALIGN_LEFT,
                new Phrase(person.getAddress(), smallFont),
                290, PageSize.A4.getHeight()-237, 0);
    ColumnText.showTextAligned(page,
                Element.ALIGN_LEFT,
                new Phrase(person.getPostalcode()+" "+person.getCity(), smallFont),
                290, PageSize.A4.getHeight()-257, 0);
    ColumnText.showTextAligned(page,
                Element.ALIGN_LEFT,
                new Phrase("===", smallFont),
                288, PageSize.A4.getHeight()-340, 0);
    ColumnText.showTextAligned(page,
                Element.ALIGN_LEFT,
                new Phrase("(TESTING) "+capitalFormatter.format(result.getAmount()), smallFont),
                300, PageSize.A4.getHeight()-375, 0);
    ColumnText.showTextAligned(page,
                Element.ALIGN_LEFT,
                new Phrase("(TEST) "+percentFormatter.format(result.getRate()), smallFont),
                300, PageSize.A4.getHeight()-409, 0);
    ColumnText.showTextAligned(page,
                Element.ALIGN_LEFT,
                new Phrase("(TESTING) 5 maart 2011", smallFont),
                290, PageSize.A4.getHeight()-430, 0);
    ColumnText.showTextAligned(page,
                Element.ALIGN_LEFT,
                new Phrase("=====", smallFont),
                340, PageSize.A4.getHeight()-449, 0);
    ColumnText.showTextAligned(page,
                Element.ALIGN_LEFT,
                new Phrase("=======", smallFont),
                290, PageSize.A4.getHeight()-471, 0);
    ColumnText.showTextAligned(page,
                Element.ALIGN_LEFT,
                new Phrase("===                ======= ======", smallFont),
                256, PageSize.A4.getHeight()-491, 0);
    ColumnText.showTextAligned(page,
                Element.ALIGN_LEFT,
                new Phrase("- Verzekering van het type: "+InsuranceTranslator.getLiaSubType(result.getInsuranceSubType()), smallFont),
                120, PageSize.A4.getHeight()-580, 0);
  }

  @Override
  protected void getProposalContentSmall(Response response, PdfStamper stamper) throws org.dom4j.DocumentException, IOException, DocumentException {
    getProposalContent(response, stamper);
  }

  protected void setStyles(){
		smallFont = FontFactory.getFont("Arial", 12);
		normalFont = FontFactory.getFont("Arial", 16);
		titleFont = FontFactory.getFont("Arial", 16);
		titleFont.setStyle(Font.BOLD);
  }

  private void writeCTContent(ColumnText ct) throws DocumentException {
    int currentColumn =0;
    while (true)
    {
      int status = ct.go();
      if ((status & ColumnText.NO_MORE_TEXT) != 0)
      {
        break;
      }
      ++currentColumn;
      if(currentColumn>=1){
        break;
      }
    }
  }

  private PdfPTable getUserDataTable(Response response, PdfPCell divider) throws DocumentException {
		PdfPCell cell;
    PdfPTable userDataTable = new PdfPTable(3);
    userDataTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
    userDataTable.setTotalWidth(360f);
    //userDataTable.setWidthPercentage(288 / 5.23f);
    userDataTable.setWidths(new int[]{5, 1, 5});
    cell = new MyCell(new Paragraph("Nom", smallFont));
    userDataTable.addCell(cell);
    userDataTable.addCell(divider);
    cell = new MyCell(new Paragraph(response.getImRequest().getPerson().getName(), smallFont));
    userDataTable.addCell(cell);
    cell = new MyCell(new Paragraph("Prénom", smallFont));
    userDataTable.addCell(cell);
    userDataTable.addCell(divider);
    cell = new MyCell(new Paragraph(response.getImRequest().getPerson().getFirstname(), smallFont));
    userDataTable.addCell(cell);
    cell = new MyCell(new Paragraph("Addresse", smallFont));
    userDataTable.addCell(cell);
    userDataTable.addCell(divider);
    cell = new MyCell(new Paragraph(response.getImRequest().getPerson().getAddress()+"\n"+
      response.getImRequest().getPerson().getPostalcode()+" "+response.getImRequest().getPerson().getCity(), smallFont));
    userDataTable.addCell(cell);
    cell = new MyCell(new Paragraph("Nationalité", smallFont));
    userDataTable.addCell(cell);
    userDataTable.addCell(divider);
    cell = new MyCell(new Paragraph(response.getImRequest().getPerson().getNationality(), smallFont));
    userDataTable.addCell(cell);
    cell = new MyCell(new Paragraph("Tel", smallFont));
    userDataTable.addCell(cell);
    userDataTable.addCell(divider);
    cell = new MyCell(new Paragraph("", smallFont));
    userDataTable.addCell(cell);
    return userDataTable;
  }

  private PdfPTable getSupplierDataTable(Response response, PdfPCell divider) throws DocumentException {
		PdfPCell cell;
    PdfPTable supplierDataTable = new PdfPTable(3);
    supplierDataTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
    supplierDataTable.setTotalWidth(360f);
    //supplierDataTable.setWidthPercentage(288 / 5.23f);
    supplierDataTable.setWidths(new int[]{5, 1, 5});
    cell = new MyCell(new Paragraph("Nom", smallFont));
    supplierDataTable.addCell(cell);
    supplierDataTable.addCell(divider);
    cell = new MyCell(new Paragraph("Lombard s.a.", smallFont));
    supplierDataTable.addCell(cell);
    cell = new MyCell(new Paragraph("N° TVA", smallFont));
    supplierDataTable.addCell(cell);
    supplierDataTable.addCell(divider);
    cell = new MyCell(new Paragraph("LU-0874.822.843", smallFont));
    supplierDataTable.addCell(cell);
    cell = new MyCell(new Paragraph("Addresse", smallFont));
    supplierDataTable.addCell(cell);
    supplierDataTable.addCell(divider);
    cell = new MyCell(new Paragraph("4, rue Lou Hemmer\nL-1748 Luxembourg", smallFont));
    supplierDataTable.addCell(cell);
    cell = new MyCell(new Paragraph("Tel", smallFont));
    supplierDataTable.addCell(cell);
    supplierDataTable.addCell(divider);
    cell = new MyCell(new Paragraph("(+352) 34 61 91-1", smallFont));
    supplierDataTable.addCell(cell);
    cell = new MyCell(new Paragraph("Fax", smallFont));
    supplierDataTable.addCell(cell);
    supplierDataTable.addCell(divider);
    cell = new MyCell(new Paragraph("(+352) 34 61 90", smallFont));
    supplierDataTable.addCell(cell);
    return supplierDataTable;
  }

  private PdfPTable getResultDataTable(Response response, PdfPCell divider) throws DocumentException {
    NumberFormat percentFormatter = new DecimalFormat("#0.00%");
    NumberFormat capitalFormatter = NumberFormat.getInstance(new Locale("nl","BE"));
		PdfPCell cell;
    PdfPTable resultDataTable = new PdfPTable(3);
    LiaValue result = (LiaValue)InsuranceTranslator.getResult(response.getValueId(), response.getImRequest().getInsurance().getInsuranceType());
    resultDataTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
    resultDataTable.setTotalWidth(360f);
    //resultDataTable.setWidthPercentage(288 / 5.23f);
    resultDataTable.setWidths(new int[]{5, 1, 5});
    cell = new MyCell(new Paragraph("Capital assuré", smallFont));
    resultDataTable.addCell(cell);
    resultDataTable.addCell(divider);
    cell = new MyCell(new Paragraph(capitalFormatter.format(result.getAmount()), smallFont));
    resultDataTable.addCell(cell);
    cell = new MyCell(new Paragraph("Type d'assurance", smallFont));
    resultDataTable.addCell(cell);
    resultDataTable.addCell(divider);
    cell = new MyCell(new Paragraph(InsuranceTranslator.getLiaSubType(result.getInsuranceSubType())+"", smallFont));
    resultDataTable.addCell(cell);
    cell = new MyCell(new Paragraph("Votre taux", smallFont));
    resultDataTable.addCell(cell);
    resultDataTable.addCell(divider);
    cell = new MyCell(new Paragraph(percentFormatter.format(result.getProposal()), smallFont));
    resultDataTable.addCell(cell);
    return resultDataTable;
  }

  private PdfPTable getPricingResultDataTable(org.dom4j.Document responseContent) throws DocumentException {
    //float[] widths = {25f,6f,6f,6f,6f,6f};
		PdfPCell cell;
    PdfPTable resultDataTable = new PdfPTable(6);
    resultDataTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
    resultDataTable.setTotalWidth(360f);
    resultDataTable.setWidths(new int[]{5,1,1,1,1,1});
    List cells = responseContent.selectNodes("//tr/td");
    int counter = 0;
    for (Object aCell : cells) {
      Font currentFont;
      if(counter < 6) currentFont = titleFont;
      else if(counter%6==0)currentFont = titleFont;
      else currentFont = normalFont;
      Node cellNode = (Node)aCell;
		  cell = new PdfPCell(new Paragraph(cellNode.getText(), currentFont));
      cell.setPadding(5f);
      resultDataTable.addCell(cell);
      counter++;
    }
    return resultDataTable;
  }

  private class MyCell extends PdfPCell{
    public MyCell(Phrase phrase){
      super(phrase);
      setBorder(0);
    }


  }
}
