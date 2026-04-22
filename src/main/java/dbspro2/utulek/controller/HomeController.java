package dbspro2.utulek.controller;

import dbspro2.utulek.model.Uzivatel;
import dbspro2.utulek.repository.RoleRepository;
import dbspro2.utulek.service.AdopceService;
import dbspro2.utulek.service.UzivatelService;
import dbspro2.utulek.service.ZvireService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    private final ZvireService zvireService;
    private final AdopceService adopceService;
    private final UzivatelService uzivatelService;
    private final RoleRepository roleRepository;

    public HomeController(ZvireService zvireService,
                          AdopceService adopceService,
                          UzivatelService uzivatelService,
                          RoleRepository roleRepository) {
        this.zvireService    = zvireService;
        this.adopceService   = adopceService;
        this.uzivatelService = uzivatelService;
        this.roleRepository  = roleRepository;
    }

    // ===== ADMIN DASHBOARD =====
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("pocetZvirat",    zvireService.getAll().size());
        model.addAttribute("pocetAdopci",    adopceService.getAll().size());
        model.addAttribute("pocetUzivatelu", uzivatelService.getAll().size());
        model.addAttribute("adopceProbiha",  adopceService.getByStav("Probíhá").size());
        return "admin";
    }

    // ===== SEZNAM UŽIVATELŮ =====
    @GetMapping("/uzivatele")
    public String uzivatele(Model model) {
        model.addAttribute("uzivatele", uzivatelService.getAll());
        return "uzivatele/seznam";
    }

    // ===== FORMULÁŘ – NOVÝ UŽIVATEL =====
    @GetMapping("/uzivatele/novy")
    public String uzivatelNovy(Model model) {
        model.addAttribute("uzivatel", new Uzivatel());
        model.addAttribute("role", roleRepository.findAll());
        return "uzivatele/formular";
    }

    // ===== FORMULÁŘ – ÚPRAVA UŽIVATELE =====
    @GetMapping("/uzivatele/{id}/upravit")
    public String uzivatelUprava(@PathVariable Integer id, Model model) {
        Uzivatel u = uzivatelService.getById(id)
                .orElseThrow(() -> new RuntimeException("Uživatel nenalezen"));
        // Vymažeme hash hesla – formulář ho nezobrazuje, jen přijme nové
        u.setHesloHash("");
        model.addAttribute("uzivatel", u);
        model.addAttribute("role", roleRepository.findAll());
        return "uzivatele/formular";
    }

    // ===== ULOŽENÍ UŽIVATELE =====
    @PostMapping("/uzivatele/ulozit")
    public String uzivatelUlozit(@ModelAttribute Uzivatel uzivatel,
                                 @RequestParam(value = "avatar_file", required = false) org.springframework.web.multipart.MultipartFile avatarFile,
                                 RedirectAttributes ra) {
        boolean novy = (uzivatel.getIdUzivatel() == null);
        
        try {
            // Processing starého souboru pokud chybí
            if (avatarFile != null && !avatarFile.isEmpty()) {
                uzivatel.setAvatar(avatarFile.getBytes());
            } else if (!novy) {
                Uzivatel stary = uzivatelService.getById(uzivatel.getIdUzivatel()).orElse(null);
                if (stary != null) {
                    uzivatel.setAvatar(stary.getAvatar());
                }
            }

            if (!novy && (uzivatel.getHesloHash() == null || uzivatel.getHesloHash().isBlank())) {
                uzivatelService.getById(uzivatel.getIdUzivatel())
                        .ifPresent(stary -> uzivatel.setHesloHash(stary.getHesloHash()));
                uzivatelService.save(uzivatel, false);
            } else {
                uzivatelService.save(uzivatel, true);
            }

            ra.addFlashAttribute("zprava", "Uživatel byl úspěšně uložen.");
        } catch (Exception e) {
            ra.addFlashAttribute("chyba", "Chyba při zpracování: " + e.getMessage());
        }
        return "redirect:/uzivatele";
    }

    @GetMapping("/uzivatele/{id}/avatar")
    @ResponseBody
    public org.springframework.http.ResponseEntity<byte[]> getAvatar(@PathVariable Integer id) {
        Uzivatel u = uzivatelService.getById(id).orElse(null);
        if (u != null && u.getAvatar() != null && u.getAvatar().length > 0) {
            return org.springframework.http.ResponseEntity.ok()
                    .header(org.springframework.http.HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .body(u.getAvatar());
        }
        return org.springframework.http.ResponseEntity.notFound().build();
    }

    // ===== SMAZÁNÍ UŽIVATELE =====
    @PostMapping("/uzivatele/{id}/smazat")
    public String uzivatelSmazat(@PathVariable Integer id, RedirectAttributes ra) {
        uzivatelService.delete(id);
        ra.addFlashAttribute("zprava", "Uživatel byl odstraněn.");
        return "redirect:/uzivatele";
    }
}
