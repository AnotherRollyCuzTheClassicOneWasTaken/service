package ro.unibuc.hello.data;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class OrderEntityTest {
    UserEntity user = new UserEntity("1234","a@a.com", "parola");
    Produs produs1 = new Produs("shrbf", "minge", "9.99");
    Produs produs2 = new Produs("sfvd", "os", "9.99");
    List <Produs> listaProduse = new ArrayList<Produs>() {
        {
            add(produs1);
            add(produs2);
        }
    };

    @Test
    void testConstructor() {
        OrderEntity OrderEntity = new OrderEntity("1234", user, listaProduse);

        assertEquals("1234", OrderEntity.getId());
        assertEquals(user, OrderEntity.getUser());
        assertEquals(listaProduse, OrderEntity.getListaProduse());
    }

    @Test
    void testSetters() {
        OrderEntity OrderEntity = new OrderEntity();

        assertEquals(null, OrderEntity.getId());
        assertEquals(null, OrderEntity.getUser());
        assertEquals(null, OrderEntity.getListaProduse());

        OrderEntity.setId("1234");
        OrderEntity.setUser(user);
        OrderEntity.setListaProduse(listaProduse);

        assertEquals("1234", OrderEntity.getId());
        assertEquals(user, OrderEntity.getUser());
        assertEquals(listaProduse, OrderEntity.getListaProduse());
    }
}
