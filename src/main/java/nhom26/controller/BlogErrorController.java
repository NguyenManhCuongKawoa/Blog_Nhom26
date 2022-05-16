package nhom26.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlogErrorController {

    @GetMapping("/error")
    public ModelAndView error() {
        return new ModelAndView("/error");
    }

    @GetMapping("/403")
    public ModelAndView error403() {
        return new ModelAndView("/403");
    }

}
