package dbspro2.utulek.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chyba")
public class ChybaController {

    @GetMapping("/pristup")
    public String pristupOdepren(HttpServletResponse response, Model model) {
        response.setStatus(403);
        model.addAttribute("kod", "403");
        model.addAttribute("nadpis", "Přístup odepřen");
        model.addAttribute("popis", "Na tuto stránku nemáš dostatečná oprávnění. Pokud si myslíš, že jde o chybu, obrať se na administrátora.");
        return "chyba";
    }

    @GetMapping("/404")
    public String nenalezeno(HttpServletResponse response, Model model) {
        response.setStatus(404);
        model.addAttribute("kod", "404");
        model.addAttribute("nadpis", "Stránka nenalezena");
        model.addAttribute("popis", "Stránka, kterou hledáš, neexistuje nebo byla přesunuta.");
        return "chyba";
    }
}
