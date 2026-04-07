package dbspro2.utulek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/recepce")
    public String recepce() { return "recepce"; }

    @GetMapping("/veterinar")
    public String veterinar() { return "veterinar"; }

    @GetMapping("/admin")
    public String admin() { return "admin"; }
}
