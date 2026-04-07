package dbspro2.utulek.service;

import dbspro2.utulek.model.Adopce;
import dbspro2.utulek.model.Zvire;
import dbspro2.utulek.repository.ZvireRepository;
import dbspro2.utulek.repository.AdopceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class AdopceService {

    @Autowired
    private AdopceRepository adopceRepository;

    @Autowired
    private ZvireRepository zvireRepository;

    public Adopce vytvorAdopci(Adopce adopce) {
        adopce.setStav("PROBIHA");
        return adopceRepository.save(adopce);
    }

    public Adopce schvalAdopci(Integer id) {
        Adopce adopce = adopceRepository.findById(id)
                .orElseThrow();

        Integer idZvire = adopce.getZvire().getIdZvire();

        // ❗ pravidlo: jen jedna schválená adopce
        if (adopceRepository.existsByZvire_IdZvireAndStav(idZvire, "SCHVALENA")) {
            throw new RuntimeException("Zvíře už má schválenou adopci");
        }

        adopce.setStav("SCHVALENA");
        adopce.setDatumSchvaleni(LocalDate.now());

        // ❗ změna statusu zvířete
        Zvire zvire = adopce.getZvire();
        zvire.getStatus().setStav("Adoptováno");

        zvireRepository.save(zvire);

        return adopceRepository.save(adopce);
    }

    public Adopce zamitniAdopci(Integer id) {
        Adopce adopce = adopceRepository.findById(id)
                .orElseThrow();

        adopce.setStav("ZAMITNUTA");

        return adopceRepository.save(adopce);
    }
}