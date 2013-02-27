package HelloSpring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class HomeController {
	@RequestMapping(value="/")
    public String test(ModelMap model) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        String str = sdf.format(new Date());
        model.addAttribute("datetime", str);
        return "home";
    }
}