package dbspro2.utulek.service;

import dbspro2.utulek.model.Role;
import dbspro2.utulek.model.Uzivatel;
import dbspro2.utulek.repository.UzivatelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UzivatelService – unit testy")
class UzivatelServiceTest {

    @Mock
    private UzivatelRepository uzivatelRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UzivatelService uzivatelService;

    private Uzivatel uzivatel;
    private Role recepce;

    @BeforeEach
    void setUp() {
        recepce = new Role("Recepční");

        uzivatel = new Uzivatel();
        uzivatel.setIdUzivatel(1);
        uzivatel.setJmeno("Jana");
        uzivatel.setPrijmeni("Nováková");
        uzivatel.setEmail("jana@example.com");
        uzivatel.setHesloHash("plainTextHeslo");
        uzivatel.setRole(recepce);
    }

    // ===== getAll =====

    @Test
    @DisplayName("getAll – vrátí seznam uživatelů")
    void getAll_vratiSeznamUzivatelu() {
        when(uzivatelRepository.findAll()).thenReturn(List.of(uzivatel));

        List<Uzivatel> result = uzivatelService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("jana@example.com");
    }

    // ===== getById =====

    @Test
    @DisplayName("getById – nalezený uživatel")
    void getById_nalezenUzivatel() {
        when(uzivatelRepository.findById(1)).thenReturn(Optional.of(uzivatel));

        Optional<Uzivatel> result = uzivatelService.getById(1);

        assertThat(result).isPresent();
        assertThat(result.get().getJmeno()).isEqualTo("Jana");
    }

    @Test
    @DisplayName("getById – neexistující ID vrátí prázdný Optional")
    void getById_neexistujiciId_vratiEmpty() {
        when(uzivatelRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Uzivatel> result = uzivatelService.getById(99);

        assertThat(result).isEmpty();
    }

    // ===== save s hashováním =====

    @Test
    @DisplayName("save(hashPassword=true) – zahashuje heslo před uložením")
    void save_hashPassword_true_zahashuje() {
        when(passwordEncoder.encode("plainTextHeslo")).thenReturn("$2a$10$hashedHeslo");
        when(uzivatelRepository.save(any(Uzivatel.class))).thenAnswer(inv -> inv.getArgument(0));

        Uzivatel result = uzivatelService.save(uzivatel, true);

        assertThat(result.getHesloHash()).isEqualTo("$2a$10$hashedHeslo");
        verify(passwordEncoder).encode("plainTextHeslo");
    }

    @Test
    @DisplayName("save(hashPassword=false) – heslo zůstane beze změny")
    void save_hashPassword_false_nezahashuje() {
        when(uzivatelRepository.save(any(Uzivatel.class))).thenAnswer(inv -> inv.getArgument(0));

        Uzivatel result = uzivatelService.save(uzivatel, false);

        assertThat(result.getHesloHash()).isEqualTo("plainTextHeslo");
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    @DisplayName("save(hashPassword=true) – prázdné heslo se nezahashuje")
    void save_hashPassword_true_prazdneHeslo_nezahashuje() {
        uzivatel.setHesloHash("");
        when(uzivatelRepository.save(any(Uzivatel.class))).thenAnswer(inv -> inv.getArgument(0));

        Uzivatel result = uzivatelService.save(uzivatel, true);

        assertThat(result.getHesloHash()).isEmpty();
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    @DisplayName("save(hashPassword=true) – null heslo se nezahashuje")
    void save_hashPassword_true_nullHeslo_nezahashuje() {
        uzivatel.setHesloHash(null);
        when(uzivatelRepository.save(any(Uzivatel.class))).thenAnswer(inv -> inv.getArgument(0));

        Uzivatel result = uzivatelService.save(uzivatel, true);

        assertThat(result.getHesloHash()).isNull();
        verify(passwordEncoder, never()).encode(anyString());
    }

    // ===== delete =====

    @Test
    @DisplayName("delete – zavolá deleteById na repository")
    void delete_zavolaDeleteById() {
        uzivatelService.delete(1);

        verify(uzivatelRepository).deleteById(1);
    }
}
