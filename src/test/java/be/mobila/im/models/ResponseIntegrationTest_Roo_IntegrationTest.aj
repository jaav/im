// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package be.mobila.im.models;

import be.mobila.im.models.ResponseDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ResponseIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ResponseIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: ResponseIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: ResponseIntegrationTest: @Transactional;
    
    @Autowired
    private ResponseDataOnDemand ResponseIntegrationTest.dod;
    
    @Test
    public void ResponseIntegrationTest.testCountResponses() {
        org.junit.Assert.assertNotNull("Data on demand for 'Response' failed to initialize correctly", dod.getRandomResponse());
        long count = be.mobila.im.models.Response.countResponses();
        org.junit.Assert.assertTrue("Counter for 'Response' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void ResponseIntegrationTest.testFindResponse() {
        be.mobila.im.models.Response obj = dod.getRandomResponse();
        org.junit.Assert.assertNotNull("Data on demand for 'Response' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Response' failed to provide an identifier", id);
        obj = be.mobila.im.models.Response.findResponse(id);
        org.junit.Assert.assertNotNull("Find method for 'Response' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Response' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void ResponseIntegrationTest.testFindAllResponses() {
        org.junit.Assert.assertNotNull("Data on demand for 'Response' failed to initialize correctly", dod.getRandomResponse());
        long count = be.mobila.im.models.Response.countResponses();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Response', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<be.mobila.im.models.Response> result = be.mobila.im.models.Response.findAllResponses();
        org.junit.Assert.assertNotNull("Find all method for 'Response' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Response' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void ResponseIntegrationTest.testFindResponseEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Response' failed to initialize correctly", dod.getRandomResponse());
        long count = be.mobila.im.models.Response.countResponses();
        if (count > 20) count = 20;
        java.util.List<be.mobila.im.models.Response> result = be.mobila.im.models.Response.findResponseEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Response' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Response' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void ResponseIntegrationTest.testFlush() {
        be.mobila.im.models.Response obj = dod.getRandomResponse();
        org.junit.Assert.assertNotNull("Data on demand for 'Response' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Response' failed to provide an identifier", id);
        obj = be.mobila.im.models.Response.findResponse(id);
        org.junit.Assert.assertNotNull("Find method for 'Response' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyResponse(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Response' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ResponseIntegrationTest.testMerge() {
        be.mobila.im.models.Response obj = dod.getRandomResponse();
        org.junit.Assert.assertNotNull("Data on demand for 'Response' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Response' failed to provide an identifier", id);
        obj = be.mobila.im.models.Response.findResponse(id);
        boolean modified =  dod.modifyResponse(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        be.mobila.im.models.Response merged = (be.mobila.im.models.Response) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Response' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ResponseIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Response' failed to initialize correctly", dod.getRandomResponse());
        be.mobila.im.models.Response obj = dod.getNewTransientResponse(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Response' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Response' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Response' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void ResponseIntegrationTest.testRemove() {
        be.mobila.im.models.Response obj = dod.getRandomResponse();
        org.junit.Assert.assertNotNull("Data on demand for 'Response' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Response' failed to provide an identifier", id);
        obj = be.mobila.im.models.Response.findResponse(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Response' with identifier '" + id + "'", be.mobila.im.models.Response.findResponse(id));
    }
    
}