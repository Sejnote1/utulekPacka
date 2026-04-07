package dbspro2.utulek.service;

import dbspro2.utulek.model.VeterinarniZaznam;
import dbspro2.utulek.repository.VeterinarniZaznamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VeterinarniZaznamService {

    private final VeterinarniZaznamRepository zaznamRepository;

    public VeterinarniZaznamService(VeterinarniZaznamRepository zaznamRepository) {
        this.zaznamRepository = zaznamRepository;
    }

    public List<VeterinarniZaznam> getByZvire(Integer idZvire) {
        return zaznamRepository.findByZvire_IdZvire(idZvire);
    }

    public VeterinarniZaznam create(VeterinarniZaznam zaznam) {
        if (zaznam.getDatum() == null) {
            zaznam.setDatum(LocalDate.now());
        }
        return zaznamRepository.save(zaznam);
    }

    public void delete(Integer id) {
        zaznamRepository.deleteById(id);
    }
}
