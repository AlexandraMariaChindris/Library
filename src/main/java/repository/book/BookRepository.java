package repository.book;

import model.Book;

import java.util.*;

public interface BookRepository {
    //ce poate folosi service din repository

    List<Book> findAll();

    Optional<Book> findById(Long id);

    boolean save(Book book);

    boolean delete(Book book);

    boolean updateStock(Book book);

    void removeAll();
}
