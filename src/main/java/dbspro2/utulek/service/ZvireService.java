package dbspro2.utulek.service;

import dbspro2.utulek.model.Zvire;
import dbspro2.utulek.model.StatusZvirete;
import dbspro2.utulek.repository.ZvireRepository;
import dbspro2.utulek.repository.StatusZvireteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZvireService {

    private final ZvireRepository zvireRepository;
    private final StatusZvireteRepository statusRepository;

    public ZvireService(ZvireRepository zvireRepository, StatusZvireteRepository statusRepository) {
        this.zvireRepository = zvireRepository;
        this.statusRepository = statusRepository;
    }

    public List<Zvire> getAll() {
        return zvireRepository.findAll();
    }

    public Optional<Zvire> getById(Integer id) {
        return zvireRepository.findById(id);
    }

    public List<Zvire> searchByName(String jmeno) {
        return zvireRepository.findByJmenoContainingIgnoreCase(jmeno);
    }

    public List<Zvire> filterByStatus(Integer idStatus) {
        return zvireRepository.findByStatus_IdStatus(idStatus);
    }

    public List<Zvire> filterByPlemeno(Integer idPlemeno) {
        return zvireRepository.findByPlemeno_IdPlemeno(idPlemeno);
    }

    public Zvire save(Zvire zvire) {
        return zvireRepository.save(zvire);
    }

    public void delete(Integer id) {
        zvireRepository.deleteById(id);
    }

    public Zvire updateStatus(Integer idZvire, Integer idStatus) {
        Zvire zvire = zvireRepository.findById(idZvire)
                .orElseThrow(() -> new RuntimeException("Zvíře nenalezeno: " + idZvire));
        StatusZvirete status = statusRepository.findById(idStatus)
                .orElseThrow(() -> new RuntimeException("Status nenalezen: " + idStatus));
        zvire.setStatus(status);
        return zvireRepository.save(zvire);
    }

    public Integer getVekMesice(Integer idZvire) {
        return zvireRepository.getVekMesiceNativne(idZvire);
    }

    public Integer getPocetZviratDruhu(String druh) {
        return zvireRepository.getPocetZviratDruhuNativne(druh);
    }

    @org.springframework.transaction.annotation.Transactional
    public void zmenStatusHromadne(Integer idStare, Integer idNove) {
        zvireRepository.zmenStatusHromadneNativne(idStare, idNove);
    }
}