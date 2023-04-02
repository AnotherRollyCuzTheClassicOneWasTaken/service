package ro.unibuc.hello.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.unibuc.hello.data.OrderEntity;
import ro.unibuc.hello.data.OrderRepo;
import ro.unibuc.hello.data.Produs;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.dto.UserDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Tag("IT")
public class OrderServiceTestIT {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProdusService produsService;
    @Autowired
    private UserService userService;

    private         UserEntity user = new UserEntity("1","b","c");
    private         Produs produs = new Produs("1","asd","as");
    private Produs produs1 = new Produs("2","SDF","ASDF");
    private List<Produs> list;

    @Test
    void test_createOrder() {
        addStuff();

        OrderEntity orderExpected = new OrderEntity("1", user, list);
        OrderEntity orderActual = orderService.createOrder(new OrderDTO("1", user.getId(), getIdFromProds(list)));

        assertEquals(orderExpected, orderActual);
        orderService.deleteAll();
        produsService.deleteAll();
        userService.deleteAll();
    }

    @Test
    void test_getOrder() {
        addStuff();

        OrderEntity expectedOrder = orderService.createOrder(new OrderDTO("1", user.getId(), getIdFromProds(list)));
        OrderDTO actualOrder = orderService.getOrder(expectedOrder.getId());

        assertEquals(orderService.entityToDTO(expectedOrder), actualOrder);
        orderService.deleteAll();
        produsService.deleteAll();
        userService.deleteAll();
    }

    @Test
    void test_getAllOrders() {
//        UserEntity user = new UserEntity("1","b","c");
//        Produs produs = new Produs("1","asd","as");
//        Produs produs1 = new Produs("2","SDF","ASDF");
        addStuff();
        orderService.createOrder(new OrderDTO("1", user.getId(), getIdFromProds(list)));

        OrderDTO order1 = new OrderDTO("1", "1",  getIdFromProds(list));

        List<OrderDTO> expectedList = new ArrayList<>();
        expectedList.add(order1);

        List<OrderDTO> actualList = orderService.getAll();

        assertEquals(expectedList, actualList);
        orderService.deleteAll();
        produsService.deleteAll();
        userService.deleteAll();
    }

    @Test
    void test_updateOrder() {
        addStuff();
        user.setParola("updatedFromOrder");
        orderService.createOrder(new OrderDTO("1", user.getId(), getIdFromProds(list)));

        OrderDTO updatedOrder = new OrderDTO("1", user.getId(), getIdFromProds(list));

        OrderDTO actualOrder= orderService.updateOrder(updatedOrder);

        assertEquals(updatedOrder, actualOrder);
        orderService.deleteAll();
        produsService.deleteAll();
        userService.deleteAll();

    }

    @Test
    void test_deleteorder() {
        addStuff();
        orderService.createOrder(new OrderDTO("1", user.getId(), getIdFromProds(list)));

        orderService.deleteOrder("2");

        try {
            orderService.getOrder("2");
        } catch (Exception e) {
            assertTrue(true);
        }
        orderService.deleteAll();
        produsService.deleteAll();
        userService.deleteAll();
    }

    private void addStuff() {
        orderService.deleteAll();
        produsService.deleteAll();
        userService.deleteAll();

        userService.createUser(userService.entityToDTO(user));

        produsService.createProdus(produsService.entityToDTO(produs));
        produsService.createProdus(produsService.entityToDTO(produs1));

        list = new ArrayList<>();
        list.add(produs);
        list.add(produs1);
    }

    private List<String> getIdFromProds(List<Produs> list) {
        List<String> stringList = new ArrayList<>();
        for (Produs prod : list) {
            stringList.add(prod.getId());
        }
        return stringList;
    }
}