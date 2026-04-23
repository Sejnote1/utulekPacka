package dbspro2.utulek.controller;

import dbspro2.utulek.model.*;
import dbspro2.utulek.repository.*;
import dbspro2.utulek.service.AdopceService;
import dbspro2.utulek.service.ZajemceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/adopce")
@org.springframework.transaction.annotation.Transactional
public class AdopceController {

    private final AdopceService adopceService;
    private final ZajemceService zajemceService;
    private final ZvireRepository zvireRepository;
    private final DuvodZamitnutiRepository duvodRepository;

    public AdopceController(AdopceService adopceService,
                            ZajemceService zajemceService,
                            ZvireRepository zvireRepository,
                            DuvodZamitnutiRepository duvodRepository) {
        this.adopceService  = adopceService;
        this.zajemceService = zajemceService;
        this.zvireRepository = zvireRepository;
        this.duvodRepository = duvodRepository;
    }

    // ===== SEZNAM ADOPCÍ =====
    @GetMapping
    public String seznam(@RequestParam(required = false) String stav, Model model) {
        List<Adopce> adopce;
        if (stav != null && !stav.isBlank()) {
            adopce = adopceService.getByStav(stav);
        } else {
            adopce = adopceService.getAll();
        }
        model.addAttribute("adopce", adopce);
        model.addAttribute("filtrStav", stav);
        return "adopce/seznam";
    }

    // ===== DETAIL ADOPCE =====
    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Adopce adopce = adopceService.getById(id)
                .orElseThrow(() -> new RuntimeException("Adopce nenalezena"));
        model.addAttribute("adopce", adopce);
        model.addAttribute("duvody", duvodRepository.findAll());
        return "adopce/detail";
    }

    // ===== FORMULÁŘ – NOVÁ ŽÁDOST =====
    @GetMapping("/nova")
    public String formularNovy(Model model) {
        model.addAttribute("adopce", new Adopce());
        model.addAttribute("zvirata", zvireRepository.findAll());
        model.addAttribute("zajemci", zajemceService.getAll());
        return "adopce/formular";
    }

    // ===== ULOŽENÍ ŽÁDOSTI =====
    @PostMapping("/ulozit")
    public String ulozit(@ModelAttribute Adopce adopce, RedirectAttributes ra) {
        adopceService.vytvorAdopci(adopce);
        ra.addFlashAttribute("zprava", "Žádost o adopci byla zaregistrována.");
        return "redirect:/adopce";
    }

    // ===== SCHVÁLENÍ =====
    @PostMapping("/{id}/schvalit")
    public String schvalit(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            adopceService.schvalAdopci(id);
            ra.addFlashAttribute("zprava", "Adopce byla schválena. Status zvířete byl změněn na 'Adoptováno'.");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("chyba", e.getMessage());
        }
        return "redirect:/adopce/" + id;
    }

    // ===== ZAMÍTNUTÍ =====
    @PostMapping("/{id}/zamitnout")
    public String zamitnout(@PathVariable Integer id,
                            @RequestParam(required = false) List<Integer> duvodIds,
                            RedirectAttributes ra) {
        adopceService.zamitniAdopci(id, duvodIds);
        ra.addFlashAttribute("zprava", "Adopce byla zamítnuta.");
        return "redirect:/adopce/" + id;
    }

    // ===== SMAZÁNÍ =====
    @PostMapping("/{id}/smazat")
    public String smazat(@PathVariable Integer id, RedirectAttributes ra) {
        adopceService.delete(id);
        ra.addFlashAttribute("zprava", "Žádost o adopci byla odstraněna.");
        return "redirect:/adopce";
    }
}