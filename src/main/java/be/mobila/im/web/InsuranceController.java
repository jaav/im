package be.mobila.im.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import be.mobila.im.models.Insurance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "insurances", formBackingObject = Insurance.class)
@RequestMapping("/insurances")
@Controller
public class InsuranceController {
}
