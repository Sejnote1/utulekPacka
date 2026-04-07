package dbspro2.utulek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login"; // vrátí Thymeleaf šablonu login.html
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // stránka po přihlášení
    }
}
