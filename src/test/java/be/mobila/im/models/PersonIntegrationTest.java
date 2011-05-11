package be.mobila.im.models;

import org.springframework.roo.addon.test.RooIntegrationTest;
import be.mobila.im.models.Person;
import org.junit.Test;

@RooIntegrationTest(entity = Person.class)
public class PersonIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
}
