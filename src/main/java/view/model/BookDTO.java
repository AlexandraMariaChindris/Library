package view.model;

import javafx.beans.property.*;

import java.time.LocalDate;


public class BookDTO {
    //mi se vor sterge si adauga carti in functie de ce contine ObservableList

    private LongProperty id;


    public Long getId(){
        return idProperty().get();
    }

    public void setId(long id){
        idProperty().set(id);
    }

    public LongProperty idProperty() {
        if (id == null) {
            id = new SimpleLongProperty(this, "id");
        }
        return id;
    }

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

    private IntegerProperty stock;

    public void setStock(Integer stock) {
        stockProperty().set(stock);
    }

    public Integer getStock() {
        return stockProperty().get();
    }

    public IntegerProperty stockProperty() {
        if(stock == null) {
            stock = new SimpleIntegerProperty(this, "stock");
        }
        return stock;
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

    private ObjectProperty<LocalDate> publishedDate;

    public void setPublishedDate(LocalDate publishedDate) {
       publishedDateProperty().set(publishedDate);
    }

    public LocalDate getPublishedDate() {
        return publishedDateProperty().get();
    }

    public ObjectProperty<LocalDate> publishedDateProperty() {
        if(publishedDate == null) {
            publishedDate = new SimpleObjectProperty(this, "publishedDate");
        }
        return publishedDate;
    }


}
