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
@org.springframework.transaction.annotation.Transactional
public class HomeController {

    private final ZvireService zvireService;
    private final AdopceService adopceService;
    private final UzivatelService uzivatelService;
    private final RoleRepository roleRepository;
    private final dbspro2.utulek.repository.ZvireKAdopciRepository zvireKAdopciRepository;
    private final dbspro2.utulek.repository.ZvireDetailHistorieRepository historieRepository;
    private final dbspro2.utulek.repository.StatusZvireteRepository statusRepository;

    public HomeController(ZvireService zvireService,
                          AdopceService adopceService,
                          UzivatelService uzivatelService,
                          RoleRepository roleRepository,
                          dbspro2.utulek.repository.ZvireKAdopciRepository zvireKAdopciRepository,
                          dbspro2.utulek.repository.ZvireDetailHistorieRepository historieRepository,
                          dbspro2.utulek.repository.StatusZvireteRepository statusRepository) {
        this.zvireService    = zvireService;
        this.adopceService   = adopceService;
        this.uzivatelService = uzivatelService;
        this.roleRepository  = roleRepository;
        this.zvireKAdopciRepository = zvireKAdopciRepository;
        this.historieRepository = historieRepository;
        this.statusRepository = statusRepository;
    }

    // ===== VEŘEJNÝ SEZNAM K ADOPCI (z Nativního View) =====
    @GetMapping("/verejne-adopce")
    public String verejneAdopce(Model model) {
        model.addAttribute("zvirata", zvireKAdopciRepository.findAll());
        return "verejne-adopce";
    }

    // ===== ADMIN DASHBOARD =====
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("pocetZvirat",    zvireService.getAll().size());
        model.addAttribute("pocetAdopci",    adopceService.getAll().size());
        model.addAttribute("pocetUzivatelu", uzivatelService.getAll().size());
        model.addAttribute("adopceProbiha",  adopceService.getByStav("Probíhá").size());
        
        // Z SQL funkcí (fn_pocet_zvirat_druhu)
        model.addAttribute("pocetPsu", zvireService.getPocetZviratDruhu("Pes"));
        model.addAttribute("pocetKocek", zvireService.getPocetZviratDruhu("Kočka"));
        
        // Z View (v_detail_zvirat_historie)
        model.addAttribute("zvirataHistorie", historieRepository.findAll());
        
        return "admin";
    }

    // ===== ADMIN MACRA (Procedury) =====
    @PostMapping("/admin/hromadne-k-adopci")
    public String hromadneKAdopci(RedirectAttributes ra) {
        // Získáme dynamicky správná ID pro tyto stavy z DB (aby nedošlo k chybě pokud 1 a 2 znamenají něco jiného)
        dbspro2.utulek.model.StatusZvirete staryStatus = statusRepository.findByStav("Přijato").orElse(null);
        dbspro2.utulek.model.StatusZvirete novyStatus = statusRepository.findByStav("K adopci").orElse(null);
        
        if (staryStatus != null && novyStatus != null) {
            zvireService.zmenStatusHromadne(staryStatus.getIdStatus(), novyStatus.getIdStatus());
            ra.addFlashAttribute("zprava", "Procedura sp_zmen_status_hromadne proběhla: Všechna zvířata ve stavu 'Přijato' (" + staryStatus.getIdStatus() + ") byla převedena na 'K adopci' (" + novyStatus.getIdStatus() + ").");
        } else {
            ra.addFlashAttribute("chyba", "Selhání procedury: Stavy 'Přijato' a 'K adopci' nebyly v DB nalezeny.");
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/expiruj-adopce")
    public String expirujAdopce(RedirectAttributes ra) {
        adopceService.expirujStareAdopce();
        ra.addFlashAttribute("zprava", "Procedura sp_expiruj_stare_adopce proběhla: Adopce probíhající déle než 30 dní byly zamítnuty.");
        return "redirect:/admin";
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

    @GetMapping("/uzivatele/{id}/upravit")
    public String uzivatelUprava(@PathVariable Integer id, Model model) {
        Uzivatel u = uzivatelService.getById(id)
                .orElseThrow(() -> new RuntimeException("Uživatel nenalezen"));
        // NEMAZAT heslo_hash – uložíme ho jako hidden field, aby se zachovalo při prázdném políčku hesla
        model.addAttribute("uzivatel", u);
        model.addAttribute("role", roleRepository.findAll());
        return "uzivatele/formular";
    }

    // ===== ULOŽENÍ UŽIVATELE =====
    @PostMapping("/uzivatele/ulozit")
    public String uzivatelUlozit(@ModelAttribute Uzivatel uzivatel,
                                 @RequestParam(value = "noveHeslo",   required = false) String noveHeslo,
                                 @RequestParam(value = "existingHash", required = false) String existingHash,
                                 @RequestParam(value = "avatar_file", required = false) org.springframework.web.multipart.MultipartFile avatarFile,
                                 RedirectAttributes ra) {
        boolean novy = (uzivatel.getIdUzivatel() == null);

        try {
            // Avatar: nový soubor nebo zachovat starý
            if (avatarFile != null && !avatarFile.isEmpty()) {
                uzivatel.setAvatar(avatarFile.getBytes());
            } else if (!novy) {
                Uzivatel stary = uzivatelService.getById(uzivatel.getIdUzivatel()).orElse(null);
                if (stary != null) {
                    uzivatel.setAvatar(stary.getAvatar());
                }
            }

            // Heslo: nové vyplněné → zahashovat; prázdné → zachovat existující hash
            if (noveHeslo != null && !noveHeslo.isBlank()) {
                uzivatel.setHesloHash(noveHeslo);
                uzivatelService.save(uzivatel, true);   // hashuje
            } else {
                uzivatel.setHesloHash(existingHash);
                uzivatelService.save(uzivatel, false);  // nehashuje
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
