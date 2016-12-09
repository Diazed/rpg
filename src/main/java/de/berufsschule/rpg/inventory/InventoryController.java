package de.berufsschule.rpg.inventory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InventoryController {

    @RequestMapping(value = "/prle", method = RequestMethod.GET)
    public String test(){
        return "test";
    }

}
