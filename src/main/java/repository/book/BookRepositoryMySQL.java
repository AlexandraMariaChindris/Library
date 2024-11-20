package repository.book;

import model.Book;
import model.builder.BookBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository {

    private final Connection connection;
    public BookRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Book> findAll() {
        //aici putem folosi Statement pentru ca datele de intrare nu modifica Stringul
        String sql = "SELECT * FROM book;";

        List<Book> books = new ArrayList<>();

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {

        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setStock(resultSet.getInt("stock"))
                .setPrice(resultSet.getFloat("price"))
                .build();
    }

    @Override
    public Optional<Book> findById(Long id) {
        //avem nevoie de Prepared Statement
        String sql = "SELECT * FROM book WHERE id = ?";
        Optional<Book> book = Optional.empty();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                book = Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return book;
    }

    @Override
    public boolean save(Book book) {
        String newSql = "INSERT INTO book VALUES (null, ?, ?, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(newSql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, Date.valueOf(book.getPublishedDate()));
            preparedStatement.setInt(4, book.getStock());
            preparedStatement.setFloat(5, book.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Book book) {
        //nu avem nevoie de PreparedStatement pentru ca delete se face la selectarea unui rand si apasarea butonului de delete
        //o facem doar pentru a fi codul mai lizibil

        String deleteSql = "DELETE FROM book WHERE author = ? AND title = ? AND stock = ? AND price = ?;";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setInt(3, book.getStock());
            preparedStatement.setFloat(4, book.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateStock(Book book) {
        String updateSql = "UPDATE book SET stock = stock - 1 WHERE author = ? AND title = ? AND stock = ? AND price = ?;";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setInt(3, book.getStock());
            preparedStatement.setFloat(4, book.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM book WHERE id >= 0;";

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
