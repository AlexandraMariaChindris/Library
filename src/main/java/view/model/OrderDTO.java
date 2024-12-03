package view.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class OrderDTO {


    private StringProperty author;

    public void setAuthor(String author) {
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

    private ObjectProperty<LocalDateTime> time;

    public void setTime(LocalDateTime time) {
        timeProperty().set(time);
    }

    public LocalDateTime getTime() {
        return timeProperty().get();
    }

    public ObjectProperty<LocalDateTime> timeProperty() {
        if(time == null) {
            time = new SimpleObjectProperty(this, "time");
        }
        return time;
    }

}
