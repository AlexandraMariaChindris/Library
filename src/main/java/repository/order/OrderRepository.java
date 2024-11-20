package repository.order;

import model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> findAll();

    Optional<Order> findById(Long id);

    boolean save(Order order);

    boolean delete(Order order);

    void removeAll();
}
