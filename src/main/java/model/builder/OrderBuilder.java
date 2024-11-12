package model.builder;

import model.Order;

public class OrderBuilder {

    private Order order;

    public OrderBuilder() {
        order = new Order();
    }

    public OrderBuilder setId(Long id){
        order.setId(id);
        return this;
    }

    public OrderBuilder setTitle(String title){
        order.setTitle(title);
        return this;
    }

    public OrderBuilder setAuthor(String author){
        order.setAuthor(author);
        return this;
    }

    public OrderBuilder setQuantity(Integer quantity){
        order.setQuantity(quantity);
        return this;
    }

    public OrderBuilder setPrice(Float price){
        order.setPrice(price);
        return this;
    }

    public Order build(){
        return order;
    }


}
