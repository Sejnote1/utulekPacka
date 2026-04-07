package dbspro2.utulek.service;

import dbspro2.utulek.model.Zajemce;
import dbspro2.utulek.repository.ZajemceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZajemceService {

    private final ZajemceRepository zajemceRepository;

    public ZajemceService(ZajemceRepository zajemceRepository) {
        this.zajemceRepository = zajemceRepository;
    }

    public List<Zajemce> getAll() {
        return zajemceRepository.findAll();
    }

    public Optional<Zajemce> getById(Integer id) {
        return zajemceRepository.findById(id);
    }

    public List<Zajemce> search(String query) {
        return zajemceRepository.findByPrijmeniContainingIgnoreCaseOrJmenoContainingIgnoreCase(query, query);
    }

    public Zajemce save(Zajemce zajemce) {
        return zajemceRepository.save(zajemce);
    }

    public void delete(Integer id) {
        zajemceRepository.deleteById(id);
    }
}
