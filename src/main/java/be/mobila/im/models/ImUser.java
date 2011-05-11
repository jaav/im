package be.mobila.im.models;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findImUsersByIdentifierEquals" })
public class ImUser {

    private String name;

    private String firstname;

    private String identifier;
}
