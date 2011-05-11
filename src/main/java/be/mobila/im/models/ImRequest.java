package be.mobila.im.models;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import be.mobila.im.models.Person;
import javax.persistence.ManyToOne;
import be.mobila.im.models.Insurance;
import be.mobila.im.models.RequestMode;
import javax.persistence.Enumerated;
import be.mobila.im.models.ImUser;
import be.mobila.im.models.RequestFor;

@RooJavaBean
@RooToString
@RooEntity
public class ImRequest {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date requestDate;

    @ManyToOne
    private Person person;

    @ManyToOne
    private Insurance insurance;

    @Enumerated
    private RequestMode requestMode;

    @ManyToOne
    private ImUser imuser;

    private String uri;

    @Enumerated
    private RequestFor requestFor;

    private String log;
}
