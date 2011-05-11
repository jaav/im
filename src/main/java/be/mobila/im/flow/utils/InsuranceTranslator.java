package be.mobila.im.flow.utils;

import be.mobila.im.models.*;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: Dec 22, 2010
 * Time: 10:13:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class InsuranceTranslator {


  private static String PCP_GAC5_POL = "PCP with GAC (5) and policy fee";
  private static String PCP_GAC5_NOPOL = "PCP with GAC (5) and no policy fee";
  private static String PCP_GAC7_POL = "PCP with GAC (8) and policy fee";
  private static String PCP_GAC7_NOPOL = "PCP with GAC (8) and no policy fee";
  private static String PCP_NOGAC_POL = "PCP with No GAC and policy fee";
  private static String PCP_NOGAC_NOPOL = "PCP with No GAC and no policy fee";

  public static LiaInsuranceSubType getLiaSubType(String type){
    if(type.trim().equals(PCP_GAC5_POL)) return LiaInsuranceSubType.PCPGAC5POL;
    else if(type.trim().equals(PCP_GAC7_POL)) return LiaInsuranceSubType.PCPGAC7POL;
    else if(type.trim().equals(PCP_GAC5_NOPOL)) return LiaInsuranceSubType.PCPGAC5NOPOL;
    else if(type.trim().equals(PCP_GAC7_NOPOL)) return LiaInsuranceSubType.PCPGAC7NOPOL;
    else if(type.trim().equals(PCP_NOGAC_POL)) return LiaInsuranceSubType.PCPNOGACPOL;
    else if(type.trim().equals(PCP_NOGAC_NOPOL)) return LiaInsuranceSubType.PCPNOGACNOPOL;
    else return null;
  }

  public static String getLiaSubType(LiaInsuranceSubType type){
    if(type.equals(LiaInsuranceSubType.PCPGAC5POL)) return PCP_GAC5_POL;
    else if(type.equals(LiaInsuranceSubType.PCPGAC7POL)) return PCP_GAC7_POL;
    else if(type.equals(LiaInsuranceSubType.PCPGAC5NOPOL)) return PCP_GAC5_NOPOL;
    else if(type.equals(LiaInsuranceSubType.PCPGAC7NOPOL)) return PCP_GAC7_NOPOL;
    else if(type.equals(LiaInsuranceSubType.PCPNOGACPOL)) return PCP_NOGAC_POL;
    else if(type.equals(LiaInsuranceSubType.PCPNOGACNOPOL)) return PCP_NOGAC_NOPOL;
    else return null;
  }

  public static String getResponseMode(ResponseMode mode){
    if(mode.equals(ResponseMode.MAIL_WA)) return "MAIL_WA";
    else if(mode.equals(ResponseMode.BB_MAIL)) return "BB_MAIL";
    else if(mode.equals(ResponseMode.FAX)) return "FAX";
    else if(mode.equals(ResponseMode.MAIL)) return "MAIL";
    else return null;
  }

  public static ResponseMode getResponseMode(String mode){
    if("MAIL_WA".equals(mode)) return ResponseMode.MAIL_WA;
    else if("BB_MAIL".equals(mode)) return ResponseMode.BB_MAIL;
    else if("FAX".equals(mode)) return ResponseMode.FAX;
    else if("MAIL".equals(mode)) return ResponseMode.MAIL;
    else return null;
  }

  public static InsuranceValue getResult(Long valueId, InsuranceType insuranceType){
    if(insuranceType.equals(InsuranceType.LIFE)) return LiaValue.findLiaValue(valueId);
    else return null;
  }
}
