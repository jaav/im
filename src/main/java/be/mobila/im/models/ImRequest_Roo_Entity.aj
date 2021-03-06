// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package be.mobila.im.models;

import be.mobila.im.models.ImRequest;
import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ImRequest_Roo_Entity {
    
    declare @type: ImRequest: @Entity;
    
    @PersistenceContext
    transient EntityManager ImRequest.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ImRequest.id;
    
    @Version
    @Column(name = "version")
    private Integer ImRequest.version;
    
    public Long ImRequest.getId() {
        return this.id;
    }
    
    public void ImRequest.setId(Long id) {
        this.id = id;
    }
    
    public Integer ImRequest.getVersion() {
        return this.version;
    }
    
    public void ImRequest.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void ImRequest.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ImRequest.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ImRequest attached = this.entityManager.find(this.getClass(), this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ImRequest.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public ImRequest ImRequest.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ImRequest merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager ImRequest.entityManager() {
        EntityManager em = new ImRequest().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ImRequest.countImRequests() {
        return entityManager().createQuery("select count(o) from ImRequest o", Long.class).getSingleResult();
    }
    
    public static List<ImRequest> ImRequest.findAllImRequests() {
        return entityManager().createQuery("select o from ImRequest o", ImRequest.class).getResultList();
    }
    
    public static ImRequest ImRequest.findImRequest(Long id) {
        if (id == null) return null;
        return entityManager().find(ImRequest.class, id);
    }
    
    public static List<ImRequest> ImRequest.findImRequestEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from ImRequest o", ImRequest.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
