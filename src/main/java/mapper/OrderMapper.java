package mapper;

import model.Order;
import model.builder.OrderBuilder;
import view.model.BookDTO;
import view.model.OrderDTO;
import view.model.builder.OrderDTOBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO convertOrderToOrderDTO(Order order) {
        return new OrderDTOBuilder().setAuthor(order.getAuthor()).setTitle(order.getTitle()).setQuantity(order.getQuantity()).setPrice(order.getPrice()).build();
    }

    public static Order convertOrderDTOToOrder(OrderDTO orderDTO) {
        return new OrderBuilder().setAuthor(orderDTO.getAuthor()).setTitle(orderDTO.getTitle()).setQuantity(orderDTO.getQuantity()).setPrice(orderDTO.getPrice()).build();
    }

    public static List<OrderDTO> convertOrderListToOrderDTOList(List<Order> orders) {
        return orders.parallelStream().map(OrderMapper::convertOrderToOrderDTO).collect(Collectors.toList());
    }

    public static List<Order> convertOrderDTOListToOrderList(List<OrderDTO> ordersDTO) {
        return ordersDTO.parallelStream().map(OrderMapper::convertOrderDTOToOrder).collect(Collectors.toList());
    }

    public static Order convertBookDTOToOrder(BookDTO bookDTO, Long id) {
        return new OrderBuilder().setAuthor(bookDTO.getAuthor()).setTitle(bookDTO.getTitle()).setPrice(bookDTO.getPrice()).setQuantity(1).setUserId(id).build();
    }

    public static OrderDTO convertBookDTOToOrderDTO(BookDTO bookDTO) {
        return new OrderDTOBuilder().setAuthor(bookDTO.getAuthor()).setTitle(bookDTO.getTitle()).setPrice(bookDTO.getPrice()).setQuantity(1).build();
    }
}
