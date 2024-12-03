package view.model.builder;

import view.model.OrderDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderDTOBuilder {

    private OrderDTO orderDTO;

    public OrderDTOBuilder() {
        orderDTO = new OrderDTO();
    }


    public OrderDTOBuilder setAuthor(String author) {
        orderDTO.setAuthor(author);
        return this;
    }

    public OrderDTOBuilder setTitle(String title) {
        orderDTO.setTitle(title);
        return this;
    }

    public OrderDTOBuilder setQuantity(Integer quantity) {
        orderDTO.setQuantity(quantity);
        return this;
    }

    public OrderDTOBuilder setPrice(Float price) {
        orderDTO.setPrice(price);
        return this;
    }

    public OrderDTOBuilder setTime(LocalDateTime time) {
        orderDTO.setTime(time);
        return this;
    }

    public OrderDTO build() {
        return orderDTO;
    }
}
