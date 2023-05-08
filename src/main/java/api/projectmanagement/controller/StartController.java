package api.projectmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StartController {
    @GetMapping("/")
    public ModelAndView homepage() {
        return new ModelAndView("index");
    }
}
