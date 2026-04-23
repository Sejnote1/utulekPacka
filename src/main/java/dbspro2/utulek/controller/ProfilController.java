package dbspro2.utulek.controller;

import dbspro2.utulek.model.Uzivatel;
import dbspro2.utulek.security.UzivatelDetails;
import dbspro2.utulek.service.UzivatelService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profil")
@org.springframework.transaction.annotation.Transactional
public class ProfilController {

    private final UzivatelService uzivatelService;

    public ProfilController(UzivatelService uzivatelService) {
        this.uzivatelService = uzivatelService;
    }

    // ===== ZOBRAZENÍ PROFILU =====
    @GetMapping
    public String profil(@AuthenticationPrincipal UzivatelDetails principal, Model model) {
        Uzivatel uzivatel = uzivatelService.getById(principal.getUzivatel().getIdUzivatel())
                .orElseThrow(() -> new RuntimeException("Uživatel nenalezen"));
        model.addAttribute("uzivatel", uzivatel);
        return "profil";
    }

    // ===== ULOŽENÍ PROFILU =====
    @PostMapping("/ulozit")
    public String ulozit(@AuthenticationPrincipal UzivatelDetails principal,
                         @RequestParam(value = "noveHeslo",   required = false) String noveHeslo,
                         @RequestParam(value = "existingHash", required = false) String existingHash,
                         @RequestParam(value = "avatar_file", required = false) MultipartFile avatarFile,
                         RedirectAttributes ra) {
        try {
            Uzivatel uzivatel = uzivatelService.getById(principal.getUzivatel().getIdUzivatel())
                    .orElseThrow(() -> new RuntimeException("Uživatel nenalezen"));

            // Avatar: nahrát nový nebo zachovat stávající
            if (avatarFile != null && !avatarFile.isEmpty()) {
                uzivatel.setAvatar(avatarFile.getBytes());
            }

            // Heslo: nové → zahashovat; prázdné → zachovat
            if (noveHeslo != null && !noveHeslo.isBlank()) {
                uzivatel.setHesloHash(noveHeslo);
                uzivatelService.save(uzivatel, true);
            } else {
                uzivatel.setHesloHash(existingHash);
                uzivatelService.save(uzivatel, false);
            }

            ra.addFlashAttribute("zprava", "Profil byl úspěšně uložen.");
        } catch (Exception e) {
            ra.addFlashAttribute("chyba", "Chyba: " + e.getMessage());
        }
        return "redirect:/profil";
    }
}
