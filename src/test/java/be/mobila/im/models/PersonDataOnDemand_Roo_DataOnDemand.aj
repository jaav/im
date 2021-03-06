// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package be.mobila.im.models;

import be.mobila.im.models.Person;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect PersonDataOnDemand_Roo_DataOnDemand {
    
    declare @type: PersonDataOnDemand: @Component;
    
    private Random PersonDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Person> PersonDataOnDemand.data;
    
    public Person PersonDataOnDemand.getNewTransientPerson(int index) {
        be.mobila.im.models.Person obj = new be.mobila.im.models.Person();
        obj.setAddress("address_" + index);
        obj.setBirthdate(new java.util.Date());
        obj.setCity("city_" + index);
        obj.setCountry("country_" + index);
        obj.setFax("fax_" + index);
        obj.setFirstname("firstname_" + index);
        obj.setGender("gender_" + index);
        obj.setMail("mail_" + index);
        obj.setName("name_" + index);
        obj.setNationality("nationality_" + index);
        obj.setPhone1("phone1_" + index);
        obj.setPhone2("phone2_" + index);
        obj.setPlaceofbirth("placeofbirth_" + index);
        obj.setPostalcode("postalcode_" + index);
        return obj;
    }
    
    public Person PersonDataOnDemand.getSpecificPerson(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Person obj = data.get(index);
        return Person.findPerson(obj.getId());
    }
    
    public Person PersonDataOnDemand.getRandomPerson() {
        init();
        Person obj = data.get(rnd.nextInt(data.size()));
        return Person.findPerson(obj.getId());
    }
    
    public boolean PersonDataOnDemand.modifyPerson(Person obj) {
        return false;
    }
    
    public void PersonDataOnDemand.init() {
        data = be.mobila.im.models.Person.findPersonEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Person' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<be.mobila.im.models.Person>();
        for (int i = 0; i < 10; i++) {
            be.mobila.im.models.Person obj = getNewTransientPerson(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}
