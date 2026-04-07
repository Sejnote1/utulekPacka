package dbspro2.utulek.service;

import dbspro2.utulek.model.Adopce;
import dbspro2.utulek.model.AdopceZamitnuti;
import dbspro2.utulek.model.DuvodZamitnuti;
import dbspro2.utulek.model.StatusZvirete;
import dbspro2.utulek.model.Zvire;
import dbspro2.utulek.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AdopceService {

    private final AdopceRepository adopceRepository;
    private final ZvireRepository zvireRepository;
    private final StatusZvireteRepository statusRepository;
    private final DuvodZamitnutiRepository duvodRepository;
    private final AdopceZamitnutiRepository zamitnutiRepository;

    public AdopceService(AdopceRepository adopceRepository,
                         ZvireRepository zvireRepository,
                         StatusZvireteRepository statusRepository,
                         DuvodZamitnutiRepository duvodRepository,
                         AdopceZamitnutiRepository zamitnutiRepository) {
        this.adopceRepository   = adopceRepository;
        this.zvireRepository    = zvireRepository;
        this.statusRepository   = statusRepository;
        this.duvodRepository    = duvodRepository;
        this.zamitnutiRepository = zamitnutiRepository;
    }

    public List<Adopce> getAll() {
        return adopceRepository.findAll();
    }

    public List<Adopce> getByStav(String stav) {
        return adopceRepository.findByStav(stav);
    }

    public Optional<Adopce> getById(Integer id) {
        return adopceRepository.findById(id);
    }

    public List<Adopce> getByZvire(Integer idZvire) {
        return adopceRepository.findByZvire_IdZvire(idZvire);
    }

    @Transactional
    public Adopce vytvorAdopci(Adopce adopce) {
        adopce.setStav("Probíhá");
        adopce.setDatumZadosti(LocalDate.now());
        return adopceRepository.save(adopce);
    }

    @Transactional
    public Adopce schvalAdopci(Integer id) {
        Adopce adopce = adopceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adopce nenalezena: " + id));

        if (adopceRepository.existsByZvire_IdZvireAndStav(adopce.getZvire().getIdZvire(), "Schválena")) {
            throw new RuntimeException("Zvíře již má schválenou adopci.");
        }

        adopce.setStav("Schválena");
        adopce.setDatumSchvaleni(LocalDate.now());

        // Automatická změna statusu zvířete na "Adoptováno"
        Zvire zvire = adopce.getZvire();
        StatusZvirete adoptovano = statusRepository.findByStav("Adoptováno")
                .orElseThrow(() -> new RuntimeException("Status 'Adoptováno' nenalezen"));
        zvire.setStatus(adoptovano);
        zvireRepository.save(zvire);

        return adopceRepository.save(adopce);
    }

    @Transactional
    public Adopce zamitniAdopci(Integer id, List<Integer> duvodIds) {
        Adopce adopce = adopceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adopce nenalezena: " + id));

        adopce.setStav("Zamítnuta");

        // Uložení důvodů zamítnutí
        if (duvodIds != null) {
            for (Integer idDuvod : duvodIds) {
                DuvodZamitnuti duvod = duvodRepository.findById(idDuvod).orElse(null);
                if (duvod != null) {
                    AdopceZamitnuti az = new AdopceZamitnuti();
                    az.setAdopce(adopce);
                    az.setDuvod(duvod);
                    zamitnutiRepository.save(az);
                }
            }
        }

        return adopceRepository.save(adopce);
    }

    public void delete(Integer id) {
        adopceRepository.deleteById(id);
    }
}