package ro.unibuc.hello.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.OrderEntity;
import ro.unibuc.hello.data.OrderRepo;
import ro.unibuc.hello.data.Produs;
import ro.unibuc.hello.data.ProdusRepository;
import ro.unibuc.hello.data.UserRepo;
import ro.unibuc.hello.dto.OrderDTO;
import ro.unibuc.hello.dto.ProdusDTO;

@Component
public class OrderService {
    @Autowired
    OrderRepo repo;
    @Autowired
    UserRepo repoUser;
    @Autowired
    ProdusRepository repoProduse;

    public OrderEntity toEntity(OrderDTO orderDTO) {
        OrderEntity order = new OrderEntity(orderDTO.getId(), 
                repoUser.findById(orderDTO.getUserID()).get(), StreamSupport.stream(repoProduse.findAllById(orderDTO.getListaProduse()).spliterator(),false).collect(Collectors.toList()));
        return order;
    }

    public OrderDTO entityToDTO(OrderEntity order) {
        return new OrderDTO(order.getId(), order.getUser().getId(), order.getListaProduse().stream().map(produs -> produs.getId()).collect(Collectors.toList()));
    }

    public OrderDTO getOrder(String id) {
        return entityToDTO(repo.findById(id).get());
    }

    public OrderEntity createOrder(OrderDTO order) {
        return repo.save(toEntity(order));
    }

    public List<OrderDTO> getAll() {
        return repo.findAll().stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    public OrderDTO updateOrder(OrderDTO orderDTO) {
        OrderEntity found = repo.findById(orderDTO.getId()).orElse(null);
        if (found != null) {
            found.setListaProduse(
                    StreamSupport.stream(repoProduse.findAllById(orderDTO.getListaProduse()).spliterator(), false).collect(Collectors.toList()));
            found.setUser(repoUser.findById(orderDTO.getUserID()).get());
           return entityToDTO(repo.save(found));

        }
       return new OrderDTO();
    }

    public boolean deleteOrder(String id) {
        OrderEntity found = repo.findById(id).orElse(null);
        if (found != null) {
            repo.delete(found);
            return true;
        }
        return false;
    }

    public Produs toEntity(ProdusDTO produsDTO) {
        Produs produs = new Produs(produsDTO.getId(), produsDTO.getNume(), produsDTO.getPret());
        return produs;
    }

    public void deleteAll() {
        repo.deleteAll();
    }
}