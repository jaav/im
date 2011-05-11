package be.mobila.im.flow.communicate.senders;

import be.mobila.im.flow.communicate.parsers.RequestParser;
import be.mobila.im.flow.utils.PdfCreator;
import be.mobila.im.importers.exceptions.ConverterException;
import be.mobila.im.models.*;
import com.itextpdf.text.DocumentException;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 9, 2010
 * Time: 9:42:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class MailSender extends AbstractSender{
  static Logger logger = Logger.getLogger(MailSender.class);

  private JavaMailSender springMailSender;
  private String fromAddress;
  private String fromName;

  public void setFromAddress(String fromAddress) {
    this.fromAddress = fromAddress;
  }

  public void setFromName(String fromName) {
    this.fromName = fromName;
  }

  public void setSpringMailSender(JavaMailSender springMailSender) {
      this.springMailSender = springMailSender;
  }

  public boolean send(Response response){
    //String emailAddress = null;
    String content = null;
    InternetAddress toInetAddress = null;
    try {
      if(response.getResponseMode().equals(ResponseMode.BB_MAIL)){
        toInetAddress = new InternetAddress(response.getImRequest().getImuser().getIdentifier(),
            response.getImRequest().getImuser().getFirstname()+", "+response.getImRequest().getImuser().getName());
        content = "Here comes some text telling the IM user to check the attached pdf";
      }
      else if(response.getResponseMode().equals(ResponseMode.MAIL_WA)){
        Person person = response.getImRequest().getPerson();
        String first = person.getFirstname()==null?"":person.getFirstname();
        String second = person.getName()==null?"":person.getName();
        toInetAddress = new InternetAddress(response.getAddress(), first+", "+second);
        content = "Here comes some text telling the client to check the attached pdf";
      }
      String subject = null;
      if(response.getImRequest().getRequestFor().equals(RequestFor.PRICING)){
        subject = "Pricing Life Insurance";
      }
      else if(response.getImRequest().getRequestFor().equals(RequestFor.PROPOSAL)){
        subject = "Proposal Life Insurance";
      }
      else if(response.getImRequest().getRequestFor().equals(RequestFor.CONTRACT)){
        subject = "Contract Life Insurance";
      }

      MimeMessage message = springMailSender.createMimeMessage();

      // use the true flag to indicate you need a multipart message
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(new InternetAddress(fromAddress, fromName));
      helper.setTo(toInetAddress);
      helper.setSubject(subject);
      helper.setText(content);
      //create pdf
      PdfCreator pdfCreator = pdfCreators.get(response.getImRequest().getInsurance().getInsuranceType().toString());
      File newPdf = pdfCreator.createPdf(response);
      FileSystemResource file = new FileSystemResource(newPdf);
      helper.addAttachment(file.getFilename(), file);
      //@todo uncomment line here under
      springMailSender.send(message);
    } catch (MessagingException e) {
      logger.error("Inside the MessagingException");
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      return false;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      return false;
    } catch (DocumentException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      return false;
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      return false;
    } catch (org.dom4j.DocumentException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      return false;
    } catch (ConverterException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      return false;
    } catch (Exception e) {
      logger.error("Another Exception occurred. Can't connect to SMTP with address from = "+fromAddress+" and to = "+toInetAddress);
      //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      return false;
    }
    return true;
  }
}
