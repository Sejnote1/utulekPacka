package dbspro2.utulek.controller;

import dbspro2.utulek.model.VeterinarniZaznam;
import dbspro2.utulek.model.Zvire;
import dbspro2.utulek.repository.UzivatelRepository;
import dbspro2.utulek.repository.ZvireRepository;
import dbspro2.utulek.service.VeterinarniZaznamService;
import dbspro2.utulek.security.UzivatelDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/zaznamy")
@org.springframework.transaction.annotation.Transactional
public class VeterinarniZaznamController {

    private final VeterinarniZaznamService zaznamService;
    private final ZvireRepository zvireRepository;
    private final UzivatelRepository uzivatelRepository;

    public VeterinarniZaznamController(VeterinarniZaznamService zaznamService,
                                       ZvireRepository zvireRepository,
                                       UzivatelRepository uzivatelRepository) {
        this.zaznamService    = zaznamService;
        this.zvireRepository  = zvireRepository;
        this.uzivatelRepository = uzivatelRepository;
    }

    // ===== FORMULÁŘ =====
    @GetMapping("/novy")
    public String formular(@RequestParam Integer idZvire, Model model) {
        Zvire zvire = zvireRepository.findById(idZvire)
                .orElseThrow(() -> new RuntimeException("Zvíře nenalezeno"));
        model.addAttribute("zaznam", new VeterinarniZaznam());
        model.addAttribute("zvire", zvire);
        return "zaznamy/formular";
    }

    // ===== ULOŽENÍ =====
    @PostMapping("/ulozit")
    public String ulozit(@RequestParam Integer idZvire,
                         @RequestParam String popis,
                         @RequestParam(required = false) String datum,
                         @AuthenticationPrincipal UzivatelDetails currentUser,
                         RedirectAttributes ra) {

        Zvire zvire = zvireRepository.findById(idZvire)
                .orElseThrow(() -> new RuntimeException("Zvíře nenalezeno: " + idZvire));

        VeterinarniZaznam zaznam = new VeterinarniZaznam();
        zaznam.setZvire(zvire);
        zaznam.setPopis(popis);
        zaznam.setDatum(datum != null && !datum.isBlank()
                ? LocalDate.parse(datum)
                : LocalDate.now());

        // Přiřadit přihlášeného veterináře
        uzivatelRepository.findByEmail(currentUser.getUsername())
                .ifPresent(zaznam::setUzivatel);

        zaznamService.create(zaznam);
        ra.addFlashAttribute("zprava", "Veterinární záznam byl úspěšně uložen.");
        return "redirect:/zvirata/" + idZvire;
    }

    // ===== SMAZÁNÍ =====
    @PostMapping("/{id}/smazat")
    public String smazat(@PathVariable Integer id,
                         @RequestParam Integer idZvire,
                         RedirectAttributes ra) {
        zaznamService.delete(id);
        ra.addFlashAttribute("zprava", "Záznam byl odstraněn.");
        return "redirect:/zvirata/" + idZvire;
    }
}