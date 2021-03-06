// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package be.mobila.im.models;

import be.mobila.im.models.Person;
import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect Person_Roo_Finder {
    
    public static TypedQuery<Person> Person.findPeopleByMailEquals(String mail) {
        if (mail == null || mail.length() == 0) throw new IllegalArgumentException("The mail argument is required");
        EntityManager em = Person.entityManager();
        TypedQuery<Person> q = em.createQuery("SELECT Person FROM Person AS person WHERE person.mail = :mail", Person.class);
        q.setParameter("mail", mail);
        return q;
    }
    
}
