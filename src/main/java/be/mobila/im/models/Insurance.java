package be.mobila.im.models;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.entity.RooEntity;
import be.mobila.im.models.InsuranceType;
import javax.persistence.Enumerated;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findInsurancesByInsuranceType", "findInsurancesByImportFile" })
public class Insurance {

    @Enumerated
    private InsuranceType InsuranceType;

    private String importFile;

    private String converter;

    private String abstractDataSaver;

    private String pdfTemplate;
}
