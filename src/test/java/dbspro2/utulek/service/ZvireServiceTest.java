package dbspro2.utulek.service;

import dbspro2.utulek.model.StatusZvirete;
import dbspro2.utulek.model.Zvire;
import dbspro2.utulek.repository.StatusZvireteRepository;
import dbspro2.utulek.repository.ZvireRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ZvireService – unit testy")
class ZvireServiceTest {

    @Mock
    private ZvireRepository zvireRepository;

    @Mock
    private StatusZvireteRepository statusRepository;

    @InjectMocks
    private ZvireService zvireService;

    private Zvire zvire;
    private StatusZvirete status;

    @BeforeEach
    void setUp() {
        status = new StatusZvirete();
        status.setIdStatus(1);
        status.setStav("K adopci");

        zvire = new Zvire();
        zvire.setIdZvire(1);
        zvire.setJmeno("Rex");
        zvire.setPohlavi("M");
        zvire.setDatumPrijeti(LocalDate.now());
        zvire.setStatus(status);
    }

    // ===== getAll =====

    @Test
    @DisplayName("getAll – vrátí seznam všech zvířat")
    void getAll_vratiVsechnaZvirata() {
        when(zvireRepository.findAll()).thenReturn(List.of(zvire));

        List<Zvire> result = zvireService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getJmeno()).isEqualTo("Rex");
        verify(zvireRepository).findAll();
    }

    @Test
    @DisplayName("getAll – prázdná databáze vrátí prázdný seznam")
    void getAll_prazdnaDB_vratiPrazdnyList() {
        when(zvireRepository.findAll()).thenReturn(List.of());

        List<Zvire> result = zvireService.getAll();

        assertThat(result).isEmpty();
    }

    // ===== getById =====

    @Test
    @DisplayName("getById – nalezené zvíře vrátí Optional s hodnotou")
    void getById_existujiciId_vratiZvire() {
        when(zvireRepository.findById(1)).thenReturn(Optional.of(zvire));

        Optional<Zvire> result = zvireService.getById(1);

        assertThat(result).isPresent();
        assertThat(result.get().getJmeno()).isEqualTo("Rex");
    }

    @Test
    @DisplayName("getById – neexistující ID vrátí prázdný Optional")
    void getById_neexistujiciId_vratiEmpty() {
        when(zvireRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Zvire> result = zvireService.getById(99);

        assertThat(result).isEmpty();
    }

    // ===== searchByName =====

    @Test
    @DisplayName("searchByName – hledání dle jména (case-insensitive)")
    void searchByName_najdeZvirePoJmenu() {
        when(zvireRepository.findByJmenoContainingIgnoreCase("rex")).thenReturn(List.of(zvire));

        List<Zvire> result = zvireService.searchByName("rex");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getJmeno()).isEqualTo("Rex");
    }

    @Test
    @DisplayName("searchByName – hledání bez výsledku vrátí prázdný seznam")
    void searchByName_nenajdeZvire_vratiPrazdnyList() {
        when(zvireRepository.findByJmenoContainingIgnoreCase("Maxik")).thenReturn(List.of());

        List<Zvire> result = zvireService.searchByName("Maxik");

        assertThat(result).isEmpty();
    }

    // ===== updateStatus =====

    @Test
    @DisplayName("updateStatus – správně změní status zvířete")
    void updateStatus_zmeneniStatusu() {
        StatusZvirete novyStatus = new StatusZvirete();
        novyStatus.setIdStatus(2);
        novyStatus.setStav("V léčení");

        when(zvireRepository.findById(1)).thenReturn(Optional.of(zvire));
        when(statusRepository.findById(2)).thenReturn(Optional.of(novyStatus));
        when(zvireRepository.save(any(Zvire.class))).thenAnswer(inv -> inv.getArgument(0));

        Zvire result = zvireService.updateStatus(1, 2);

        assertThat(result.getStatus().getStav()).isEqualTo("V léčení");
        verify(zvireRepository).save(zvire);
    }

    @Test
    @DisplayName("updateStatus – neexistující zvíře vyhodí RuntimeException")
    void updateStatus_neexistujiciZvire_vyhodiVyjimku() {
        when(zvireRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> zvireService.updateStatus(99, 1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("nenalezeno");
    }

    @Test
    @DisplayName("updateStatus – neexistující status vyhodí RuntimeException")
    void updateStatus_neexistujiciStatus_vyhodiVyjimku() {
        when(zvireRepository.findById(1)).thenReturn(Optional.of(zvire));
        when(statusRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> zvireService.updateStatus(1, 99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("nenalezen");
    }

    // ===== save =====

    @Test
    @DisplayName("save – uloží zvíře a vrátí ho")
    void save_ulozi_a_vrati_zvire() {
        when(zvireRepository.save(zvire)).thenReturn(zvire);

        Zvire result = zvireService.save(zvire);

        assertThat(result).isEqualTo(zvire);
        verify(zvireRepository).save(zvire);
    }

    // ===== delete =====

    @Test
    @DisplayName("delete – zavolá deleteById na repository")
    void delete_zavolaDeleteById() {
        zvireService.delete(1);

        verify(zvireRepository).deleteById(1);
    }
}
