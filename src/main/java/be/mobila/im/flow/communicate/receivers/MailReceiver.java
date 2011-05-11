package be.mobila.im.flow.communicate.receivers;

import be.mobila.im.flow.communicate.parsers.RequestParser;
import be.mobila.im.flow.exceptions.FlowException;
import be.mobila.im.models.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 9, 2010
 * Time: 9:46:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class MailReceiver extends AbstractReceiver {
  static Logger logger = Logger.getLogger(MailReceiver.class);
  private MailProps mailProps;

  public void setMailProps(MailProps mailProps) {
    this.mailProps = mailProps;
  }

  public void fetch(){

    Properties properties = System.getProperties();

    Session session = Session.getDefaultInstance(properties, null);
    //session.setDebug(true);
    Store store = null;
    Message[] messages = null;
    try {
      if("imap".equals(mailProps.getProtocol())){
        store = session.getStore("imap");
        properties.put("mail.imap.auth.plain.disable", "true");
      }
      else if("pop3".equals(mailProps.getProtocol())) store = session.getStore("pop3");
      else throw new FlowException("Unable to read the mail protocol value (imap, pop3) from the properties.");
      store.connect(mailProps.getHost(), mailProps.getUsername(), mailProps.getPassword());
      Folder folder = store.getFolder("inbox");
      folder.open(Folder.READ_WRITE);
      messages = folder.getMessages();

      for (int a = 0; a < messages.length; a++) {
        try{
          processMessage(messages[a]);
        }catch(Exception e){
          logger.error("Error processing a message's attachment");
        }
      }

      folder.expunge();
      folder.close(true);
      store.close();

    } catch (MessagingException e) {
      logger.error("Can't connect to MailServer while trying to fetch mails");
    } catch (FlowException e) {
      logger.error("Can't connect to MailServer while trying to fetch mails");
    }


  }

  /*
  As this is a mailReceiver
  - we get the Insurance from the mailsubject (should be equal to one of the InsuranceType enum values)
  - we get the ImUser from the "from" emailaddress.
   */
  private void processMessage(Message message){
    ImRequest request = new ImRequest();
    Document document = null;
    DataSaver dataSaver = null;
    try{
      System.out.println(message.getSentDate());
      Address[] addresses = message.getFrom();
      String subject = message.getSubject();
      if(addresses != null && addresses.length>0 && StringUtils.isNotBlank(subject)){
        String emailAddress = ((InternetAddress)addresses[0]).getAddress();
        List<ImUser> users = ImUser.findImUsersByIdentifierEquals(emailAddress).getResultList();
        if(users.isEmpty()) throw new FlowException("ImUser could not be found for identifier "+emailAddress+"(subject = "+subject+")");
        InsuranceType insuranceType = null;
        try{
          insuranceType = InsuranceType.valueOf(subject);
        }catch(IllegalArgumentException e){
          throw new FlowException("InsuranceType could not be found for subject: "+subject);
        }
        Insurance insurance = null;
        try{
          insurance = Insurance.findInsurancesByInsuranceType(insuranceType).getSingleResult();
        }catch(IllegalArgumentException e){
          throw new FlowException("Insurance could not be found for InsuranceType: "+insuranceType);
        }
        ImUser user = users.get(0);
        MimeMessage cmsg = new MimeMessage((MimeMessage)message);
        Multipart multipart = (Multipart) cmsg.getContent();
        dataSaver = getDataSaver(insurance);
        for (int i = 0; i < multipart.getCount(); i++) {
          BodyPart bodyPart = multipart.getBodyPart(i);
          if(bodyPart.getContentType().startsWith("text/xml") || bodyPart.getContentType().startsWith("application/octet-stream")) {
            InputStream stream = bodyPart.getInputStream();
            SAXReader reader = new SAXReader();
            document = reader.read(stream);
            String requestFor = RequestParser.parseRequestFor(document);
            if(requestFor ==null) throw new FlowException("RequestFor could not be parsed from uploaded xml document");
            else{
              request.setRequestFor(RequestFor.valueOf(requestFor));
              request.setRequestMode(RequestMode.BB_MAIL);
              request.setImuser(user);
              request.setInsurance(insurance);
              request.setRequestDate(new Date());
            }
          }
        }
      }
      else{
        throw new FlowException("Subject or FromAddress missing from mail upload\n");
      }
    }catch(MessagingException e){
      request.setLog(e.getMessage()+"\n");
      logger.error(e.getMessage());
    }catch (DocumentException e) {
      request.setLog(e.getMessage()+"\n");
      logger.error(e.getMessage());
    }catch (IOException e) {
      request.setLog(e.getMessage()+"\n");
      logger.error(e.getMessage());
    }catch (FlowException e) {
      request.setLog(e.getMessage()+"\n");
      logger.error(e.getMessage());
    } catch (IllegalArgumentException e) {
      request.setLog(e.getMessage()+"\n");
      logger.error(e.getMessage());
    } catch(Exception e){            
      request.setLog(e.getMessage()+"\n");
      logger.error(e.getMessage());
    }
    finally {
      if(dataSaver == null)
        request.persist();
      else
        dataSaver.save(document, request);
      try {
        //message.setFlag(Flags.Flag.FLAGGED, true);
        message.setFlag(Flags.Flag.DELETED, true);
        //message.setFlag(Flags.Flag.RECENT, true);
      } catch (MessagingException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
    }
  }
}
