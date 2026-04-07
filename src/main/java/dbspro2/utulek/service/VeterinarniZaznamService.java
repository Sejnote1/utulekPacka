package dbspro2.utulek.service;

import dbspro2.utulek.model.VeterinarniZaznam;
import dbspro2.utulek.repository.VeterinarniZaznamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeterinarniZaznamService {

    @Autowired
    private VeterinarniZaznamRepository repo;

    public VeterinarniZaznam create(VeterinarniZaznam zaznam) {

        // ❗ kontrola role
        if (!zaznam.getUzivatel().getRole().getNazev().equals("Veterinář")) {
            throw new RuntimeException("Záznam může vytvořit jen veterinář");
        }

        return repo.save(zaznam);
    }
}
