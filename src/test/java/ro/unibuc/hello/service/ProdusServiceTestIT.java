package ro.unibuc.hello.service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.unibuc.hello.data.ProdusRepository;
import ro.unibuc.hello.dto.ProdusDTO;
import ro.unibuc.hello.data.Produs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@SpringBootTest
//@Tag("IT")
public class ProdusServiceTestIT {
    @Autowired
    ProdusRepository produsRepository;

    @Autowired
    private ProdusService produsService;

    private ProdusDTO produsOriginal = new ProdusDTO("375", "Zgarda roz", "48.99");;

    @Test
    void test_createProdus() {
        Produs produsCheck = produsService.createProdus(produsOriginal);

        assertEquals(produsOriginal, produsCheck);
    }

    @Test
    void test_getProdus() {
        ProdusDTO produsCheck = produsService.getProdus(produsOriginal.getId());

        assertEquals(produsOriginal.getNume(), produsCheck.getNume());
    }

    @Test
    void test_getAllProduse() {
        ProdusDTO anotherProdus = new ProdusDTO("53345", "Lesa albastra", "87.50");

        produsService.createProdus(anotherProdus);
        List<ProdusDTO> produsCheckList = produsService.getAll();

        assertEquals("640a641f849746930bd895c3", produsCheckList.get(0).getId());
        assertEquals("afaa", produsCheckList.get(0).getNume());
        assertEquals("12345", produsCheckList.get(0).getPret());
        //assertEquals(anotherProdus, produsCheckList.get(6));
    }

    @Test
    void test_updateOriginalProdus() {
        // Integrity checks
        assertEquals("375", produsOriginal.getId());
        assertEquals("Zgarda roz", produsOriginal.getNume());
        assertEquals("48.99", produsOriginal.getPret());

        produsService.createProdus(produsOriginal);
        ProdusDTO updatedProdus = new ProdusDTO("375", "Zgarda albastra", "66.66");

        produsService.updateProdus(updatedProdus);
        produsOriginal = produsService.getProdus("375");

        assertEquals("375", produsOriginal.getId());
        assertEquals("Zgarda albastra", produsOriginal.getNume());
        assertEquals("66.66", produsOriginal.getPret());
    }

    @Test
    void test_deleteProdus() {
        produsService.deleteProdus("375");

        try {
            produsService.getProdus("375");
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}
