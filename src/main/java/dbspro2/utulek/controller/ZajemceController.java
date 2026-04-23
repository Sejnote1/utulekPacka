package dbspro2.utulek.controller;

import dbspro2.utulek.model.Zajemce;
import dbspro2.utulek.service.ZajemceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/zajemci")
@org.springframework.transaction.annotation.Transactional
public class ZajemceController {

    private final ZajemceService zajemceService;

    public ZajemceController(ZajemceService zajemceService) {
        this.zajemceService = zajemceService;
    }

    @GetMapping
    public String seznam(@RequestParam(required = false) String hledat, Model model) {
        if (hledat != null && !hledat.isBlank()) {
            model.addAttribute("zajemci", zajemceService.search(hledat));
        } else {
            model.addAttribute("zajemci", zajemceService.getAll());
        }
        model.addAttribute("hledat", hledat);
        return "zajemci/seznam";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("zajemce", zajemceService.getById(id)
                .orElseThrow(() -> new RuntimeException("Zájemce nenalezen")));
        
        zajemceService.getSkore(id).ifPresent(skore -> model.addAttribute("skore", skore));
        
        return "zajemci/detail";
    }

    @GetMapping("/novy")
    public String formularNovy(Model model) {
        model.addAttribute("zajemce", new Zajemce());
        return "zajemci/formular";
    }

    @GetMapping("/{id}/upravit")
    public String formularUprava(@PathVariable Integer id, Model model) {
        model.addAttribute("zajemce", zajemceService.getById(id)
                .orElseThrow(() -> new RuntimeException("Zájemce nenalezen")));
        return "zajemci/formular";
    }

    @PostMapping("/ulozit")
    public String ulozit(@ModelAttribute Zajemce zajemce, RedirectAttributes ra) {
        zajemceService.save(zajemce);
        ra.addFlashAttribute("zprava", "Zájemce byl uložen.");
        return "redirect:/zajemci";
    }

    @PostMapping("/{id}/smazat")
    public String smazat(@PathVariable Integer id, RedirectAttributes ra) {
        zajemceService.delete(id);
        ra.addFlashAttribute("zprava", "Zájemce byl odstraněn.");
        return "redirect:/zajemci";
    }

    @PostMapping("/{id}/anonymizovat")
    public String anonymizovat(@PathVariable Integer id, RedirectAttributes ra) {
        zajemceService.anonymizuj(id);
        ra.addFlashAttribute("zprava", "Osobní údaje zájemce byly v databázi anonymizovány přes nativní Stored Procedure.");
        return "redirect:/zajemci";
    }
}
