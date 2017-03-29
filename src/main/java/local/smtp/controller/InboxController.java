package local.smtp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InboxController {
	@RequestMapping("/inbox")
    public String hello(Model model) {
        model.addAttribute("name", "aaa");
        return "inbox";
    }
	
}
