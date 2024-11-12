package service;

import model.Book;

import java.util.*;

public interface BookService {

    //ce poate folosi presentation din service

    List<Book> findAll();
    Book findById(Long id);
    boolean save(Book book);
    boolean delete(Book book);
    boolean updateStock(Book book);
    int getAgeOfbook(Long id);


}
