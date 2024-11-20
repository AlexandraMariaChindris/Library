import database.DatabaseConnectionFactory;
import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySQL;
import repository.book.Cache;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQLImpl;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World");

        BookRepository bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySQL(
                DatabaseConnectionFactory.getConnectionWrapper(true).getConnection()), new Cache<>());
        BookService bookService = new BookServiceImpl(bookRepository);

        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(true).getConnection();

        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQLImpl(connection);
        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);

        if(userRepository.existsByUsername("ale01")){
            System.out.println("Username already used");
        }
        else {
            authenticationService.register("ale01", "parola123!");
        }

        System.out.println(authenticationService.login("ale01", "parola123!"));


    }
}
