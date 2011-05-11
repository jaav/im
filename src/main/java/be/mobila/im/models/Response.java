package be.mobila.im.models;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import java.util.Date;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import be.mobila.im.models.ResponseMode;
import be.mobila.im.models.ImRequest;
import be.mobila.im.models.Status;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findResponsesByStatus" })
public class Response {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date responseDate;

    @Enumerated
    private ResponseMode responseMode;

    @ManyToOne
    private ImRequest imRequest;

    @Enumerated
    private Status status;

    @Column(columnDefinition = "LONGTEXT")
    private String imresult;

    private String address;

    private Long valueId;

  
}
