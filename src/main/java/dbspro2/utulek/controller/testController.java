package dbspro2.utulek.controller;

import dbspro2.utulek.model.testTable;
import dbspro2.utulek.repository.testRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class testController {

    private final testRepo repository;

    public testController(testRepo repository) {
        this.repository = repository;
    }

    @GetMapping("/testdb")
    public List<testTable> testDb() {
        return repository.findAll();
    }
}