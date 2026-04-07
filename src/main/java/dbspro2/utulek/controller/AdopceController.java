package dbspro2.utulek.controller;

import dbspro2.utulek.model.Adopce;
import dbspro2.utulek.service.AdopceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adopce")
public class AdopceController {

    @Autowired
    private AdopceService service;

    @PostMapping
    public Adopce create(@RequestBody Adopce adopce) {
        return service.vytvorAdopci(adopce);
    }

    @PutMapping("/{id}/schvalit")
    public Adopce schval(@PathVariable Integer id) {
        return service.schvalAdopci(id);
    }

    @PutMapping("/{id}/zamitnout")
    public Adopce zamitni(@PathVariable Integer id) {
        return service.zamitniAdopci(id);
    }
}