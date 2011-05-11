package be.mobila.im.models;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import javax.validation.constraints.Digits;
import be.mobila.im.models.Insurance;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
public abstract class InsuranceValue {

    @Digits(integer = 2, fraction = 30)
    private Double proposal;

    @ManyToOne
    private Insurance insurance;
}
