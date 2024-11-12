package view.model;

import javafx.beans.property.*;

public class OrderDTO {

    private StringProperty author;

    public void setauthor(String author) {
        authorProperty().set(author);
    }

    public String getAuthor() {
        return authorProperty().get();
    }

    public StringProperty authorProperty() {
        if(author == null) {
            author = new SimpleStringProperty(this, "author");
        }
        return author;
    }

    private StringProperty title;

    public void setTitle(String title) {
        titleProperty().set(title);
    }

    public String getTitle() {
        return titleProperty().get();
    }

    public StringProperty titleProperty() {
        if(title == null) {
            title = new SimpleStringProperty(this, "title");
        }
        return title;
    }

    private IntegerProperty quantity;

    public void setQuantity(Integer quantity) {
        quantityProperty().set(quantity);
    }

    public Integer getQuantity() {
        return quantityProperty().get();
    }

    public IntegerProperty quantityProperty() {
        if(quantity == null) {
            quantity = new SimpleIntegerProperty(this, "quantity");
        }
        return quantity;
    }

    private FloatProperty price;

    public void setPrice(Float price) {
        priceProperty().set(price);
    }

    public Float getPrice() {
        return priceProperty().get();
    }

    public FloatProperty priceProperty() {
        if(price == null) {
            price = new SimpleFloatProperty(this, "price");
        }
        return price;
    }

}
