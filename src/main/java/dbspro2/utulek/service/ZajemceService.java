package dbspro2.utulek.service;

import dbspro2.utulek.model.Zajemce;
import dbspro2.utulek.repository.ZajemceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZajemceService {

    private final ZajemceRepository zajemceRepository;
    private final dbspro2.utulek.repository.ZajemceSkoreRepository skoreRepository;

    public ZajemceService(ZajemceRepository zajemceRepository, dbspro2.utulek.repository.ZajemceSkoreRepository skoreRepository) {
        this.zajemceRepository = zajemceRepository;
        this.skoreRepository = skoreRepository;
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

    @org.springframework.transaction.annotation.Transactional
    public void anonymizuj(Integer id) {
        zajemceRepository.anonymizujZajemceNativne(id);
    }

    public Optional<dbspro2.utulek.model.ZajemceSkore> getSkore(Integer id) {
        return skoreRepository.findById(id);
    }
}
