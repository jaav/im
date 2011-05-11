package be.mobila.im.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import be.mobila.im.models.LiaValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "liavalues", formBackingObject = LiaValue.class)
@RequestMapping("/liavalues")
@Controller
public class LiaValueController {
}
