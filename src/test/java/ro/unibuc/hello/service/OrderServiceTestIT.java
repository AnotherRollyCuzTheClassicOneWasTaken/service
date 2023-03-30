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


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Tag("IT")
public class OrderServiceTestIT {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    private OrderService orderService;


    private List<String> listID = new ArrayList<>();


    @Test
    void test_createOrder() {
        UserEntity user = new UserEntity("1","b","c");
        Produs produs = new Produs("1","asd","as");
        Produs produs1 = new Produs("2","SDF","ASDF");
        List<Produs> list = new ArrayList<>();
        list.add(produs1);
        list.add(produs);
        OrderEntity orderExpected= new OrderEntity("1", user, list);
        OrderDTO order = buildOrderDto();
        OrderEntity orderActual = orderService.createOrder(order);

        assertEquals(orderExpected, orderActual);
    }

    @Test
    void test_getOrder() {
        OrderDTO expected =buildOrderDto();
        OrderDTO actualOrder = orderService.getOrder(expected.getId());

        assertEquals(expected, actualOrder);
    }

    @Test
    void test_getAllOrders() {
//        UserEntity user = new UserEntity("1","b","c");
//        Produs produs = new Produs("1","asd","as");
//        Produs produs1 = new Produs("2","SDF","ASDF");

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        OrderDTO order1 = new OrderDTO("1", "1", list);
        OrderDTO order2 = new OrderDTO("2", "1", list);

        List<OrderDTO> expectedList = new ArrayList<>();
        expectedList.add(order1);
        expectedList.add(order2);

        List<OrderDTO> actualList = orderService.getAll();

        assertEquals(expectedList,actualList);
    }

    @Test
    void test_updateOrder() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");

        OrderDTO updatedOrder = new OrderDTO("1", "uraaa", list);

       OrderDTO actualOrder= orderService.updateOrder(updatedOrder);


        assertEquals(updatedOrder, actualOrder);

    }

    @Test
    void test_deleteorder() {
        orderService.deleteOrder("2");

        try {
            orderService.getOrder("2");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    private OrderDTO buildOrderDto(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        OrderDTO order1 = new OrderDTO("1", "1", list);
        return order1;
    }
}