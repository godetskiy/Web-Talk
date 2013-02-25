package HelloSpring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/")
public class HelloController {

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        String str = sdf.format(new Date());

        model.addAttribute("dateTime", str);
        return "index";

    }

}