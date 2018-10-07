package sk.stuba.fiit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class StorageController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
