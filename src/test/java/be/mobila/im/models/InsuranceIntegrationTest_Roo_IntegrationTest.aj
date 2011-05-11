// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package be.mobila.im.models;

import be.mobila.im.models.InsuranceDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect InsuranceIntegrationTest_Roo_IntegrationTest {
    
    declare @type: InsuranceIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: InsuranceIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: InsuranceIntegrationTest: @Transactional;
    
    @Autowired
    private InsuranceDataOnDemand InsuranceIntegrationTest.dod;
    
    @Test
    public void InsuranceIntegrationTest.testCountInsurances() {
        org.junit.Assert.assertNotNull("Data on demand for 'Insurance' failed to initialize correctly", dod.getRandomInsurance());
        long count = be.mobila.im.models.Insurance.countInsurances();
        org.junit.Assert.assertTrue("Counter for 'Insurance' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void InsuranceIntegrationTest.testFindInsurance() {
        be.mobila.im.models.Insurance obj = dod.getRandomInsurance();
        org.junit.Assert.assertNotNull("Data on demand for 'Insurance' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Insurance' failed to provide an identifier", id);
        obj = be.mobila.im.models.Insurance.findInsurance(id);
        org.junit.Assert.assertNotNull("Find method for 'Insurance' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Insurance' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void InsuranceIntegrationTest.testFindAllInsurances() {
        org.junit.Assert.assertNotNull("Data on demand for 'Insurance' failed to initialize correctly", dod.getRandomInsurance());
        long count = be.mobila.im.models.Insurance.countInsurances();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Insurance', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<be.mobila.im.models.Insurance> result = be.mobila.im.models.Insurance.findAllInsurances();
        org.junit.Assert.assertNotNull("Find all method for 'Insurance' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Insurance' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void InsuranceIntegrationTest.testFindInsuranceEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Insurance' failed to initialize correctly", dod.getRandomInsurance());
        long count = be.mobila.im.models.Insurance.countInsurances();
        if (count > 20) count = 20;
        java.util.List<be.mobila.im.models.Insurance> result = be.mobila.im.models.Insurance.findInsuranceEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Insurance' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Insurance' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void InsuranceIntegrationTest.testFlush() {
        be.mobila.im.models.Insurance obj = dod.getRandomInsurance();
        org.junit.Assert.assertNotNull("Data on demand for 'Insurance' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Insurance' failed to provide an identifier", id);
        obj = be.mobila.im.models.Insurance.findInsurance(id);
        org.junit.Assert.assertNotNull("Find method for 'Insurance' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyInsurance(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Insurance' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void InsuranceIntegrationTest.testMerge() {
        be.mobila.im.models.Insurance obj = dod.getRandomInsurance();
        org.junit.Assert.assertNotNull("Data on demand for 'Insurance' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Insurance' failed to provide an identifier", id);
        obj = be.mobila.im.models.Insurance.findInsurance(id);
        boolean modified =  dod.modifyInsurance(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        be.mobila.im.models.Insurance merged = (be.mobila.im.models.Insurance) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Insurance' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void InsuranceIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Insurance' failed to initialize correctly", dod.getRandomInsurance());
        be.mobila.im.models.Insurance obj = dod.getNewTransientInsurance(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Insurance' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Insurance' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Insurance' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void InsuranceIntegrationTest.testRemove() {
        be.mobila.im.models.Insurance obj = dod.getRandomInsurance();
        org.junit.Assert.assertNotNull("Data on demand for 'Insurance' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Insurance' failed to provide an identifier", id);
        obj = be.mobila.im.models.Insurance.findInsurance(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Insurance' with identifier '" + id + "'", be.mobila.im.models.Insurance.findInsurance(id));
    }
    
}
