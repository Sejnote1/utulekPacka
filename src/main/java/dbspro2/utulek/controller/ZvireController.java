package dbspro2.utulek.controller;

import dbspro2.utulek.model.Zvire;
import dbspro2.utulek.service.ZvireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zvirata")
public class ZvireController {

    @Autowired
    private ZvireService service;

    @GetMapping
    public List<Zvire> getAll() {
        return service.getAll();
    }

    @GetMapping("/search")
    public List<Zvire> search(@RequestParam String jmeno) {
        return service.searchByName(jmeno);
    }

    @PostMapping
    public Zvire create(@RequestBody Zvire zvire) {
        return service.save(zvire);
    }
}
