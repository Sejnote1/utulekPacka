package dbspro2.utulek.controller;

import dbspro2.utulek.model.VeterinarniZaznam;
import dbspro2.utulek.service.VeterinarniZaznamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zaznamy")
public class VeterinarniZaznamController {

    @Autowired
    private VeterinarniZaznamService service;

    @PostMapping
    public VeterinarniZaznam create(@RequestBody VeterinarniZaznam zaznam) {
        return service.create(zaznam);
    }
}