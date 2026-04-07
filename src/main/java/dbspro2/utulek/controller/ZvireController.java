package dbspro2.utulek.controller;

import dbspro2.utulek.model.*;
import dbspro2.utulek.repository.*;
import dbspro2.utulek.service.ZvireService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/zvirata")
public class ZvireController {

    private final ZvireService zvireService;
    private final PlemenoRepository plemenoRepository;
    private final DruhRepository druhRepository;
    private final StatusZvireteRepository statusRepository;

    public ZvireController(ZvireService zvireService,
                           PlemenoRepository plemenoRepository,
                           DruhRepository druhRepository,
                           StatusZvireteRepository statusRepository) {
        this.zvireService      = zvireService;
        this.plemenoRepository = plemenoRepository;
        this.druhRepository    = druhRepository;
        this.statusRepository  = statusRepository;
    }

    // ===== SEZNAM =====
    @GetMapping
    public String seznam(@RequestParam(required = false) String jmeno,
                         @RequestParam(required = false) Integer idStatus,
                         @RequestParam(required = false) Integer idPlemeno,
                         Model model) {
        List<Zvire> zvirata;
        if (jmeno != null && !jmeno.isBlank()) {
            zvirata = zvireService.searchByName(jmeno);
        } else if (idStatus != null) {
            zvirata = zvireService.filterByStatus(idStatus);
        } else if (idPlemeno != null) {
            zvirata = zvireService.filterByPlemeno(idPlemeno);
        } else {
            zvirata = zvireService.getAll();
        }
        model.addAttribute("zvirata", zvirata);
        model.addAttribute("statusy", statusRepository.findAll());
        model.addAttribute("plemena", plemenoRepository.findAll());
        model.addAttribute("druhy", druhRepository.findAll());
        model.addAttribute("filtrJmeno", jmeno);
        model.addAttribute("filtrStatus", idStatus);
        model.addAttribute("filtrPlemeno", idPlemeno);
        return "zvirata/seznam";
    }

    // ===== DETAIL =====
    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Zvire zvire = zvireService.getById(id)
                .orElseThrow(() -> new RuntimeException("Zvíře nenalezeno"));
        model.addAttribute("zvire", zvire);
        model.addAttribute("statusy", statusRepository.findAll());
        return "zvirata/detail";
    }

    // ===== FORMULÁŘ – NOVÉ ZVÍŘE =====
    @GetMapping("/novy")
    public String formularNovy(Model model) {
        model.addAttribute("zvire", new Zvire());
        model.addAttribute("plemena", plemenoRepository.findAll());
        model.addAttribute("statusy", statusRepository.findAll());
        model.addAttribute("druhy", druhRepository.findAll());
        return "zvirata/formular";
    }

    // ===== FORMULÁŘ – ÚPRAVA =====
    @GetMapping("/{id}/upravit")
    public String formularUprava(@PathVariable Integer id, Model model) {
        Zvire zvire = zvireService.getById(id)
                .orElseThrow(() -> new RuntimeException("Zvíře nenalezeno"));
        model.addAttribute("zvire", zvire);
        model.addAttribute("plemena", plemenoRepository.findAll());
        model.addAttribute("statusy", statusRepository.findAll());
        model.addAttribute("druhy", druhRepository.findAll());
        return "zvirata/formular";
    }

    // ===== ULOŽENÍ (nové i úprava) =====
    @PostMapping("/ulozit")
    public String ulozit(@ModelAttribute Zvire zvire, RedirectAttributes ra) {
        zvireService.save(zvire);
        ra.addFlashAttribute("zprava", "Zvíře bylo úspěšně uloženo.");
        return "redirect:/zvirata";
    }

    // ===== AKTUALIZACE STATUSU (recepční) =====
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Integer id,
                               @RequestParam Integer idStatus,
                               RedirectAttributes ra) {
        zvireService.updateStatus(id, idStatus);
        ra.addFlashAttribute("zprava", "Status byl aktualizován.");
        return "redirect:/zvirata/" + id;
    }

    // ===== SMAZÁNÍ =====
    @PostMapping("/{id}/smazat")
    public String smazat(@PathVariable Integer id, RedirectAttributes ra) {
        zvireService.delete(id);
        ra.addFlashAttribute("zprava", "Zvíře bylo odstraněno.");
        return "redirect:/zvirata";
    }
}
