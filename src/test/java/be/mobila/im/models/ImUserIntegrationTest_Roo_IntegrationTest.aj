// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package be.mobila.im.models;

import be.mobila.im.models.ImUserDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ImUserIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ImUserIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: ImUserIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: ImUserIntegrationTest: @Transactional;
    
    @Autowired
    private ImUserDataOnDemand ImUserIntegrationTest.dod;
    
    @Test
    public void ImUserIntegrationTest.testCountImUsers() {
        org.junit.Assert.assertNotNull("Data on demand for 'ImUser' failed to initialize correctly", dod.getRandomImUser());
        long count = be.mobila.im.models.ImUser.countImUsers();
        org.junit.Assert.assertTrue("Counter for 'ImUser' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void ImUserIntegrationTest.testFindImUser() {
        be.mobila.im.models.ImUser obj = dod.getRandomImUser();
        org.junit.Assert.assertNotNull("Data on demand for 'ImUser' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'ImUser' failed to provide an identifier", id);
        obj = be.mobila.im.models.ImUser.findImUser(id);
        org.junit.Assert.assertNotNull("Find method for 'ImUser' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'ImUser' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void ImUserIntegrationTest.testFindAllImUsers() {
        org.junit.Assert.assertNotNull("Data on demand for 'ImUser' failed to initialize correctly", dod.getRandomImUser());
        long count = be.mobila.im.models.ImUser.countImUsers();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'ImUser', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<be.mobila.im.models.ImUser> result = be.mobila.im.models.ImUser.findAllImUsers();
        org.junit.Assert.assertNotNull("Find all method for 'ImUser' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'ImUser' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void ImUserIntegrationTest.testFindImUserEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'ImUser' failed to initialize correctly", dod.getRandomImUser());
        long count = be.mobila.im.models.ImUser.countImUsers();
        if (count > 20) count = 20;
        java.util.List<be.mobila.im.models.ImUser> result = be.mobila.im.models.ImUser.findImUserEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'ImUser' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'ImUser' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void ImUserIntegrationTest.testFlush() {
        be.mobila.im.models.ImUser obj = dod.getRandomImUser();
        org.junit.Assert.assertNotNull("Data on demand for 'ImUser' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'ImUser' failed to provide an identifier", id);
        obj = be.mobila.im.models.ImUser.findImUser(id);
        org.junit.Assert.assertNotNull("Find method for 'ImUser' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyImUser(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'ImUser' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ImUserIntegrationTest.testMerge() {
        be.mobila.im.models.ImUser obj = dod.getRandomImUser();
        org.junit.Assert.assertNotNull("Data on demand for 'ImUser' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'ImUser' failed to provide an identifier", id);
        obj = be.mobila.im.models.ImUser.findImUser(id);
        boolean modified =  dod.modifyImUser(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        be.mobila.im.models.ImUser merged = (be.mobila.im.models.ImUser) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'ImUser' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ImUserIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'ImUser' failed to initialize correctly", dod.getRandomImUser());
        be.mobila.im.models.ImUser obj = dod.getNewTransientImUser(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'ImUser' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'ImUser' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'ImUser' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void ImUserIntegrationTest.testRemove() {
        be.mobila.im.models.ImUser obj = dod.getRandomImUser();
        org.junit.Assert.assertNotNull("Data on demand for 'ImUser' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'ImUser' failed to provide an identifier", id);
        obj = be.mobila.im.models.ImUser.findImUser(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'ImUser' with identifier '" + id + "'", be.mobila.im.models.ImUser.findImUser(id));
    }
    
}
