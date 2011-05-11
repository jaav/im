package be.mobila.im.utils;

import be.mobila.im.models.*;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 13, 2010
 * Time: 5:17:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBFiller {
  static Logger logger = Logger.getLogger(DBFiller.class);

  public void fill(){
    List<Response> responses = Response.findAllResponses();
    for (Response response : responses) {
      response.remove();
    }
    List<ImRequest> requests = ImRequest.findAllImRequests();
    for (ImRequest request : requests) {
      request.remove();
    } 
    List<LiaValue> liaValues = LiaValue.findAllLiaValues();
    for (LiaValue liaValue : liaValues) {
      liaValue.remove();
    }
    List<Insurance> insurances = Insurance.findAllInsurances();
    for (Insurance insurance : insurances) {
      insurance.remove();
    }
    List<ImUser> users = ImUser.findAllImUsers();
    for (ImUser user : users) {
      user.remove();
    }
    ImUser jef = createJef();
    logger.debug("Jef added. The new id is "+jef.getId());
    ImUser piet = createPiet();
    logger.debug("Piet added. The new id is "+piet.getId());
    ImUser jan = createJan();
    logger.debug("Jan added. The new id is "+jan.getId());
    createLifeInsurance();
  }

  private void createLifeInsurance(){
    Insurance insurance = new Insurance();
    insurance.setInsuranceType(InsuranceType.LIFE);
    insurance.setImportFile("SM_PCP.xls");
    insurance.setConverter("liaConverter");
    insurance.setPdfTemplate("liaTemplate");
    insurance.setAbstractDataSaver("liaDataSaver");
    insurance.persist();
  }

  private ImUser createJef(){
      ImUser imUser = new ImUser();
      imUser.setFirstname("Jef");
      imUser.setIdentifier("jef@waumans.net");
      imUser.setName("Waumans");
      imUser.persist();
      return imUser;
  }

  private ImUser createPiet(){
      ImUser imUser = new ImUser();
      imUser.setFirstname("Piet");
      imUser.setIdentifier("piet@colruyt.be");
      imUser.setName("Colruyt");
      imUser.persist();
      return imUser;
  }

  private ImUser createJan(){
      ImUser imUser = new ImUser();
      imUser.setFirstname("Jan");
      imUser.setIdentifier("jan@janssens.be");
      imUser.setName("Janssens");
      imUser.persist();
      return imUser;
  }
}
