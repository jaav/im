package be.mobila.im.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import be.mobila.im.models.ImUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "imusers", formBackingObject = ImUser.class)
@RequestMapping("/imusers")
@Controller
public class ImUserController {
}
