package dbspro2.utulek.service;

import dbspro2.utulek.model.Zvire;
import dbspro2.utulek.repository.ZvireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZvireService {

    @Autowired
    private ZvireRepository zvireRepository;

    public List<Zvire> getAll() {
        return zvireRepository.findAll();
    }

    public List<Zvire> searchByName(String jmeno) {
        return zvireRepository.findByJmenoContainingIgnoreCase(jmeno);
    }

    public Zvire save(Zvire zvire) {
        return zvireRepository.save(zvire);
    }
}