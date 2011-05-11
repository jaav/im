package be.mobila.im.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import be.mobila.im.models.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "people", formBackingObject = Person.class)
@RequestMapping("/people")
@Controller
public class PersonController {
}
