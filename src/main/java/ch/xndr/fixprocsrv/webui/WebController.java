package ch.xndr.fixprocsrv.webui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index"; // Renders src/main/resources/templates/index.html
    }
}