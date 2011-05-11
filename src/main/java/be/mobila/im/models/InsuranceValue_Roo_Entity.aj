// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package be.mobila.im.models;

import be.mobila.im.models.InsuranceValue;
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

privileged aspect InsuranceValue_Roo_Entity {
    
    declare @type: InsuranceValue: @Entity;
    
    @PersistenceContext
    transient EntityManager InsuranceValue.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long InsuranceValue.id;
    
    @Version
    @Column(name = "version")
    private Integer InsuranceValue.version;
    
    public Long InsuranceValue.getId() {
        return this.id;
    }
    
    public void InsuranceValue.setId(Long id) {
        this.id = id;
    }
    
    public Integer InsuranceValue.getVersion() {
        return this.version;
    }
    
    public void InsuranceValue.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void InsuranceValue.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void InsuranceValue.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            InsuranceValue attached = this.entityManager.find(this.getClass(), this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void InsuranceValue.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public InsuranceValue InsuranceValue.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        InsuranceValue merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager InsuranceValue.entityManager() {
        EntityManager em = new InsuranceValue(){
        }.entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long InsuranceValue.countInsuranceValues() {
        return entityManager().createQuery("select count(o) from InsuranceValue o", Long.class).getSingleResult();
    }
    
    public static List<InsuranceValue> InsuranceValue.findAllInsuranceValues() {
        return entityManager().createQuery("select o from InsuranceValue o", InsuranceValue.class).getResultList();
    }
    
    public static InsuranceValue InsuranceValue.findInsuranceValue(Long id) {
        if (id == null) return null;
        return entityManager().find(InsuranceValue.class, id);
    }
    
    public static List<InsuranceValue> InsuranceValue.findInsuranceValueEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from InsuranceValue o", InsuranceValue.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}