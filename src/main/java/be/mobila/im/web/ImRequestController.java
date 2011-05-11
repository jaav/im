package be.mobila.im.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import be.mobila.im.models.ImRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@RooWebScaffold(path = "imrequests", formBackingObject = ImRequest.class)
@RequestMapping("/imrequests")
@Controller
public class ImRequestController {
}
