package dbspro2.utulek.service;

import dbspro2.utulek.model.*;
import dbspro2.utulek.repository.*;
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
@DisplayName("AdopceService – unit testy")
class AdopceServiceTest {

    @Mock
    private AdopceRepository adopceRepository;

    @Mock
    private ZvireRepository zvireRepository;

    @Mock
    private StatusZvireteRepository statusRepository;

    @Mock
    private DuvodZamitnutiRepository duvodRepository;

    @Mock
    private AdopceZamitnutiRepository zamitnutiRepository;

    @InjectMocks
    private AdopceService adopceService;

    private Adopce adopce;
    private Zvire zvire;
    private StatusZvirete statusAdoptovano;

    @BeforeEach
    void setUp() {
        zvire = new Zvire();
        zvire.setIdZvire(10);
        zvire.setJmeno("Bafík");

        StatusZvirete pocatecniStatus = new StatusZvirete();
        pocatecniStatus.setIdStatus(1);
        pocatecniStatus.setStav("K adopci");
        zvire.setStatus(pocatecniStatus);

        adopce = new Adopce();
        adopce.setIdAdopce(1);
        adopce.setZvire(zvire);
        adopce.setStav("Probíhá");
        adopce.setDatumZadosti(LocalDate.now().minusDays(5));

        statusAdoptovano = new StatusZvirete();
        statusAdoptovano.setIdStatus(5);
        statusAdoptovano.setStav("Adoptováno");
    }

    // ===== vytvorAdopci =====

    @Test
    @DisplayName("vytvorAdopci – nastaví stav 'Probíhá' a dnešní datum")
    void vytvorAdopci_nastavi_stav_a_datum() {
        Adopce nova = new Adopce();
        nova.setZvire(zvire);
        when(adopceRepository.save(any(Adopce.class))).thenAnswer(inv -> inv.getArgument(0));

        Adopce result = adopceService.vytvorAdopci(nova);

        assertThat(result.getStav()).isEqualTo("Probíhá");
        assertThat(result.getDatumZadosti()).isEqualTo(LocalDate.now());
        verify(adopceRepository).save(nova);
    }

    // ===== schvalAdopci =====

    @Test
    @DisplayName("schvalAdopci – správně schválí adopci a změní status zvířete na 'Adoptováno'")
    void schvalAdopci_schvaliAZmeniStatusZvirete() {
        when(adopceRepository.findById(1)).thenReturn(Optional.of(adopce));
        when(adopceRepository.existsByZvire_IdZvireAndStav(10, "Schválena")).thenReturn(false);
        when(statusRepository.findByStav("Adoptováno")).thenReturn(Optional.of(statusAdoptovano));
        when(zvireRepository.save(any(Zvire.class))).thenReturn(zvire);
        when(adopceRepository.save(any(Adopce.class))).thenAnswer(inv -> inv.getArgument(0));

        Adopce result = adopceService.schvalAdopci(1);

        assertThat(result.getStav()).isEqualTo("Schválena");
        assertThat(result.getDatumSchvaleni()).isEqualTo(LocalDate.now());
        assertThat(zvire.getStatus().getStav()).isEqualTo("Adoptováno");
        verify(zvireRepository).save(zvire);
    }

    @Test
    @DisplayName("schvalAdopci – zvíře již má schválenou adopci → vyhodí výjimku")
    void schvalAdopci_zvireUzMaSchvalenou_vyhodiVyjimku() {
        when(adopceRepository.findById(1)).thenReturn(Optional.of(adopce));
        when(adopceRepository.existsByZvire_IdZvireAndStav(10, "Schválena")).thenReturn(true);

        assertThatThrownBy(() -> adopceService.schvalAdopci(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("již má schválenou adopci");
    }

    @Test
    @DisplayName("schvalAdopci – neexistující adopce → vyhodí výjimku")
    void schvalAdopci_neexistujiciAdopce_vyhodiVyjimku() {
        when(adopceRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adopceService.schvalAdopci(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("nenalezena");
    }

    // ===== zamitniAdopci =====

    @Test
    @DisplayName("zamitniAdopci – správně zamítne adopci se dvěma důvody")
    void zamitniAdopci_zamitneAUlozDuvody() {
        DuvodZamitnuti duvod1 = new DuvodZamitnuti();
        duvod1.setIdDuvod(1);
        duvod1.setPopis("Nevhodné podmínky");

        DuvodZamitnuti duvod2 = new DuvodZamitnuti();
        duvod2.setIdDuvod(2);
        duvod2.setPopis("Malé děti");

        when(adopceRepository.findById(1)).thenReturn(Optional.of(adopce));
        when(duvodRepository.findById(1)).thenReturn(Optional.of(duvod1));
        when(duvodRepository.findById(2)).thenReturn(Optional.of(duvod2));
        when(zamitnutiRepository.save(any(AdopceZamitnuti.class))).thenAnswer(inv -> inv.getArgument(0));
        when(adopceRepository.save(any(Adopce.class))).thenAnswer(inv -> inv.getArgument(0));

        Adopce result = adopceService.zamitniAdopci(1, List.of(1, 2));

        assertThat(result.getStav()).isEqualTo("Zamítnuta");
        verify(zamitnutiRepository, times(2)).save(any(AdopceZamitnuti.class));
    }

    @Test
    @DisplayName("zamitniAdopci – null seznam důvodů → zamítne bez ukládání důvodů")
    void zamitniAdopci_bezDuvodu_zamitneAdopci() {
        when(adopceRepository.findById(1)).thenReturn(Optional.of(adopce));
        when(adopceRepository.save(any(Adopce.class))).thenAnswer(inv -> inv.getArgument(0));

        Adopce result = adopceService.zamitniAdopci(1, null);

        assertThat(result.getStav()).isEqualTo("Zamítnuta");
        verify(zamitnutiRepository, never()).save(any());
    }

    @Test
    @DisplayName("zamitniAdopci – neexistující adopce → vyhodí výjimku")
    void zamitniAdopci_neexistujiciAdopce_vyhodiVyjimku() {
        when(adopceRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adopceService.zamitniAdopci(99, List.of()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("nenalezena");
    }

    // ===== getByStav =====

    @Test
    @DisplayName("getByStav – vrátí adopce filtrované dle stavu")
    void getByStav_vratiSpravneAdopce() {
        when(adopceRepository.findByStav("Probíhá")).thenReturn(List.of(adopce));

        List<Adopce> result = adopceService.getByStav("Probíhá");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStav()).isEqualTo("Probíhá");
    }
}
