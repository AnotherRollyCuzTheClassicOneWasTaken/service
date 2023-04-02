package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.OrderEntity;
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.service.OrderService;


import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrder")
    @ResponseBody
    public OrderDTO getOrder(@RequestParam(name = "id", required = false) String id) {
        return orderService.getOrder(id);
    }

    @PostMapping("/createOrder")
    @ResponseBody
    public OrderEntity createorder(OrderDTO order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/getAllOrders")
    @ResponseBody
    public List<OrderDTO> getAll() {
        return orderService.getAll();
    }

    @PutMapping("/putOrder")
    @ResponseBody
    public OrderDTO updateorder(OrderDTO order) {
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/deleteOrder")
    @ResponseBody
    public boolean deleteorder(String id) {
        return orderService.deleteOrder(id);
    }
}