package dbspro2.utulek.repository;

import dbspro2.utulek.model.testTable;
import java.util.List;
import java.util.ArrayList;

/**
 * Testovací repozitář – nahrazen stub implementací
 * kvůli odstranění @Entity z testTable.
 */
public class testRepo {
    public List<testTable> findAll() {
        return new ArrayList<>();
    }
}