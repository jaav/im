package be.mobila.im.models;

import be.mobila.im.models.InsuranceValue;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import be.mobila.im.models.LiaInsuranceSubType;
import javax.persistence.Enumerated;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findLiaValuesByRateBetween", "findLiaValuesByRateIsNotNull", "findLiaValuesByRateEquals" })
public class LiaValue extends InsuranceValue {

    private Long amount;

    private Double rate;

    @Enumerated
    private LiaInsuranceSubType insuranceSubType;
}
