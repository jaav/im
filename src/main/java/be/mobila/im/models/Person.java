package be.mobila.im.models;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findPeopleByMailEquals" })
public class Person {

    private String name;

    private String firstname;

    private String address;

    private String postalcode;

    private String city;

    private String country;

    private String nationality;

    private String gender;

    private String placeofbirth;

    private String phone1;

    private String phone2;

    private String mail;

    private String fax;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date birthdate;
}
