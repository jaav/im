// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package be.mobila.im.models;

import be.mobila.im.models.ImUser;
import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect ImUser_Roo_Finder {
    
    public static TypedQuery<ImUser> ImUser.findImUsersByIdentifierEquals(String identifier) {
        if (identifier == null || identifier.length() == 0) throw new IllegalArgumentException("The identifier argument is required");
        EntityManager em = ImUser.entityManager();
        TypedQuery<ImUser> q = em.createQuery("SELECT ImUser FROM ImUser AS imuser WHERE imuser.identifier = :identifier", ImUser.class);
        q.setParameter("identifier", identifier);
        return q;
    }
    
}
