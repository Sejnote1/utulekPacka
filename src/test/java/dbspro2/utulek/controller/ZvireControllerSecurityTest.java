package dbspro2.utulek.controller;

import dbspro2.utulek.model.*;
import dbspro2.utulek.repository.*;
import dbspro2.utulek.security.UzivatelDetails;
import dbspro2.utulek.security.UzivatelDetailsService;
import dbspro2.utulek.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integrační testy bezpečnostních pravidel pro ZvireController.
 * Testujeme: kdo má přístup kam a kdo je přesměrován na login.
 */
@WebMvcTest(ZvireController.class)
@DisplayName("ZvireController – bezpečnostní testy")
class ZvireControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    // ── Mocky pro service vrstvu ──────────────────────────────────────────────
    @MockBean private ZvireService zvireService;
    @MockBean private PlemenoRepository plemenoRepository;
    @MockBean private DruhRepository druhRepository;
    @MockBean private StatusZvireteRepository statusRepository;

    // ── Mocky vyžadované Spring Security konfigurací ──────────────────────────
    @MockBean private UzivatelDetailsService uzivatelDetailsService;

    // ── Pomocné mock data ─────────────────────────────────────────────────────

    private Zvire mockZvire() {
        StatusZvirete status = new StatusZvirete("K adopci");
        status.setIdStatus(1);

        Zvire z = new Zvire();
        z.setIdZvire(1);
        z.setJmeno("Rex");
        z.setStatus(status);
        return z;
    }

    // =========================================================================
    // 1. Nepřihlášený uživatel → přesměrování na /login
    // =========================================================================

    @Test
    @DisplayName("GET /zvirata – nepřihlášený → přesměrování na /login")
    void seznam_neprihlaseny_presmerovaniNaLogin() throws Exception {
        mockMvc.perform(get("/zvirata"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @DisplayName("GET /zvirata/1 – nepřihlášený → přesměrování na /login")
    void detail_neprihlaseny_presmerovaniNaLogin() throws Exception {
        mockMvc.perform(get("/zvirata/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @DisplayName("GET /zvirata/novy – nepřihlášený → přesměrování na /login")
    void formular_neprihlaseny_presmerovaniNaLogin() throws Exception {
        mockMvc.perform(get("/zvirata/novy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    // =========================================================================
    // 2. Přihlášený Veterinář – může vidět seznam a detail, ale NE formulář
    // =========================================================================

    @Test
    @WithMockUser(username = "vet@example.com", authorities = "Veterinář")
    @DisplayName("GET /zvirata – Veterinář → 200 OK (seznam zvířat)")
    void seznam_veterinar_maApristup() throws Exception {
        when(zvireService.getAll()).thenReturn(List.of(mockZvire()));
        when(statusRepository.findAll()).thenReturn(List.of());
        when(plemenoRepository.findAll()).thenReturn(List.of());
        when(druhRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/zvirata"))
                .andExpect(status().isOk())
                .andExpect(view().name("zvirata/seznam"));
    }

    @Test
    @WithMockUser(username = "vet@example.com", authorities = "Veterinář")
    @DisplayName("GET /zvirata/1 – Veterinář → 200 OK (detail zvířete)")
    void detail_veterinar_maApristup() throws Exception {
        when(zvireService.getById(1)).thenReturn(Optional.of(mockZvire()));
        when(statusRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/zvirata/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("zvirata/detail"));
    }

    @Test
    @WithMockUser(username = "vet@example.com", authorities = "Veterinář")
    @DisplayName("GET /zvirata/novy – Veterinář → 403 Forbidden (nesmí přidat zvíře)")
    void formular_veterinar_zakazan() throws Exception {
        mockMvc.perform(get("/zvirata/novy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/chyba/pristup"));
    }

    // =========================================================================
    // 3. Přihlášená Recepční – má přístup na seznam i formulář
    // =========================================================================

    @Test
    @WithMockUser(username = "recepce@example.com", authorities = "Recepční")
    @DisplayName("GET /zvirata – Recepční → 200 OK")
    void seznam_recepce_maApristup() throws Exception {
        when(zvireService.getAll()).thenReturn(List.of());
        when(statusRepository.findAll()).thenReturn(List.of());
        when(plemenoRepository.findAll()).thenReturn(List.of());
        when(druhRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/zvirata"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "recepce@example.com", authorities = "Recepční")
    @DisplayName("GET /zvirata/novy – Recepční → 200 OK (smí přidat zvíře)")
    void formular_recepce_maApristup() throws Exception {
        when(plemenoRepository.findAll()).thenReturn(List.of());
        when(statusRepository.findAll()).thenReturn(List.of());
        when(druhRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/zvirata/novy"))
                .andExpect(status().isOk())
                .andExpect(view().name("zvirata/formular"));
    }

    // =========================================================================
    // 4. Přihlášený Administrátor – má přístup všude
    // =========================================================================

    @Test
    @WithMockUser(username = "admin@example.com", authorities = "Administrátor")
    @DisplayName("GET /zvirata/novy – Administrátor → 200 OK")
    void formular_admin_maApristup() throws Exception {
        when(plemenoRepository.findAll()).thenReturn(List.of());
        when(statusRepository.findAll()).thenReturn(List.of());
        when(druhRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/zvirata/novy"))
                .andExpect(status().isOk());
    }
}
