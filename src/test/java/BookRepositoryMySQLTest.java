import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryMySQLTest {

    private static BookRepository bookRepository;
    private static Connection connection = DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();

    @BeforeAll
    public static void setup() {
        bookRepository = new BookRepositoryMySQL(connection);
    }

    @Test
    public void findAll(){
        List<Book> books =  bookRepository.findAll();
        assertEquals(0, books.size());
    }

    @Test
    public void findById(){
        final Optional<Book> book = bookRepository.findById(1L);
        assertTrue(book.isEmpty());
    }

    @Test
    public void save(){
        assertTrue(bookRepository.save(new BookBuilder().setTitle("Moara cu noroc").setAuthor("Ioan Slavici").setPublishedDate(LocalDate.of(1950, 2, 10)).build()));
    }

    @Test
    public void delete(){
        assertTrue(bookRepository.delete(new BookBuilder().setTitle("Moara cu noroc").setAuthor("Ioan Slavici").setPublishedDate(LocalDate.of(1950, 2, 10)).build()));
    }

    //nu e necesara pentru ca stergem cartea pe care o salvam, dar daca cele doua carti ar fi diferite vom avem nevoie
    @AfterAll
    public static void removeAll(){
        bookRepository.removeAll();
    }
}
